package com.example.jgaug.descubracuritiba;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class CreateItinerary extends AppCompatActivity {
    private boolean parksSelected = false;
    private boolean landmarksSelected = false;
    private boolean museumsSelected = false;
    private boolean shoppingSelected = false;
    private boolean foodsSelected = false;

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_create_itinerary );

        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences( getApplicationContext( ) );
        SharedPreferences.Editor editor = settings.edit( );
        editor.putString( "parques", "Parques não" );
        editor.putString( "museus", "Museus não" );
        editor.putString( "locais historicos", "Locais históricos não" );
        editor.putString( "pracas", "Praças não" );
        editor.putString( "restaurantes", "Restaurantes não" );
        editor.apply( );

        /*dateView = ( TextView ) findViewById( R.id.textView13 );
        //day = 22;
        //month = 07;
        //year = 2017;
        calendar = Calendar.getInstance( );
        year = calendar.get( Calendar.YEAR );
        month = calendar.get( Calendar.MONTH );
        day = calendar.get( Calendar.DAY_OF_MONTH );
        showDate1( year, month + 1, day );

        editor.putInt( "year1", year );
        editor.putInt( "month1", month );
        editor.putInt( "day1", day );
        editor.apply( );

        dateView2 = ( TextView ) findViewById( R.id.textView14 );
        //day2 = 22;
        //month2 = 07;
        //year2 = 2017;
        calendar2 = Calendar.getInstance( );
        year2 = calendar2.get( Calendar.YEAR );
        month2 = calendar2.get( Calendar.MONTH );
        day2 = calendar2.get( Calendar.DAY_OF_MONTH );
        showDate2( year2, month2 + 1, day2 );
        editor.putInt( "year2", year2 );
        editor.putInt( "month2", month2 );
        editor.putInt( "day2", day2 );
        editor.apply( );

        TextView tv1 = ( TextView ) findViewById( R.id.textView15 );
        tv1.setText( "00 : 00" );

        TextView tv2 = ( TextView ) findViewById( R.id.textView16 );
        tv2.setText( "00 : 00" );*/
    }

    public void btnSelectPlaceOnMap( View view ) {
        Toast.makeText( this, "Não implementado", Toast.LENGTH_SHORT ).show( );
    }

    public void setTime( View view ) {
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
        if( placeSelected == true ) {
            imageView.setImageResource( checkedImageViewId );
        } else {
            imageView.setImageResource( uncheckedImageViewId );
        }
    }

    public void btnMakeItinerary( View view ) {
        Intent intent = new Intent( this, Itinerary.class );

        TextView textViewStartDay = ( TextView ) findViewById( R.id.textViewStartDay );
        intent.putExtra( "startDay", textViewStartDay.getText( ).toString( ) );

        TextView textViewEndDay = ( TextView ) findViewById( R.id.textViewEndDay );
        intent.putExtra( "endDay", textViewEndDay.getText( ).toString( ) );

        TextView textViewStartTime = ( TextView ) findViewById( R.id.textViewStartTime );
        intent.putExtra( "startTime", textViewStartTime.getText( ).toString( ) );

        TextView textViewEndTime = ( TextView ) findViewById( R.id.textViewEndTime );
        intent.putExtra( "endTime", textViewEndTime.getText( ).toString( ) );

        startActivity( intent );
    }
}
