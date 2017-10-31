package com.example.jgaug.descubracuritiba.Helpers;

import java.util.ArrayList;

public class Place {
    public String image;
    public String name;
    public boolean weatherDependent;
    public int relevance;
    public int visitTime;
    public ArrayList< Integer > placeGroup;
    public String description;
    //TODO: Horário de funcionamento

    //Firebase needs an empty constructor in order to map places back from database
    public Place( ) { }

    public Place( String image, String name, String description ) {
        this.image = image;
        this.name = name;
        this.description = description;
    }

    public Place( String name, boolean weatherDependent, int relevance, int visitTime, ArrayList< Integer > placeGroup, String description ) {
        this.name = name;
        this.weatherDependent = weatherDependent;
        this.relevance = relevance;
        this.visitTime = visitTime;
        this.placeGroup = placeGroup;
        this.description = description;
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

    public boolean isWeatherDependent( ) {
        return weatherDependent;
    }

    public int getRelevance( ) {
        return relevance;
    }
}
