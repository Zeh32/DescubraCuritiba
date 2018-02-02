package com.example.jgaug.descubracuritiba.Activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.jgaug.descubracuritiba.Api.DescubraCuritibaApi;
import com.example.jgaug.descubracuritiba.Api.Response.Distance;
import com.example.jgaug.descubracuritiba.Api.Response.DistanciaResponse;
import com.example.jgaug.descubracuritiba.Api.Response.Element;
import com.example.jgaug.descubracuritiba.Api.Response.Row;
import com.example.jgaug.descubracuritiba.Api.endpoint.distanciaApi;
import com.example.jgaug.descubracuritiba.Fragments.DatePickerFragment;
import com.example.jgaug.descubracuritiba.Fragments.TimePickerFragment;
import com.example.jgaug.descubracuritiba.Helpers.Dia;
import com.example.jgaug.descubracuritiba.Helpers.Itinerário;
import com.example.jgaug.descubracuritiba.Helpers.Place;
import com.example.jgaug.descubracuritiba.Helpers.PlaceGroup;
import com.example.jgaug.descubracuritiba.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreateItinerary extends AppCompatActivity {
    private boolean parksSelected = false;
    private boolean landmarksSelected = false;
    private boolean museumsSelected = false;
    private boolean shoppingSelected = false;
    private boolean foodsSelected = false;
    private Calendar startDay = Calendar.getInstance( );
    private Calendar endDay = Calendar.getInstance( );
    public int mudouTempoinicio = 0;
    public int mudouTempofim = 0;
    private ProgressDialog progressDialog;
    public Integer distancia;

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_create_itinerary );
    }

    public void btnSelectPlaceOnMap( View view ) {
        Toast.makeText( this, "Não implementado", Toast.LENGTH_SHORT ).show( );
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
            mudouTempoinicio = 1;
        } else {
            this.endDay.set( Calendar.HOUR_OF_DAY, hourOfDay );
            this.endDay.set( Calendar.MINUTE, minute );
            mudouTempofim = 1;
        }
    }

    public Calendar getStartDay( ) {
        return startDay;
    }

    public void onSelectPlacesToVisit( View view ) {
        switch( view.getId( ) ) {
            case R.id.imageViewParks:
                parksSelected = !parksSelected;
                changeImageViewResource( parksSelected, R.id.imageViewParks, R.drawable.parks_checked, R.drawable.parks_unchecked );
                break;
            case R.id.imageViewLandmarks:
                landmarksSelected = !landmarksSelected;
                changeImageViewResource( landmarksSelected, R.id.imageViewLandmarks, R.drawable.landmarks_checked, R.drawable.landmarks_unchecked );
                break;
            case R.id.imageViewMuseums:
                museumsSelected = !museumsSelected;
                changeImageViewResource( museumsSelected, R.id.imageViewMuseums, R.drawable.museums_checked, R.drawable.museums_unchecked );
                break;
            case R.id.imageViewShopping:
                shoppingSelected = !shoppingSelected;
                changeImageViewResource( shoppingSelected, R.id.imageViewShopping, R.drawable.shopping_checked, R.drawable.shopping_unchecked );
                break;
            case R.id.imageViewFood:
                foodsSelected = !foodsSelected;
                changeImageViewResource( foodsSelected, R.id.imageViewFood, R.drawable.food_checked, R.drawable.food_unchecked );
                break;
        }
    }

    private void changeImageViewResource( boolean placeSelected, int imageViewId, int checkedImageViewId, int uncheckedImageViewId ) {
        ImageView imageView = ( ImageView ) findViewById( imageViewId );
        if( placeSelected ) {
            imageView.setImageResource( checkedImageViewId );
        } else {
            imageView.setImageResource( uncheckedImageViewId );
        }
    }

    public void btnMakeItinerary( View view ) {
        if( checkConstraints( ) ) {
            progressDialog = ProgressDialog.show( this, "", "Carregando", true );

            long diff = endDay.getTimeInMillis( ) - startDay.getTimeInMillis( ); //result in millis
            final long numberOfDays = ( diff / ( 24 * 60 * 60 * 1000 ) ) + 1;
            mudouTempofim = 0;
            mudouTempoinicio = 0;

            final Intent intent = new Intent( this, Itinerary.class );
            intent.putExtra( "startHour", startDay.get( Calendar.HOUR_OF_DAY ) );
            intent.putExtra( "startMinute", startDay.get( Calendar.MINUTE ) );
            intent.putExtra( "endHour", endDay.get( Calendar.HOUR_OF_DAY ) );
            intent.putExtra( "endMinute", endDay.get( Calendar.MINUTE ) );
            intent.putExtra( "numberOfDays", ( int ) numberOfDays );

            final FirebaseDatabase database = FirebaseDatabase.getInstance( );
            DatabaseReference ref = database.getReference( "" );

            // Attach a listener to read the data at our posts reference
            ref.child( "places" ).addValueEventListener( new ValueEventListener( ) {
                @Override
                public void onDataChange( DataSnapshot dataSnapshot ) {
                    List selectedPlaces = getSelectedPlaces( dataSnapshot );
                    Itinerário itinerary = makeItinerary( selectedPlaces, numberOfDays );

                    saveItinerary( itinerary );

                    intent.putExtra( "places", itinerary);
//                    intent.putExtra("distancia", distancia);

                    progressDialog.dismiss( );

                    CreateItinerary.this.startActivity( intent );
                }

                @Override
                public void onCancelled( DatabaseError databaseError ) {
                    System.out.println( "The read failed: " + databaseError.getCode( ) );
                }
            } );
        }
    }

    private boolean checkConstraints( ) {
        //TODO: Fazer um limite de seleção de dias... se selecionar só gastronômicos por exemplo, não deixar mais de 1 dia

        if( !parksSelected && !landmarksSelected && !museumsSelected && !shoppingSelected && !foodsSelected ) {
            Toast.makeText( this, "Para gerar o itinerário, selecione ao menos um grupo de locais para visita!", Toast.LENGTH_SHORT ).show( );
        } else {
            long diff = endDay.getTimeInMillis( ) - startDay.getTimeInMillis( ); //result in millis

            if( startDay.getTimeInMillis( ) == 0 ) {
                Toast.makeText( this, "Preencha o primeiro dia!", Toast.LENGTH_SHORT ).show( );
            } else if( endDay.getTimeInMillis( ) == 0 ) {
                Toast.makeText( this, "Preencha o último dia!", Toast.LENGTH_SHORT ).show( );
            } else if( diff < 0 ) {
                Toast.makeText( this, "O último dia tem que vir depois do primeiro!", Toast.LENGTH_SHORT ).show( );
            } else if( mudouTempoinicio == 0 ) {
                Toast.makeText( this, "Preencha o horário de início!", Toast.LENGTH_SHORT ).show( );
            } else if( mudouTempofim == 0 ) {
                Toast.makeText( this, "Preencha o horário de fim!", Toast.LENGTH_SHORT ).show( );
            } else {
                return true;
            }
        }

        return false;
    }

    private List getSelectedPlaces( DataSnapshot dataSnapshot ) {
        List selectedPlaces =  new ArrayList< Place >( );

        for( DataSnapshot placeDataSnapshot : dataSnapshot.getChildren( ) ) {
            Place place = placeDataSnapshot.getValue( Place.class );

            boolean belongsToSelectedGroup = ( place.placeGroup.contains( PlaceGroup.PARKS ) && parksSelected ) ||
                ( place.placeGroup.contains( PlaceGroup.LANDMARKS ) && landmarksSelected ) ||
                ( place.placeGroup.contains( PlaceGroup.MUSEUMS ) && museumsSelected ) ||
                ( place.placeGroup.contains( PlaceGroup.SHOPPING ) && shoppingSelected ) ||
                ( place.placeGroup.contains( PlaceGroup.FOOD ) && foodsSelected );

            if( belongsToSelectedGroup ) {
                selectedPlaces.add( place );
            }
        }

        return selectedPlaces;
    }

    //Cria um itinerário teste, com os locais em sequência conforme obtidos do banco
    private Itinerário makeItinerary( List selectedPlaces, long numberOfDays ) {
        ArrayList<Dia> itinerary = new ArrayList<>();
        Dia dia = new Dia();
        int placeIndex = 0;

        getDistancia("-25.438029,-49.26347","-25.4392404,-49.2347639");

        for( int day = 0; day < numberOfDays; day++ ) {
            Calendar startTime = ( Calendar ) startDay.clone();
            startTime.add( Calendar.DAY_OF_YEAR, day );

            Calendar endTime = ( Calendar ) startDay.clone();
            endTime.add( Calendar.DAY_OF_YEAR, day );
            endTime.set( Calendar.HOUR_OF_DAY, endDay.get( Calendar.HOUR_OF_DAY ) );
            endTime.set( Calendar.MINUTE, endDay.get( Calendar.MINUTE ) );

            ArrayList<Place> dailyItinerary = new ArrayList<>( );
            do {
                ( ( Place ) selectedPlaces.get( placeIndex ) ).setStartTime( ( Calendar ) startTime.clone() );

                dailyItinerary.add( (Place) selectedPlaces.get( placeIndex ) );

                int visitTime = ( ( Place ) selectedPlaces.get( placeIndex ) ).getVisitTime( );
                startTime.add( Calendar.MINUTE, visitTime + 10 ); //10 é o tempo de deslocamento padrão de um lugar para outro

                placeIndex++;
            } while( endTime.after( startTime ) && placeIndex < (selectedPlaces.size()-1) );

            dia.setListPlaces(dailyItinerary);
            itinerary.add(dia);
        }

        Itinerário itinerário = new Itinerário();
        itinerário.setDias(itinerary);

        return itinerário;
    }

    private void saveItinerary( Itinerário itinerary ) {
        SharedPreferences settings = getSharedPreferences( "mySharedPreferences", MODE_PRIVATE );
        SharedPreferences.Editor editor = settings.edit();

        String teste = new Gson().toJson( itinerary );
        editor.putString( "itinerary", teste );

        // Commit the edits!
        editor.apply();
    }

    public void getDistancia(String latlonOrigem, String latlonDest){
        final distanciaApi distanciaEndpoint = new DescubraCuritibaApi()
                .distanciaApi();

        final Integer[] distanciaaux = new Integer[1];

        retrofit2.Call<DistanciaResponse> call;

        call = distanciaEndpoint.getDistancia(latlonOrigem,latlonDest,"AIzaSyA2yt9xJV1gwgqJTpn-zUKnKIMK44iRCJA");

        call.enqueue(new Callback<DistanciaResponse>() {
            @Override
            public void onResponse(Call<DistanciaResponse> call, Response<DistanciaResponse> response) {
                DistanciaResponse distanciaResponse = response.body();

                List<Row> lista = distanciaResponse.getRows();
                Row row = lista.get(0);
                List<Element> elementList = row.getElements();
                Element element = elementList.get(0);
                Distance distance = element.getDistance();
                distancia = distance.getValue();
            }

            @Override
            public void onFailure(Call<DistanciaResponse> call, Throwable t) {

            }
        });
    }
}
