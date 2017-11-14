package com.example.jgaug.descubracuritiba;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.jgaug.descubracuritiba.Helpers.Place;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;

public class CreateItinerary extends AppCompatActivity {
    private boolean parksSelected = false;
    private boolean landmarksSelected = false;
    private boolean museumsSelected = false;
    private boolean shoppingSelected = false;
    private boolean foodsSelected = false;
    private Calendar startDay = Calendar.getInstance( );
    private Calendar endDay = Calendar.getInstance( );
    public int mudouTempoinicio = 0;
    public int mudouTempofim = 0;

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_create_itinerary );
    }

    public void btnSelectPlaceOnMap( View view ) {
        Toast.makeText( this, "Não implementado", Toast.LENGTH_SHORT ).show( );
    }

    public void setTime( View view ) {
        //TODO: não deixar setar o tempo, se o dia selecionado é o de hoje e já se passaram das 19
        //TODO: não deixar setar o tempo de término, se o inicial ainda não foi definido

        Bundle bundle = new Bundle( );
        if( view.getId( ) == R.id.textViewStartTime ) {
            bundle.putBoolean( "isStartTime", true );
        } else {
            bundle.putBoolean( "isStartTime", false );
        }

        DialogFragment timePickerFragment = new TimePickerFragment( );
        timePickerFragment.setArguments( bundle );
        timePickerFragment.show( getSupportFragmentManager( ), "timePicker" );
    }

    public void setDate( View view ) {
        //TODO: não deixar setar a data de término, se a inicial ainda não foi definida

        Bundle bundle = new Bundle( );
        if( view.getId( ) == R.id.textViewStartDay ) {
            bundle.putBoolean( "isStartDay", true );
        } else {
            bundle.putBoolean( "isStartDay", false );
        }

        DialogFragment datePickerFragment = new DatePickerFragment( );
        datePickerFragment.setArguments( bundle );
        datePickerFragment.show( getSupportFragmentManager( ), "datePicker" );
    }

    public void setDay( boolean isStartDay, int year, int month, int day ) {
        if( isStartDay ) {
            this.startDay.set( year, month, day );
        } else {
            this.endDay.set( year, month, day );
        }
    }

    public void setTime( boolean isStartTime, int hourOfDay, int minute ) {
        if( isStartTime ) {
            this.startDay.set( Calendar.HOUR_OF_DAY, hourOfDay );
            this.startDay.set( Calendar.MINUTE, minute );
            mudouTempoinicio = 1;
        } else {
            this.endDay.set( Calendar.HOUR_OF_DAY, hourOfDay );
            this.endDay.set( Calendar.MINUTE, minute );
            mudouTempofim = 1;
        }
    }

    public Calendar getStartDay( ) {
        return startDay;
    }

    public void onSelectPlacesToVisit( View view ) {
        switch( view.getId( ) ) {
            case R.id.imageViewParks:
                parksSelected = !parksSelected;
                changeImageViewResource( parksSelected, R.id.imageViewParks, R.drawable.parks_checked, R.drawable.parks_unchecked );
                break;
            case R.id.imageViewLandmarks:
                landmarksSelected = !landmarksSelected;
                changeImageViewResource( landmarksSelected, R.id.imageViewLandmarks, R.drawable.landmarks_checked, R.drawable.landmarks_unchecked );
                break;
            case R.id.imageViewMuseums:
                museumsSelected = !museumsSelected;
                changeImageViewResource( museumsSelected, R.id.imageViewMuseums, R.drawable.museums_checked, R.drawable.museums_unchecked );
                break;
            case R.id.imageViewShopping:
                shoppingSelected = !shoppingSelected;
                changeImageViewResource( shoppingSelected, R.id.imageViewShopping, R.drawable.shopping_checked, R.drawable.shopping_unchecked );
                break;
            case R.id.imageViewFood:
                foodsSelected = !foodsSelected;
                changeImageViewResource( foodsSelected, R.id.imageViewFood, R.drawable.food_checked, R.drawable.food_unchecked );
                break;
        }
    }

    private void changeImageViewResource( boolean placeSelected, int imageViewId, int checkedImageViewId, int uncheckedImageViewId ) {
        ImageView imageView = ( ImageView ) findViewById( imageViewId );
        if( placeSelected ) {
            imageView.setImageResource( checkedImageViewId );
        } else {
            imageView.setImageResource( uncheckedImageViewId );
        }
    }

    public void btnMakeItinerary( View view ) {
        if( !parksSelected && !landmarksSelected && !museumsSelected && !shoppingSelected && !foodsSelected ) {
            Toast.makeText( this, "Para gerar o itinerário, selecione ao menos um grupo de locais para visita!", Toast.LENGTH_SHORT ).show( );
        } else {
            long diff = endDay.getTimeInMillis( ) - startDay.getTimeInMillis( ); //result in millis

            if(startDay.getTimeInMillis() == 0){
                Toast.makeText( this, "Preencha o primeiro dia!", Toast.LENGTH_SHORT ).show( );
            }
            else if(endDay.getTimeInMillis() == 0){
                Toast.makeText( this, "Preencha o último dia!", Toast.LENGTH_SHORT ).show( );
            }
            else if(diff < 0){
                Toast.makeText( this, "O último dia tem que vir depois do primeiro!", Toast.LENGTH_SHORT ).show( );
            }
            else if(mudouTempoinicio == 0){
                Toast.makeText( this, "Preencha o horário de início!", Toast.LENGTH_SHORT ).show( );
            }
            else if(mudouTempofim == 0){
                Toast.makeText( this, "Preencha o horário de fim!", Toast.LENGTH_SHORT ).show( );
            }
            else{
                long numberOfDays = ( diff / ( 24 * 60 * 60 * 1000 ) ) + 1;
                mudouTempofim = 0;
                mudouTempoinicio = 0;

                final Intent intent = new Intent( this, Itinerary.class );
                intent.putExtra( "startHour", startDay.get( Calendar.HOUR_OF_DAY ) );
                intent.putExtra( "startMinute", startDay.get( Calendar.MINUTE ) );
                intent.putExtra( "endHour", endDay.get( Calendar.HOUR_OF_DAY ) );
                intent.putExtra( "endMinute", endDay.get( Calendar.MINUTE ) );
                intent.putExtra( "numberOfDays", ( int ) numberOfDays );

                final FirebaseDatabase database = FirebaseDatabase.getInstance( );
                DatabaseReference ref = database.getReference( "" );

                // Attach a listener to read the data at our posts reference
                ref.addValueEventListener( new ValueEventListener( ) {
                    @Override
                    public void onDataChange( DataSnapshot dataSnapshot ) {
                        ArrayList< Place > places = dataSnapshot.child( "places" ).getValue( new GenericTypeIndicator< ArrayList< Place > >( ) { } );

                        //TODO: passar o places para a activity do Itinerary
                        CreateItinerary.this.startActivity( intent );
                    }

                    @Override
                    public void onCancelled( DatabaseError databaseError ) {
                        System.out.println( "The read failed: " + databaseError.getCode( ) );
                    }
                } );
            }

        }
    }
}
