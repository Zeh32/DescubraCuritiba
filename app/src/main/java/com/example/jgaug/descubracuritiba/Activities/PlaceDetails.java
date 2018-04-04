package com.example.jgaug.descubracuritiba.Activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.jgaug.descubracuritiba.GlideUtil;
import com.example.jgaug.descubracuritiba.Helpers.Place;
import com.example.jgaug.descubracuritiba.Helpers.PlaceGroup;
import com.example.jgaug.descubracuritiba.R;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.List;

public class PlaceDetails extends AppCompatActivity {

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_place_details );

        fillPlaceData( );
    }

    private void fillPlaceData( ) {
        Place place = ( Place ) getIntent( ).getSerializableExtra( "place" );

        StorageReference mStorageRef = FirebaseStorage.getInstance( ).getReference( );
        mStorageRef.child( place.getImage( ) ).getDownloadUrl( ).addOnSuccessListener( uri -> {
            ImageView imageView = findViewById( R.id.place_image_details );
            GlideUtil.loadImageFinal( PlaceDetails.this, uri.toString( ), imageView );
        } );

        TextView name = findViewById( R.id.place_name_details );
        name.setText( place.getName( ) );

        TextView description = findViewById( R.id.place_description_details );
        description.setText( place.getDescription( ) );

        TextView placeGroup = findViewById( R.id.place_group_details );
        placeGroup.setText( getReadablePlaceGroups( place.getPlaceGroup( ) ) );

        TextView relevance = findViewById( R.id.place_relevance_details );
        relevance.setText( String.valueOf( place.getRelevance( ) ) );

        TextView visitTime = findViewById( R.id.place_visit_time_details );
        visitTime.setText( place.getVisitTime( ) + " minutos" );

        TextView weatherDependent = findViewById( R.id.place_weather_dependent_details );
        weatherDependent.setText( place.isWeatherDependent( ) ? "sim" : "não" );
    }

    private StringBuilder getReadablePlaceGroups( List< Integer > placeGroup ) {
        StringBuilder text = new StringBuilder( );

        for( int placeIndex = 0; placeIndex < placeGroup.size( ); placeIndex++ ) {
            switch( placeGroup.get( placeIndex ) ) {
                case PlaceGroup.PARKS:
                    text.append( "parques, bosques e praças" );
                    break;
                case PlaceGroup.LANDMARKS:
                    text.append( "pontos turísticos" );
                    break;
                case PlaceGroup.MUSEUMS:
                    text.append( "museus" );
                    break;
                case PlaceGroup.SHOPPING:
                    text.append( "locais de compras" );
                    break;
                case PlaceGroup.FOOD:
                    text.append( "gastronômicos" );
                    break;
            }

            if( placeIndex == 0 && placeGroup.size( ) > 1 ) {
                text.append( " e " );
            }
        }

        return text;
    }
}