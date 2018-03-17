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
    @Exclude public Calendar startTime = null;

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

    public int getRelevance( ) {
        return relevance;
    }

    public boolean isWeatherDependent( ) {
        return weatherDependent;
    }

    public void setStartTime( Calendar startTime ) {
        this.startTime = startTime;
    }
}
