package com.example.jgaug.descubracuritiba.Helpers;

import com.google.firebase.database.Exclude;

import java.io.Serializable;
import java.util.Calendar;
import java.util.List;

public class Place implements Serializable {
    public int id;
    public String name;
    public String image;
    public String coordinates;
    public boolean weatherDependent;
    public int relevance;
    public int visitTime;
    public List< Integer > placeGroup;
    public String description;
    @Exclude public Calendar startTime;
    @Exclude public int travelTimeFromOrigin; //in minutes
    @Exclude public int travelTimeFromPreviousPlaceByCar; //in minutes
    @Exclude public int travelTimeFromPreviousPlaceOnFoot; //in minutes
    @Exclude public boolean goingOnFoot;

    //Firebase needs an empty constructor in order to map places back from database
    public Place( ) {
    }

    public Place( int id, String name, String image, String coordinates, boolean weatherDependent, int relevance, int visitTime, List< Integer > placeGroup, String description ) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.coordinates = coordinates;
        this.weatherDependent = weatherDependent;
        this.relevance = relevance;
        this.visitTime = visitTime;
        this.placeGroup = placeGroup;
        this.description = description;
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

    public String getCoordinates( ) {
        return coordinates;
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

    @Exclude
    public Calendar getStartTime( ) {
        return startTime;
    }

    @Exclude
    public void setStartTime( Calendar startTime ) {
        this.startTime = ( Calendar ) startTime.clone( );
    }

    @Exclude
    public int getTravelTimeFromPreviousPlaceByCar( ) {
        return travelTimeFromPreviousPlaceByCar;
    }

    @Exclude
    public void setTravelTimeFromPreviousPlaceByCar( int travelTimeFromPreviousPlaceByCar ) {
        this.travelTimeFromPreviousPlaceByCar = travelTimeFromPreviousPlaceByCar;
    }

    @Exclude
    public int getTravelTimeFromOrigin( ) {
        return travelTimeFromOrigin;
    }

    @Exclude
    public void setTravelTimeFromOrigin( int travelTimeFromOrigin ) {
        this.travelTimeFromOrigin = travelTimeFromOrigin;
    }

    @Exclude
    public int getTravelTimeFromPreviousPlaceOnFoot( ) {
        return travelTimeFromPreviousPlaceOnFoot;
    }

    @Exclude
    public void setTravelTimeFromPreviousPlaceOnFoot( int travelTimeFromPreviousPlaceOnFoot ) {
        this.travelTimeFromPreviousPlaceOnFoot = travelTimeFromPreviousPlaceOnFoot;
    }

    @Exclude
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