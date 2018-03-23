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

import com.example.jgaug.descubracuritiba.Activities.Itinerary;
import com.example.jgaug.descubracuritiba.CustomAdapter;
import com.example.jgaug.descubracuritiba.Helpers.DailyItinerary;
import com.example.jgaug.descubracuritiba.R;

import java.util.Locale;

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

        final DailyItinerary placesToVisit = ( ( Itinerary ) getActivity( ) ).getDailyItinerary( getArguments( ).getInt( "sectionNumber" ) );

//        int[][] modes = new int[ 7 ][ 20 ];
//        for( int i = 0; i < 20; i++ ) {
//            modes[ getArguments( ).getInt( "sectionNumber" ) ][ i ] = 0;
//        }

        CustomAdapter customAdapter = new CustomAdapter( getActivity( ), placesToVisit );

        RecyclerView recyclerView = view.findViewById( R.id.itinerary_recycler_view );
        recyclerView.setAdapter( customAdapter );
        recyclerView.setLayoutManager( new LinearLayoutManager( getContext( ), LinearLayoutManager.VERTICAL, false ) );

        customAdapter.setOnItemClickListener( new CustomAdapter.OnItemClickListener( ) {
            @Override
            public void onClickDetalhes( int position ) {
                Toast.makeText( getActivity( ), "Position: " + position, Toast.LENGTH_SHORT ).show( );
            }

            @Override
            public void onClickClima( int position ) {
                Toast.makeText( getActivity( ), "Position: " + position, Toast.LENGTH_SHORT ).show( );
            }

            @Override
            public void onClickNavegar( int position ) {
                double latitude = placesToVisit.getPlaces( ).get( position ).getLatitude( );
                double longitude = placesToVisit.getPlaces( ).get( position ).getLongitude( );

                String uri = String.format( Locale.ENGLISH, "geo:0,0?q=" ) + android.net.Uri.encode( String.format( "%s,%s", Double.toString( latitude ), Double.toString( longitude ) ), "UTF-8" );
                Intent mapIntent = new Intent( Intent.ACTION_VIEW, Uri.parse( uri ) );

                if( mapIntent.resolveActivity( getActivity( ).getPackageManager( ) ) != null ) {
                    startActivity( mapIntent );
                }
            }

//            @Override
//            public void onClickMode( int position ) {
//                if( modes[ getArguments( ).getInt( "sectionNumber" ) ][ position ] == 0 ) {
//                    modes[ getArguments( ).getInt( "sectionNumber" ) ][ position ] = 1;
//                } else {
//                    modes[ getArguments( ).getInt( "sectionNumber" ) ][ position ] = 0;
//                }
//
//                customAdapter.notifyDataSetChanged( );
//            }
        } );

        return view;
    }
}