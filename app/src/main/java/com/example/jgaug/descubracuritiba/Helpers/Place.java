package com.example.jgaug.descubracuritiba.Helpers;

import com.google.firebase.database.Exclude;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;

public class Place implements Serializable {

    public int id;
    public String image;
    public String name;
    public double latitude;
    public double longitude;
    public boolean weatherDependent;
    public int relevance;
    public int visitTime;
    public ArrayList< Integer > placeGroup;
    public String description;
    public Calendar startTime;
    //TODO: Horário de funcionamento

    //Firebase needs an empty constructor in order to map places back from database
    public Place( ) {
    }

    public Place( int id, String name, String image, double latitude, double longitude, boolean weatherDependent, int relevance, int visitTime, ArrayList< Integer > placeGroup, String description ) {
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
        this.startTime = Calendar.getInstance( );
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
        String formattedHour = String.format( "%02d", startTime.get( Calendar.HOUR_OF_DAY ) );
        String formattedMinute = String.format( "%02d", startTime.get( Calendar.MINUTE ) );

        String visitPeriod = formattedHour + ":" + formattedMinute + " - ";

        startTime.add( Calendar.MINUTE, visitTime );
        formattedHour = String.format( "%02d", startTime.get( Calendar.HOUR_OF_DAY ) );
        formattedMinute = String.format( "%02d", startTime.get( Calendar.MINUTE ) );

        startTime.add( Calendar.MINUTE, (-1)*visitTime);

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
