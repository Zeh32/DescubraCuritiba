package com.example.jgaug.descubracuritiba.Activities;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.zetterstrom.com.forecast.ForecastClient;
import android.zetterstrom.com.forecast.ForecastConfiguration;
import android.zetterstrom.com.forecast.models.Forecast;
import android.zetterstrom.com.forecast.models.Language;
import android.zetterstrom.com.forecast.models.Unit;

import com.example.jgaug.descubracuritiba.Api.DescubraCuritibaApi;
import com.example.jgaug.descubracuritiba.Api.Response.DistanciaResponse;
import com.example.jgaug.descubracuritiba.Api.Response.Element;
import com.example.jgaug.descubracuritiba.Fragments.DatePickerFragment;
import com.example.jgaug.descubracuritiba.Fragments.TimePickerFragment;
import com.example.jgaug.descubracuritiba.Helpers.DailyItinerary;
import com.example.jgaug.descubracuritiba.Helpers.DailyItineraryList;
import com.example.jgaug.descubracuritiba.Helpers.Place;
import com.example.jgaug.descubracuritiba.Helpers.PlaceGroup;
import com.example.jgaug.descubracuritiba.R;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreateItinerary extends AppCompatActivity {
    private Calendar startDay = Calendar.getInstance( );
    private Calendar endDay = Calendar.getInstance( );
    private List< Integer > selectedPlaceGroups = new ArrayList<>( );
    private String originCoordinates = "";
    private final double MIN_PRECIP_PROBABILITY = 0.50;

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_create_itinerary );

        EditText editText = findViewById( R.id.editTextOrigin );
        editText.setOnEditorActionListener( ( textView, actionId, event ) -> {
            if( actionId == EditorInfo.IME_ACTION_SEARCH ) {
                onSearchOriginPlace( textView.getText( ).toString( ) );
            }

            return false; //return false to close the keyboard
        } );
    }

    public void onSearchOriginPlace( String originAddress ) {
        Geocoder geocoder = new Geocoder( getApplicationContext( ) );
        try {
            List< Address > addresses = geocoder.getFromLocationName( originAddress, 1 );
            if( addresses.size( ) > 0 ) {
                AlertDialog.Builder builder = new AlertDialog.Builder( CreateItinerary.this );
                builder.setTitle( "Pesquisa de endereço" );
                builder.setMessage( "Endereço definido para:\n\n" + addresses.get( 0 ).getAddressLine( 0 ) );
                builder.setPositiveButton( "Confirmar", ( dialog, id ) -> {
                    EditText editText = findViewById( R.id.editTextOrigin );

                    boolean coordinatesBetweenRange = addresses.get( 0 ).getLatitude( ) > -25.68 && addresses.get( 0 ).getLatitude( ) < -25.27 && addresses.get( 0 ).getLongitude( ) > -49.56 && addresses.get( 0 ).getLongitude( ) < -49.03;
                    if( coordinatesBetweenRange ) {
                        originCoordinates = addresses.get( 0 ).getLatitude( ) + "," + addresses.get( 0 ).getLongitude( );
                        editText.setText( addresses.get( 0 ).getAddressLine( 0 ) );
                        Toast.makeText( CreateItinerary.this, "Local de partida definido com sucesso.", Toast.LENGTH_SHORT ).show( );
                    } else {
                        Toast.makeText( CreateItinerary.this, "Não é possível definir como local de partida um ponto muito longe de Curitiba.", Toast.LENGTH_LONG ).show( );
                    }

                    editText.clearFocus( );
                } );
                builder.setNegativeButton( "Tentar novamente", ( dialogInterface, i ) -> {
                    EditText editText = findViewById( R.id.editTextOrigin );

                    //Shows the keyboard
                    InputMethodManager imm = ( InputMethodManager ) getSystemService( Context.INPUT_METHOD_SERVICE );
                    imm.showSoftInput( editText, InputMethodManager.SHOW_IMPLICIT );
                } );
                builder.create( ).show( );
            } else {
                Toast.makeText( this, "Não foi possível localizar o endereço fornecido. Por favor, tente novamente", Toast.LENGTH_LONG ).show( );
            }
        } catch( IOException e ) {
            Toast.makeText( this, "Houve uma falha ao tentar localizar o endereço fornecido.", Toast.LENGTH_SHORT ).show( );
            e.printStackTrace( );
        }
    }

    public void btnUseCurrentLocation( View view ) {
        if( ActivityCompat.checkSelfPermission( this, Manifest.permission.ACCESS_FINE_LOCATION ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission( this, Manifest.permission.ACCESS_COARSE_LOCATION ) != PackageManager.PERMISSION_GRANTED ) {
            final int ACCESS_FINE_LOCATION = 199;
            ActivityCompat.requestPermissions( this, new String[] { Manifest.permission.ACCESS_FINE_LOCATION }, ACCESS_FINE_LOCATION );
        } else {
            FusedLocationProviderClient mFusedLocationClient = LocationServices.getFusedLocationProviderClient( this );
            mFusedLocationClient.getLastLocation( ).addOnSuccessListener( this, location -> {
                if( location == null ) {
                    Toast.makeText( CreateItinerary.this, "Não foi possível determinar sua localização atual.", Toast.LENGTH_SHORT ).show( );
                } else {
                    Toast.makeText( CreateItinerary.this, "Local de partida definido com sucesso.", Toast.LENGTH_SHORT ).show( );

                    originCoordinates = location.getLatitude( ) + "," + location.getLongitude( );

                    EditText editTextOrigin = findViewById( R.id.editTextOrigin );
                    editTextOrigin.setText( "Localização atual" );
                }
            } );
        }
    }

    public void setDate( View view ) {
        //TODO: não deixar setar a data de término, se a inicial ainda não foi definida
        //TODO: não deixar setar para o dia de hoje se já passaram das xx horas

        Bundle bundle = new Bundle( );
        if( view.getId( ) == R.id.textViewStartDay ) {
            bundle.putBoolean( "isStartDay", true );
        } else {
            bundle.putBoolean( "isStartDay", false );
        }

        DialogFragment datePickerFragment = new DatePickerFragment( );
        datePickerFragment.setArguments( bundle );
        datePickerFragment.show( getSupportFragmentManager( ), "datePicker" );
    }

    public void setTime( View view ) {
        //TODO: não deixar setar o tempo de término, se o inicial ainda não foi definido

        Bundle bundle = new Bundle( );
        if( view.getId( ) == R.id.textViewStartTime ) {
            bundle.putBoolean( "isStartTime", true );
        } else {
            bundle.putBoolean( "isStartTime", false );
        }

        DialogFragment timePickerFragment = new TimePickerFragment( );
        timePickerFragment.setArguments( bundle );
        timePickerFragment.show( getSupportFragmentManager( ), "timePicker" );
    }

    public void setDate( boolean isStartDay, int year, int month, int day ) {
        if( isStartDay ) {
            this.startDay.set( year, month, day );
        } else {
            this.endDay.set( year, month, day );
        }
    }

    public void setTime( boolean isStartTime, int hourOfDay, int minute ) {
        if( isStartTime ) {
            this.startDay.set( Calendar.HOUR_OF_DAY, hourOfDay );
            this.startDay.set( Calendar.MINUTE, minute );
            this.startDay.set( Calendar.SECOND, 0 );
        } else {
            this.endDay.set( Calendar.HOUR_OF_DAY, hourOfDay );
            this.endDay.set( Calendar.MINUTE, minute );
            this.endDay.set( Calendar.SECOND, 0 );
        }
    }

    public Calendar getStartDay( ) {
        return startDay;
    }

    public void onSelectPlacesToVisit( View view ) {
        switch( view.getId( ) ) {
            case R.id.imageViewParks:
                changeImageViewResource( PlaceGroup.PARKS, R.id.imageViewParks, R.drawable.parks_checked, R.drawable.parks_unchecked );
                break;
            case R.id.imageViewLandmarks:
                changeImageViewResource( PlaceGroup.LANDMARKS, R.id.imageViewLandmarks, R.drawable.landmarks_checked, R.drawable.landmarks_unchecked );
                break;
            case R.id.imageViewMuseums:
                changeImageViewResource( PlaceGroup.MUSEUMS, R.id.imageViewMuseums, R.drawable.museums_checked, R.drawable.museums_unchecked );
                break;
            case R.id.imageViewShopping:
                changeImageViewResource( PlaceGroup.SHOPPING, R.id.imageViewShopping, R.drawable.shopping_checked, R.drawable.shopping_unchecked );
                break;
            case R.id.imageViewFood:
                changeImageViewResource( PlaceGroup.FOOD, R.id.imageViewFood, R.drawable.food_checked, R.drawable.food_unchecked );
                break;
        }
    }

    private void changeImageViewResource( int placeGroupSelected, int imageViewId, int checkedImageViewId, int uncheckedImageViewId ) {
        ImageView imageView = findViewById( imageViewId );
        if( selectedPlaceGroups.contains( placeGroupSelected ) ) {
            selectedPlaceGroups.remove( ( Integer ) placeGroupSelected );
            imageView.setImageResource( uncheckedImageViewId );
        } else {
            selectedPlaceGroups.add( placeGroupSelected );
            imageView.setImageResource( checkedImageViewId );
        }
    }

    public void btnMakeItinerary( View view ) {
        if( checkConstraints( ) ) {
            ProgressDialog progressDialog = ProgressDialog.show( this, "", "Gerando itinerário. Por favor, aguarde...", true );

            final FirebaseDatabase database = FirebaseDatabase.getInstance( );
            DatabaseReference ref = database.getReference( "" );

            ref.addValueEventListener( new ValueEventListener( ) {
                @Override
                public void onDataChange( DataSnapshot dataSnapshot ) {
                    List< Place > selectedPlaces = getSelectedPlaces( dataSnapshot.child( "places" ) );
                    int numberOfPlaces = selectedPlaces.size( ) >= 25 ? 25 : selectedPlaces.size( ); //Limite de destinos em uma única chamada da API é de 25
                    String destinationCoordinates = getDestinationCoordinates( selectedPlaces, numberOfPlaces );

                    retrofit2.Call< DistanciaResponse > call = new DescubraCuritibaApi( ).distanciaApi( ).getDistancia( "pt-BR", "driving", originCoordinates, destinationCoordinates, "AIzaSyA2yt9xJV1gwgqJTpn-zUKnKIMK44iRCJA" );
                    call.enqueue( new Callback< DistanciaResponse >( ) {
                        @Override
                        public void onResponse( @NonNull Call< DistanciaResponse > call, @NonNull Response< DistanciaResponse > response ) {
                            //TODO: verificar status de retorno se está ok
                            DistanciaResponse distanciaResponse = response.body( );
                            List< Element > elementList = distanciaResponse.getRows( ).get( 0 ).getElements( );

                            for( int placeIndex = 0; placeIndex < numberOfPlaces; placeIndex++ ) {
                                int travelTime = elementList.get( placeIndex ).getDuration( ).getValue( ) / 60; //convert to minutes
                                selectedPlaces.get( placeIndex ).setTravelTimeFromOrigin( travelTime );
                            }

                            ForecastConfiguration configuration = new ForecastConfiguration.Builder( "cc8d5c7bd7bd6815677077038773bb58" ).setDefaultLanguage( Language.PORTUGUESE ).setDefaultUnit( Unit.CA ).setCacheDirectory( getCacheDir( ) ).build( );
                            ForecastClient.create( configuration );

                            //Latitude e longitude do centro de Curitiba
                            ForecastClient.getInstance( ).getForecast( -25.4247427, -49.2763924, new Callback< Forecast >( ) {
                                @Override
                                public void onResponse( @NonNull Call< Forecast > call, @NonNull Response< Forecast > response ) {
                                    if( !response.isSuccessful( ) ) {
                                        Toast.makeText( CreateItinerary.this, "Houve uma falha ao obter as informações de previsão do tempo.", Toast.LENGTH_LONG ).show( );
                                        progressDialog.dismiss( );
                                    } else {
                                        Forecast forecast = response.body( );
                                        List< List< Integer > > travelTimes = dataSnapshot.child( "travelTimesByCar" ).getValue( new GenericTypeIndicator< List< List< Integer > > >( ) {
                                        } );
                                        DailyItineraryList itinerary = makeItinerary( selectedPlaces, travelTimes, forecast );

                                        saveItinerary( itinerary );

                                        Intent intent = new Intent( CreateItinerary.this, Itinerary.class );
                                        intent.putExtra( "itinerary", itinerary );

                                        progressDialog.dismiss( );

                                        CreateItinerary.this.startActivity( intent );
                                    }
                                }

                                @Override
                                public void onFailure( @NonNull Call< Forecast > call, @NonNull Throwable t ) {
                                    Toast.makeText( CreateItinerary.this, "Houve uma falha ao obter as informações de previsão do tempo.", Toast.LENGTH_LONG ).show( );
                                    progressDialog.dismiss( );
                                }
                            } );
                        }

                        @Override
                        public void onFailure( @NonNull Call< DistanciaResponse > call, @NonNull Throwable t ) {
                            Toast.makeText( CreateItinerary.this, "Houve uma falha ao calcular as distâncias a partir do ponto de origem do usuário.", Toast.LENGTH_LONG ).show( );
                            progressDialog.dismiss( );
                        }
                    } );
                }

                @Override
                public void onCancelled( DatabaseError databaseError ) {
                    Toast.makeText( CreateItinerary.this, "Houve uma falha ao ler as informações do banco de dados. Código de erro: " + databaseError.getCode( ), Toast.LENGTH_LONG ).show( );
                    progressDialog.dismiss( );
                }
            } );
        }
    }

    private String getDestinationCoordinates( List< Place > selectedPlaces, int numberOfPlaces ) {
        String destinationCoordinates = "";
        for( int placeIndex = 0; placeIndex < numberOfPlaces; placeIndex++ ) {
            destinationCoordinates += selectedPlaces.get( placeIndex ).getCoordinates( );

            if( placeIndex < numberOfPlaces - 1 ) {
                destinationCoordinates += "|";
            }
        }

        return destinationCoordinates;
    }

    private boolean checkConstraints( ) {
        //TODO: Fazer um limite de seleção de dias... se selecionar só gastronômicos por exemplo, não deixar mais de 1 dia
        if( originCoordinates.equals( "" ) ) {
            Toast.makeText( this, "Para gerar um itinerário, é necessário selecionar um local de partida", Toast.LENGTH_LONG ).show( );
        } else if( selectedPlaceGroups.isEmpty( ) ) {
            Toast.makeText( this, "Para gerar um itinerário, é necessário selecionar ao menos um grupo de locais para visita!", Toast.LENGTH_LONG ).show( );
        } else {
            long diff = endDay.getTimeInMillis( ) - startDay.getTimeInMillis( ); //result in millis

            if( diff < 0 ) {
                Toast.makeText( this, "O último dia tem que vir depois do primeiro!", Toast.LENGTH_SHORT ).show( );
            } else if( ( ( TextView ) findViewById( R.id.textViewStartDay ) ).getText( ) == "" ) {
                Toast.makeText( this, "Preencha o primeiro dia!", Toast.LENGTH_SHORT ).show( );
            } else if( ( ( TextView ) findViewById( R.id.textViewEndDay ) ).getText( ) == "" ) {
                Toast.makeText( this, "Preencha o último dia!", Toast.LENGTH_SHORT ).show( );
            } else if( ( ( TextView ) findViewById( R.id.textViewStartTime ) ).getText( ) == "" ) {
                Toast.makeText( this, "Preencha o horário de início!", Toast.LENGTH_SHORT ).show( );
            } else if( ( ( TextView ) findViewById( R.id.textViewEndTime ) ).getText( ) == "" ) {
                Toast.makeText( this, "Preencha o horário de fim!", Toast.LENGTH_SHORT ).show( );
            } else {
                return true;
            }
        }

        return false;
    }

    private List< Place > getSelectedPlaces( DataSnapshot dataSnapshot ) {
        List< Place > selectedPlaces = new ArrayList<>( );

        for( DataSnapshot placeDataSnapshot : dataSnapshot.getChildren( ) ) {
            Place place = placeDataSnapshot.getValue( Place.class );
            if( !Collections.disjoint( selectedPlaceGroups, place.getPlaceGroup( ) ) ) {
                selectedPlaces.add( place );
            }
        }

        //Order selected places in descending order by relevance
        Collections.sort( selectedPlaces, ( place1, place2 ) -> place2.getRelevance( ) - place1.getRelevance( ) );

        return selectedPlaces;
    }

    private DailyItineraryList makeItinerary( List< Place > selectedPlaces, List< List< Integer > > travelTimes, Forecast forecast ) {
        boolean considerForecast = ( ( CheckBox ) findViewById( R.id.checkBoxForecast ) ).isChecked( );

        Calendar todayAtMidnight = Calendar.getInstance( );
        todayAtMidnight.set( Calendar.HOUR_OF_DAY, 0 );
        todayAtMidnight.set( Calendar.MINUTE, 0 );
        int forecastArrayPosition = getNumberOfDays( startDay, todayAtMidnight );

        DailyItineraryList itinerary = new DailyItineraryList( );
        Calendar startTime = ( Calendar ) startDay.clone( );
        Calendar endTime = ( Calendar ) startDay.clone( );

        int numberOfDays = getNumberOfDays( endDay, startDay ) + 1;
        for( int day = 0; day < numberOfDays; day++, forecastArrayPosition++ ) {
            if( day > 0 ) {
                startTime.roll( Calendar.DAY_OF_YEAR, true );
                endTime.roll( Calendar.DAY_OF_YEAR, true );
            }

            endTime.set( Calendar.HOUR_OF_DAY, endDay.get( Calendar.HOUR_OF_DAY ) );
            endTime.set( Calendar.MINUTE, endDay.get( Calendar.MINUTE ) );

            Calendar nextStartTime = ( Calendar ) startTime.clone( );
            DailyItinerary dailyItinerary;
            if( considerForecast && forecastArrayPosition <= 7 && forecast.getDaily( ).getDataPoints( ).get( forecastArrayPosition ).getPrecipProbability( ) >= MIN_PRECIP_PROBABILITY ) {
                dailyItinerary = getDailyItineraryWithForecast( selectedPlaces, travelTimes, nextStartTime, endTime );
            } else {
                dailyItinerary = getDailyItineraryWithoutForecast( selectedPlaces, travelTimes, nextStartTime, endTime );
            }

            if( selectedPlaces.isEmpty( ) && dailyItinerary.getPlaces( ).isEmpty( ) ) {
                Toast.makeText( this, "Número de locais insuficientes para construir um itinerário de acordo com os parâmetros fornecidos.", Toast.LENGTH_LONG ).show( );
                break;
            } else if( dailyItinerary.getPlaces( ).isEmpty( ) ) {
                Toast.makeText( this, "Número de locais insuficientes para construir um itinerário de acordo com a previsão do tempo dos próximos 7 dias.", Toast.LENGTH_LONG ).show( );
            }

            itinerary.addDailyItinerary( dailyItinerary );
        }

        return itinerary;
    }

    public static int getNumberOfDays( Calendar lastDay, Calendar firstDay ) {
        long diff = lastDay.getTimeInMillis( ) - firstDay.getTimeInMillis( ); //result in millis
        long numberOfDays = ( diff / ( 24 * 60 * 60 * 1000 ) );

        return ( int ) numberOfDays;
    }

    private DailyItinerary getDailyItineraryWithoutForecast( List< Place > selectedPlaces, List< List< Integer > > travelTimes, Calendar nextStartTime, Calendar endTime ) {
        DailyItinerary dailyItinerary = new DailyItinerary( );
        while( selectedPlaces.size( ) > 0 ) {
            Place removedPlace;
            if( dailyItinerary.getPlaces( ).isEmpty( ) ) {
                //Sort selectedPlaces by descending relevance and then by ascending travelTimeFromOrigin
                Collections.sort( selectedPlaces, ( place1, place2 ) -> {
                    int relevanceDifference = place2.getRelevance( ) - place1.getRelevance( );
                    if( relevanceDifference != 0 ) {
                        return relevanceDifference;
                    } else {
                        return place1.getTravelTimeFromOrigin( ) - place2.getTravelTimeFromOrigin( );
                    }
                } );

                //Set removedPlace startTime and add it to dailyItinerary
                removedPlace = selectedPlaces.remove( 0 );
                nextStartTime.add( Calendar.MINUTE, removedPlace.getTravelTimeFromOrigin( ) );
            } else {
                removedPlace = selectedPlaces.remove( 0 );
            }

            removedPlace.setStartTime( nextStartTime );
            dailyItinerary.addPlace( removedPlace );

            if( selectedPlaces.isEmpty( ) ) {
                return dailyItinerary;
            }

            //Set the travel time from the removedPlace to other places
            for( Place place : selectedPlaces ) {
                int travelTimeToNextPlace = travelTimes.get( removedPlace.getId( ) ).get( place.getId( ) );
                place.setTravelTimeFromPreviousPlaceByCar( travelTimeToNextPlace );
            }

            //Sort selectedPlaces by descending relevance and then by ascending travelTimeFromPreviousPlaceByCar
            Collections.sort( selectedPlaces, ( place1, place2 ) -> {
                int relevanceDifference = place2.getRelevance( ) - place1.getRelevance( );
                if( relevanceDifference != 0 ) {
                    return relevanceDifference;
                } else {
                    return place1.getTravelTimeFromPreviousPlaceByCar( ) - place2.getTravelTimeFromPreviousPlaceByCar( );
                }
            } );

            nextStartTime.add( Calendar.MINUTE, removedPlace.getVisitTime( ) + selectedPlaces.get( 0 ).getTravelTimeFromPreviousPlaceByCar( ) );

            Calendar nextFinishTime = ( Calendar ) nextStartTime.clone( );
            nextFinishTime.add( Calendar.MINUTE, ( selectedPlaces.get( 0 ) ).getVisitTime( ) );
            if( endTime.after( nextFinishTime ) == false ) {
                break;
            }
        }

        return dailyItinerary;
    }

    private DailyItinerary getDailyItineraryWithForecast( List< Place > selectedPlaces, List< List< Integer > > travelTimes, Calendar nextStartTime, Calendar endTime ) {
        DailyItinerary dailyItinerary = new DailyItinerary( );
        while( selectedPlaces.size( ) > 0 ) {
            Place removedPlace = null;
            if( dailyItinerary.getPlaces( ).isEmpty( ) ) {
                //Sort selectedPlaces by descending relevance and then by ascending travelTimeFromOrigin
                Collections.sort( selectedPlaces, ( place1, place2 ) -> {
                    int relevanceDifference = place2.getRelevance( ) - place1.getRelevance( );
                    if( relevanceDifference != 0 ) {
                        return relevanceDifference;
                    } else {
                        return place1.getTravelTimeFromOrigin( ) - place2.getTravelTimeFromOrigin( );
                    }
                } );
            }

            int placeIndex;
            for( placeIndex = 0; placeIndex < selectedPlaces.size( ); placeIndex++ ) {
                if( !selectedPlaces.get( placeIndex ).isWeatherDependent( ) ) {
                    removedPlace = selectedPlaces.remove( placeIndex );

                    if( dailyItinerary.getPlaces( ).isEmpty( ) ) {
                        nextStartTime.add( Calendar.MINUTE, removedPlace.getTravelTimeFromOrigin( ) );
                    }
                    removedPlace.setStartTime( nextStartTime );
                    dailyItinerary.addPlace( removedPlace );

                    break;
                }
            }

            if( placeIndex == selectedPlaces.size( ) || selectedPlaces.isEmpty( ) ) {
                return dailyItinerary;
            }

            //Set the travel time from the removedPlace to other places
            for( Place place : selectedPlaces ) {
                int travelTimeToNextPlace = travelTimes.get( removedPlace.getId( ) ).get( place.getId( ) );
                place.setTravelTimeFromPreviousPlaceByCar( travelTimeToNextPlace );
            }

            //Sort selectedPlaces by descending relevance and then by ascending travelTimeFromPreviousPlaceByCar
            Collections.sort( selectedPlaces, ( place1, place2 ) -> {
                int relevanceDifference = place2.getRelevance( ) - place1.getRelevance( );
                if( relevanceDifference != 0 ) {
                    return relevanceDifference;
                } else {
                    return place1.getTravelTimeFromPreviousPlaceByCar( ) - place2.getTravelTimeFromPreviousPlaceByCar( );
                }
            } );

            Calendar nextFinishTime = null;
            for( placeIndex = 0; placeIndex < selectedPlaces.size( ); placeIndex++ ) {
                if( !selectedPlaces.get( placeIndex ).isWeatherDependent( ) ) {
                    nextStartTime.add( Calendar.MINUTE, removedPlace.getVisitTime( ) + selectedPlaces.get( placeIndex ).getTravelTimeFromPreviousPlaceByCar( ) );

                    nextFinishTime = ( Calendar ) nextStartTime.clone( );
                    nextFinishTime.add( Calendar.MINUTE, ( selectedPlaces.get( placeIndex ) ).getVisitTime( ) );

                    break;
                }
            }

            if( !endTime.after( nextFinishTime ) ) {
                break;
            }
        }

        return dailyItinerary;
    }

    private void saveItinerary( DailyItineraryList itinerary ) {
        SharedPreferences settings = getSharedPreferences( "mySharedPreferences", MODE_PRIVATE );
        SharedPreferences.Editor editor = settings.edit( );

        String itineraryJson = new Gson( ).toJson( itinerary );
        editor.putString( "itineraryJson", itineraryJson );

        // Commit the edits!
        editor.apply( );
    }
}