package com.example.jgaug.descubracuritiba.Helpers;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;

public class Place implements Serializable {
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

    public Place( String name, String image, double latitude, double longitude, boolean weatherDependent, int relevance, int visitTime, ArrayList< Integer > placeGroup, String description ) {
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

    public String getVisitPeriod( ) {
        String formattedHour = String.format( "%02d", startTime.get( Calendar.HOUR_OF_DAY ) );
        String formattedMinute = String.format( "%02d", startTime.get( Calendar.MINUTE ) );

        String visitPeriod = formattedHour + ":" + formattedMinute + " - ";

        startTime.add( Calendar.MINUTE, visitTime );
        formattedHour = String.format( "%02d", startTime.get( Calendar.HOUR_OF_DAY ) );
        formattedMinute = String.format( "%02d", startTime.get( Calendar.MINUTE ) );

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

    //    @Override
    //    public int describeContents() {
    //        return 0;
    //    }
    //
    //    @Override
    //    public void writeToParcel(Parcel parcel, int i) {
    //
    //        parcel.writeString(this.name);
    //        parcel.writeString(this.image);
    //        parcel.writeDouble(this.latitude);
    //        parcel.writeDouble(this.longitude);
    ////        parcel.writeBooleanArray(this.weatherDependent);
    //        parcel.writeInt(this.relevance);
    //        parcel.writeInt(this.visitTime);
    //        parcel.writeList(this.placeGroup);
    //        parcel.writeString(this.description);
    //        parcel.writeValue( this.startTime );
    //    }
    //
    //    public Place(Parcel in) {
    //        this.name = in.readString();
    //        this.image = in.readString();
    //        this.latitude = in.readDouble();
    //        this.longitude = in.readDouble();
    //        this.relevance = in.readInt();
    //        this.visitTime = in.readInt();
    //        this.placeGroup = in.readArrayList(ArrayList.class.getClassLoader());
    //        this.description = in.readString();
    //        this.startTime = ( Calendar ) in.readValue( Calendar.class.getClassLoader() );
    //    }
    //
    //    public static final Creator<Place> CREATOR = new Creator<Place>() {
    //        @Override
    //        public Place createFromParcel(Parcel source) {
    //            return new Place(source);
    //        }
    //
    //        @Override
    //        public Place[] newArray(int size) {
    //            return new Place[size];
    //        }
    //    };
}
