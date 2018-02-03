package com.example.jgaug.descubracuritiba.Helpers;

import java.io.Serializable;
import java.util.ArrayList;

public class DailyItinerary implements Serializable {
    private ArrayList< Place > places;

    public DailyItinerary( ) {
        this.places = new ArrayList<>( );
    }

    public ArrayList< Place > getPlaces( ) {
        return places;
    }

    public void addPlace( Place place ) {
        this.places.add( place );
    }
}
