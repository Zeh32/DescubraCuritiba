package com.example.jgaug.descubracuritiba.Helpers;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;

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

    public Calendar getFirstPlaceStartTime( ) {
        return itinerary.get( 0 ).getPlaces().get( 0 ).startTime;
    }
}
