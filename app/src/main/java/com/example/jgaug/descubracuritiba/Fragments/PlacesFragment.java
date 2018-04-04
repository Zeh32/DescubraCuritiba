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
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.jgaug.descubracuritiba.Activities.FirstScreen;
import com.example.jgaug.descubracuritiba.Activities.Itinerary;
import com.example.jgaug.descubracuritiba.Activities.PlaceDetails;
import com.example.jgaug.descubracuritiba.Activities.PlacesList;
import com.example.jgaug.descubracuritiba.Api.DescubraCuritibaApi;
import com.example.jgaug.descubracuritiba.Api.Response.DistanciaResponse;
import com.example.jgaug.descubracuritiba.CustomAdapter;
import com.example.jgaug.descubracuritiba.Helpers.DailyItinerary;
import com.example.jgaug.descubracuritiba.Helpers.Place;
import com.example.jgaug.descubracuritiba.PlacesAdapter;
import com.example.jgaug.descubracuritiba.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PlacesFragment extends Fragment {

    private RelativeLayout loading;
    private RecyclerView recyclerView;
    public List< Place > selectedPlaces = new ArrayList<>();
    private DailyItinerary dailyItinerary = new DailyItinerary();

    public PlacesFragment( ) {
    }

    public static PlacesFragment newInstance( int sectionNumber ) {
        PlacesFragment fragment = new PlacesFragment( );

        Bundle args = new Bundle( );
        args.putInt( "sectionNumber", sectionNumber );
        fragment.setArguments( args );

        return fragment;
    }

    @Override
    public View onCreateView( @NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState ) {
        View view = inflater.inflate( R.layout.fragment_places, container, false );
        loading = (RelativeLayout) view.findViewById(R.id.loading);
        recyclerView = view.findViewById( R.id.places_recycler_view );

        loading.setVisibility(View.VISIBLE);

        getAllPlaces();

        return view;
    }

    private List< Place > getSelectedPlaces(DataSnapshot dataSnapshot ) {
        List< Place > selectedPlaces = new ArrayList<>( );

        for( DataSnapshot placeDataSnapshot : dataSnapshot.getChildren( ) ) {
            Place place = placeDataSnapshot.getValue( Place.class );
            selectedPlaces.add( place );
        }

        //Order selected places in descending order by relevance
        Collections.sort( selectedPlaces, (place1, place2 ) -> place2.getRelevance( ) - place1.getRelevance( ) );

        return selectedPlaces;
    }

    public void getAllPlaces(){
        final FirebaseDatabase database = FirebaseDatabase.getInstance( );
        DatabaseReference ref = database.getReference( "" );

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                selectedPlaces = getSelectedPlaces( dataSnapshot.child( "places" ) );
                for(int i = 0; i<selectedPlaces.size();i++){
                    dailyItinerary.addPlace(selectedPlaces.get(i));
                }
                PlacesAdapter placesAdapter = new PlacesAdapter( getActivity( ), dailyItinerary );

                loading.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);
                recyclerView.setAdapter( placesAdapter );
                recyclerView.setLayoutManager( new LinearLayoutManager( getContext( ), LinearLayoutManager.VERTICAL, false ) );

                placesAdapter.setOnItemClickListener( new PlacesAdapter.OnItemClickListener( ) {
                    @Override
                    public void onClickDetalhes(int position) {
                        Intent intent = new Intent(getActivity(), PlaceDetails.class);
                        intent.putExtra("place", dailyItinerary.getPlaces().get(position));
                        startActivity(intent);
                    }

                    @Override
                    public void onClickGo(int position) {
                        String coordinates = dailyItinerary.getPlaces( ).get( position ).getCoordinates( );
                        String uri = "geo:0,0?q=" + android.net.Uri.encode( coordinates, "UTF-8" );
                        Intent mapIntent = new Intent( Intent.ACTION_VIEW, Uri.parse( uri ) );

                        if( mapIntent.resolveActivity( getActivity( ).getPackageManager( ) ) != null ) {
                            startActivity( mapIntent );
                        }
                    }
                });
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}