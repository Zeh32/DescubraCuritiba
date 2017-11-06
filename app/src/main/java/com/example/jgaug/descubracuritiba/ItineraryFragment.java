package com.example.jgaug.descubracuritiba;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.jgaug.descubracuritiba.Helpers.Place;

import java.util.List;

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
    public View onCreateView( LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState ) {
        View view = inflater.inflate( R.layout.fragment_itinerary, container, false );

        List< Place > placesToVisit = ( ( Itinerary ) getActivity( ) ).getPlacesToVisit( getArguments( ).getInt( "sectionNumber" ) );
        CustomAdapter customAdapter = new CustomAdapter( getActivity( ), placesToVisit );

        ListView listView = ( ListView ) view.findViewById( R.id.itinerary_list_view );
        listView.setAdapter( customAdapter );

        listView.setOnItemClickListener( new AdapterView.OnItemClickListener( ) {
            @Override
            public void onItemClick( AdapterView< ? > parent, View view, int position, long id ) {
                Toast.makeText( getActivity( ), "Position: " + position, Toast.LENGTH_SHORT ).show( );
            }
        } );

        return view;
    }
}