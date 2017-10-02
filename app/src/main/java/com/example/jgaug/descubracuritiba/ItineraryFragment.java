package com.example.jgaug.descubracuritiba;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
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
//        TextView textView = ( TextView ) rootView.findViewById( R.id.section_label );
//        textView.setText( getString( R.string.section_format, getArguments( ).getInt( ARG_SECTION_NUMBER ) ) );

        ListView listView = ( ListView ) view.findViewById( R.id.itinerary_list_view );

        List< Place > placesToVisit = getPlacesToVisit( );
        CustomAdapter customAdapter = new CustomAdapter( getActivity( ), placesToVisit );
        listView.setAdapter( customAdapter );

        listView.setOnItemClickListener( new AdapterView.OnItemClickListener( ) {
            @Override
            public void onItemClick( AdapterView< ? > parent, View view, int position, long id ) {
                Toast.makeText( getActivity( ), "Position: " + position, Toast.LENGTH_SHORT ).show( );
            }
        } );
        return view;
    }

    private List< Place > getPlacesToVisit( ) {
        List< Place > placesToVisit = new ArrayList<>( );
        placesToVisit.add( new Place( "1 - Jardim Botânico", "Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat." ) );
        placesToVisit.add( new Place( "2 - Ópera de Arame", "Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat." ) );
        placesToVisit.add( new Place( "3 - Largo da Ordem", "Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat." ) );
        placesToVisit.add( new Place( "4 - Praça Tiradentes", "Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat." ) );

        return placesToVisit;
    }
}

