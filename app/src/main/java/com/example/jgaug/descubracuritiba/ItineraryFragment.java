package com.example.jgaug.descubracuritiba;

/**
 * Created by Administrador on 18/08/2017.
 */

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class ItineraryFragment extends Fragment {

    public ItineraryFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.itinerary_fragment, container, false);
        GridView gridview = (GridView)view.findViewById(R.id.gridview);

        List<ItemObject> allItems = getAllItemObject();
        CustomAdapter customAdapter = new CustomAdapter(getActivity(), allItems);
        gridview.setAdapter(customAdapter);

        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getActivity(), "Position: " + position, Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }

    private List<ItemObject> getAllItemObject(){
        List<ItemObject> items = new ArrayList<>();
        items.add(new ItemObject(R.drawable.milian,"Dip It Low", "Christina Milian"));
        items.add(new ItemObject(R.drawable.adele,"Someone like you", "Adele Adkins"));
        items.add(new ItemObject(R.drawable.ciara,"Ride", "Ciara"));
        items.add(new ItemObject(R.drawable.gaga,"Paparazzi", "Lady Gaga"));
        items.add(new ItemObject(R.drawable.brown,"Forever", "Chris Brown"));
        items.add(new ItemObject(R.drawable.rihana,"Stay", "Rihanna"));
        items.add(new ItemObject(R.drawable.jason,"Marry me", "Jason Derulo"));
        items.add(new ItemObject(R.drawable.shakira,"Waka Waka", "Shakira"));
        items.add(new ItemObject(R.drawable.perry,"Dark Horse", "Katy Perry"));
        items.add(new ItemObject(R.drawable.milian,"Dip It Low", "Christina Milian"));
        items.add(new ItemObject(R.drawable.milian,"Dip It Low", "Christina Milian"));
        items.add(new ItemObject(R.drawable.milian,"Dip It Low", "Christina Milian"));
        return items;
    }

}

