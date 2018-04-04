package com.example.jgaug.descubracuritiba.Fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import android.zetterstrom.com.forecast.ForecastClient;
import android.zetterstrom.com.forecast.ForecastConfiguration;
import android.zetterstrom.com.forecast.models.Forecast;
import android.zetterstrom.com.forecast.models.Language;
import android.zetterstrom.com.forecast.models.Unit;

import com.example.jgaug.descubracuritiba.Activities.CreateItinerary;
import com.example.jgaug.descubracuritiba.Activities.DailyForecast;
import com.example.jgaug.descubracuritiba.Activities.Itinerary;
import com.example.jgaug.descubracuritiba.Activities.PlaceDetails;
import com.example.jgaug.descubracuritiba.Api.DescubraCuritibaApi;
import com.example.jgaug.descubracuritiba.Api.Response.DistanciaResponse;
import com.example.jgaug.descubracuritiba.CustomAdapter;
import com.example.jgaug.descubracuritiba.Helpers.DailyItinerary;
import com.example.jgaug.descubracuritiba.Helpers.Place;
import com.example.jgaug.descubracuritiba.R;

import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ItineraryFragment extends Fragment {
    public ItineraryFragment( ) {
    }

    public static ItineraryFragment newInstance( int sectionNumber ) {
        ItineraryFragment fragment = new ItineraryFragment( );

        Bundle args = new Bundle( );
        args.putInt( "sectionNumber", sectionNumber );
        fragment.setArguments( args );

        return fragment;
    }

    @Override
    public View onCreateView( @NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState ) {
        View view = inflater.inflate( R.layout.fragment_itinerary, container, false );

        final DailyItinerary dailyItinerary = ( ( Itinerary ) getActivity( ) ).getDailyItinerary( getArguments( ).getInt( "sectionNumber" ) );
        CustomAdapter customAdapter = new CustomAdapter( getActivity( ), dailyItinerary );

        RecyclerView recyclerView = view.findViewById( R.id.itinerary_recycler_view );
        recyclerView.setAdapter( customAdapter );
        recyclerView.setLayoutManager( new LinearLayoutManager( getContext( ), LinearLayoutManager.VERTICAL, false ) );

        customAdapter.setOnItemClickListener( new CustomAdapter.OnItemClickListener( ) {
            @Override
            public void onClickDetalhes( int position ) {
                Intent intent = new Intent( getActivity( ), PlaceDetails.class );
                intent.putExtra( "place", dailyItinerary.getPlaces( ).get( position ) );
                startActivity( intent );
            }

            @Override
            public void onClickClima( int position ) {
                Calendar todayAtMidnight = Calendar.getInstance( );
                todayAtMidnight.set( Calendar.HOUR_OF_DAY, 0 );
                todayAtMidnight.set( Calendar.MINUTE, 0 );
                Calendar firstPlaceStartTime = dailyItinerary.getPlaces( ).get( 0 ).getStartTime( );

                if( todayAtMidnight.after( firstPlaceStartTime ) ) {
                    Toast.makeText( getActivity( ), "Não é possível visualizar a previsão do tempo de um dia passado.", Toast.LENGTH_LONG ).show( );
                } else {
                    int forecastArrayPosition = CreateItinerary.getNumberOfDays( firstPlaceStartTime, todayAtMidnight );
                    if( forecastArrayPosition > 7 ) {
                        Toast.makeText( getActivity( ), "A previsão do tempo está disponível apenas para os próximos 7 dias.", Toast.LENGTH_LONG ).show( );
                    } else {
                        ForecastConfiguration configuration = new ForecastConfiguration.Builder( "cc8d5c7bd7bd6815677077038773bb58" ).setDefaultLanguage( Language.PORTUGUESE ).setDefaultUnit( Unit.CA ).setCacheDirectory( getActivity( ).getCacheDir( ) ).build( );
                        ForecastClient.create( configuration );

                        //Latitude e longitude do centro de Curitiba
                        ForecastClient.getInstance( ).getForecast( -25.4247427, -49.2763924, new Callback< Forecast >( ) {
                            @Override
                            public void onResponse( @NonNull Call< Forecast > call, @NonNull Response< Forecast > response ) {
                                if( !response.isSuccessful( ) ) {
                                    Toast.makeText( getActivity( ), "Houve uma falha ao obter as informações de previsão do tempo.", Toast.LENGTH_SHORT ).show( );
                                } else {
                                    Forecast forecast = response.body( );

                                    Intent intent = new Intent( getActivity( ), DailyForecast.class );
                                    intent.putExtra( "dailyForecast", forecast.getDaily( ).getDataPoints( ).get( forecastArrayPosition ) );
                                    startActivity( intent );
                                }
                            }

                            @Override
                            public void onFailure( @NonNull Call< Forecast > call, @NonNull Throwable t ) {
                                Toast.makeText( getActivity( ), "Houve uma falha ao obter as informações de previsão do tempo.", Toast.LENGTH_LONG ).show( );
                            }
                        } );
                    }
                }
            }

            @Override
            public void onClickNavegar( int position ) {
                String coordinates = dailyItinerary.getPlaces( ).get( position ).getCoordinates( );
                String uri = "geo:0,0?q=" + android.net.Uri.encode( coordinates, "UTF-8" );
                Intent mapIntent = new Intent( Intent.ACTION_VIEW, Uri.parse( uri ) );

                if( mapIntent.resolveActivity( getActivity( ).getPackageManager( ) ) != null ) {
                    startActivity( mapIntent );
                }
            }

            @Override
            public void onClickChangeTransportMode( int position ) {
                Place currentPlace = dailyItinerary.getPlaces( ).get( position );
                Place nextPlace = dailyItinerary.getPlaces( ).get( position + 1 );
                currentPlace.changeTransportMode( );

                if( currentPlace.isGoingOnFoot( ) ) {
                    retrofit2.Call< DistanciaResponse > call = new DescubraCuritibaApi( ).distanciaApi( ).getDistancia( "pt-BR", "walking", currentPlace.getCoordinates( ), nextPlace.getCoordinates( ), "AIzaSyA2yt9xJV1gwgqJTpn-zUKnKIMK44iRCJA" );
                    call.enqueue( new Callback< DistanciaResponse >( ) {
                        @Override
                        public void onResponse( @NonNull Call< DistanciaResponse > call, @NonNull Response< DistanciaResponse > response ) {
                            DistanciaResponse distanciaResponse = response.body( );
                            int travelTimeOnFoot = distanciaResponse.getRows( ).get( 0 ).getElements( ).get( 0 ).getDuration( ).getValue( ) / 60;
                            nextPlace.setTravelTimeFromPreviousPlaceOnFoot( travelTimeOnFoot );

                            int travelTimeOffset = nextPlace.getTravelTimeFromPreviousPlaceOnFoot( ) - nextPlace.getTravelTimeFromPreviousPlaceByCar( );
                            updateStartTimes( position, travelTimeOffset, dailyItinerary );

                            customAdapter.notifyDataSetChanged( );
                        }

                        @Override
                        public void onFailure( @NonNull Call< DistanciaResponse > call, @NonNull Throwable t ) {
                            Toast.makeText( getActivity( ), "Houve uma falha ao obter o tempo de deslocamento a pé.", Toast.LENGTH_LONG ).show( );
                        }
                    } );
                } else {
                    int travelTimeOffset = nextPlace.getTravelTimeFromPreviousPlaceByCar( ) - nextPlace.getTravelTimeFromPreviousPlaceOnFoot( );
                    updateStartTimes( position, travelTimeOffset, dailyItinerary );

                    customAdapter.notifyDataSetChanged( );
                }
            }
        } );

        return view;
    }

    private void updateStartTimes( int position, int travelTimeOffset, DailyItinerary dailyItinerary ) {
        for( int placeIndex = position + 1; placeIndex < dailyItinerary.getPlaces( ).size( ); placeIndex++ ) {
            dailyItinerary.getPlaces( ).get( placeIndex ).getStartTime( ).add( Calendar.MINUTE, travelTimeOffset );
        }
    }
}