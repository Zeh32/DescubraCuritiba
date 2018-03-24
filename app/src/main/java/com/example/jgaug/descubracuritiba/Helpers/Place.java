package com.example.jgaug.descubracuritiba.Helpers;

import com.google.firebase.database.Exclude;

import java.io.Serializable;
import java.util.Calendar;
import java.util.List;

public class Place implements Serializable {
    public int id;
    public int relevance;
    public int visitTime;
    public double latitude;
    public double longitude;
    public boolean weatherDependent;
    public String name;
    public String description;
    public String image;
    public List< Integer > placeGroup;
    @Exclude public Calendar startTime;
    @Exclude public int travelTimeFromOrigin; //in minutes
    @Exclude public int travelTimeFromPreviousPlaceByCar; //in minutes
    @Exclude public int travelTimeFromPreviousPlaceOnFoot; //in minutes
    @Exclude public boolean goingOnFoot;

    //Firebase needs an empty constructor in order to map places back from database
    public Place( ) {
    }

    public Place( int id, String name, String image, double latitude, double longitude, boolean weatherDependent, int relevance, int visitTime, List< Integer > placeGroup, String description ) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.latitude = latitude;
        this.longitude = longitude;
        this.weatherDependent = weatherDependent;
        this.relevance = relevance;
        this.visitTime = visitTime;
        this.placeGroup = placeGroup;
        this.description = description;
        this.startTime = null;
        this.travelTimeFromPreviousPlaceByCar = 0;
        this.travelTimeFromOrigin = 0;
        this.goingOnFoot = false;
    }

    public int getId( ) {
        return id;
    }

    public String getImage( ) {
        return image;
    }

    public String getName( ) {
        return name;
    }

    public String getDescription( ) {
        return description;
    }

    public double getLatitude( ) {
        return latitude;
    }

    public double getLongitude( ) {
        return longitude;
    }

    public int getVisitTime( ) {
        return visitTime;
    }

    public List< Integer > getPlaceGroup( ) {
        return placeGroup;
    }

    public int getRelevance( ) {
        return relevance;
    }

    public boolean isWeatherDependent( ) {
        return weatherDependent;
    }

    public void setStartTime( Calendar startTime ) {
        this.startTime = ( Calendar ) startTime.clone( );
    }

    public int getTravelTimeFromPreviousPlaceByCar( ) {
        return travelTimeFromPreviousPlaceByCar;
    }

    public void setTravelTimeFromPreviousPlaceByCar( int travelTimeFromPreviousPlaceByCar ) {
        this.travelTimeFromPreviousPlaceByCar = travelTimeFromPreviousPlaceByCar;
    }

    public int getTravelTimeFromOrigin( ) {
        return travelTimeFromOrigin;
    }

    public void setTravelTimeFromOrigin( int travelTimeFromOrigin ) {
        this.travelTimeFromOrigin = travelTimeFromOrigin;
    }

    public int getTravelTimeFromPreviousPlaceOnFoot( ) {
        return travelTimeFromPreviousPlaceOnFoot;
    }

    public void setTravelTimeFromPreviousPlaceOnFoot( int travelTimeFromPreviousPlaceOnFoot ) {
        this.travelTimeFromPreviousPlaceOnFoot = travelTimeFromPreviousPlaceOnFoot;
    }

    public boolean isGoingOnFoot( ) {
        return goingOnFoot;
    }

    @Exclude
    public void changeTransportMode( ) {
        this.goingOnFoot = !this.goingOnFoot;
    }

    @Exclude
    public String getVisitPeriod( ) {
        Calendar clonedStartTime = ( Calendar ) startTime.clone();
        String formattedHour = String.format( "%02d", clonedStartTime.get( Calendar.HOUR_OF_DAY ) );
        String formattedMinute = String.format( "%02d", clonedStartTime.get( Calendar.MINUTE ) );

        String visitPeriod = formattedHour + ":" + formattedMinute + " - ";

        clonedStartTime.add( Calendar.MINUTE, visitTime );
        formattedHour = String.format( "%02d", clonedStartTime.get( Calendar.HOUR_OF_DAY ) );
        formattedMinute = String.format( "%02d", clonedStartTime.get( Calendar.MINUTE ) );

        visitPeriod += formattedHour + ":" + formattedMinute;

        return visitPeriod;
    }
}
