package com.example.jgaug.descubracuritiba.Helpers;

import java.io.Serializable;
import java.util.ArrayList;

public class DailyItineraryList implements Serializable {
    private ArrayList< DailyItinerary > itinerary;

    public DailyItineraryList( ) {
        this.itinerary = new ArrayList<>( );
    }

    public ArrayList< DailyItinerary > getItinerary( ) {
        return itinerary;
    }

    public void addDailyItinerary( DailyItinerary dailyItinerary ) {
        this.itinerary.add( dailyItinerary );
    }
}
