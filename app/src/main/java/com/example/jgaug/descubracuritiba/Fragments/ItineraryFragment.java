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
import android.widget.ImageView;
import android.widget.Toast;

import com.example.jgaug.descubracuritiba.Activities.Itinerary;
import com.example.jgaug.descubracuritiba.Api.DescubraCuritibaApi;
import com.example.jgaug.descubracuritiba.Api.Response.DistanciaResponse;
import com.example.jgaug.descubracuritiba.CustomAdapter;
import com.example.jgaug.descubracuritiba.Helpers.DailyItinerary;
import com.example.jgaug.descubracuritiba.R;

import java.util.Locale;

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
                Toast.makeText( getActivity( ), "Não implementado", Toast.LENGTH_SHORT ).show( );
            }

            @Override
            public void onClickClima( int position ) {
                Toast.makeText( getActivity( ), "Não implementado", Toast.LENGTH_SHORT ).show( );
            }

            @Override
            public void onClickNavegar( int position ) {
                String coordinates = dailyItinerary.getPlaces( ).get( position ).getCoordinates( );
                String uri = String.format( Locale.ENGLISH, "geo:0,0?q=" ) + android.net.Uri.encode( coordinates, "UTF-8" );
                Intent mapIntent = new Intent( Intent.ACTION_VIEW, Uri.parse( uri ) );

                if( mapIntent.resolveActivity( getActivity( ).getPackageManager( ) ) != null ) {
                    startActivity( mapIntent );
                }
            }

            @Override
            public void onClickChangeTransport( int position, ImageView transportImage ) {
                dailyItinerary.getPlaces( ).get( position ).changeTransportMode( );

                //Only get travelTimeFromPreviousPlaceOnFoot in the first time
                if( dailyItinerary.getPlaces( ).get( position ).isGoingOnFoot( ) && dailyItinerary.getPlaces( ).get( position + 1 ).getTravelTimeFromPreviousPlaceOnFoot() == 0 ) {
                    String originCoordinates = dailyItinerary.getPlaces( ).get( position ).getCoordinates( );
                    String destinationCoordinates = dailyItinerary.getPlaces( ).get( position + 1 ).getCoordinates( );

                    retrofit2.Call< DistanciaResponse > call = new DescubraCuritibaApi( ).distanciaApi( ).getDistancia( "pt-BR", "walking", originCoordinates, destinationCoordinates, "AIzaSyA2yt9xJV1gwgqJTpn-zUKnKIMK44iRCJA" );
                    call.enqueue( new Callback< DistanciaResponse >( ) {
                        @Override
                        public void onResponse( @NonNull Call< DistanciaResponse > call, @NonNull Response< DistanciaResponse > response ) {
                            DistanciaResponse distanciaResponse = response.body( );
                            int onFootTravelTime = distanciaResponse.getRows( ).get( 0 ).getElements( ).get( 0 ).getDuration( ).getValue( ) / 60;
                            dailyItinerary.getPlaces( ).get( position + 1 ).setTravelTimeFromPreviousPlaceOnFoot( onFootTravelTime );

                            customAdapter.notifyDataSetChanged( );
                        }

                        @Override
                        public void onFailure( @NonNull Call< DistanciaResponse > call, @NonNull Throwable t ) {
                            Toast.makeText( getActivity( ), "Houve uma falha ao obter o tempo de deslocamento a pé.", Toast.LENGTH_LONG ).show( );
                        }
                    } );
                } else {
                    customAdapter.notifyDataSetChanged( );
                }
            }
        } );

        return view;
    }
}