package com.example.jgaug.descubracuritiba.Fragments;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.widget.DatePicker;
import android.widget.TextView;

import com.example.jgaug.descubracuritiba.Activities.CreateItinerary;
import com.example.jgaug.descubracuritiba.R;

import java.util.Calendar;

public class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {
    private boolean isStartDay;

    @NonNull
    public Dialog onCreateDialog( Bundle savedInstanceState ) {
        Calendar calendar = Calendar.getInstance( );

        Bundle args = getArguments( );
        isStartDay = args.getBoolean( "isStartDay" );
        if( isStartDay ) {
            if( calendar.get( Calendar.HOUR_OF_DAY ) >= 16 ) {
                calendar.roll( Calendar.DATE, true );
            }

            DatePickerDialog pickerDialog = new DatePickerDialog( getActivity( ), this, calendar.get( Calendar.YEAR ), calendar.get( Calendar.MONTH ), calendar.get( Calendar.DAY_OF_MONTH ) );

            long minDate = calendar.getTime( ).getTime( );
            pickerDialog.getDatePicker( ).setMinDate( minDate );

            calendar.add( Calendar.DAY_OF_YEAR, 7 );
            long maxDate = calendar.getTime( ).getTime( );
            pickerDialog.getDatePicker( ).setMaxDate( maxDate );

            return pickerDialog;
        } else {
            Calendar startDay = ( Calendar ) ( ( CreateItinerary ) getActivity( ) ).getStartDay( ).clone( );
            DatePickerDialog pickerDialog = new DatePickerDialog( getActivity( ), this, startDay.get( Calendar.YEAR ), startDay.get( Calendar.MONTH ), startDay.get( Calendar.DAY_OF_MONTH ) );

            long minDate = startDay.getTime( ).getTime( ); //Set de minimum date to the first date
            pickerDialog.getDatePicker( ).setMinDate( minDate );

            startDay.add( Calendar.DAY_OF_YEAR, 7 ); //Add a limit for 7 days to the pickerDialog
            long maxDate = startDay.getTime( ).getTime( );
            pickerDialog.getDatePicker( ).setMaxDate( maxDate );

            return pickerDialog;
        }
    }

    public void onDateSet( DatePicker view, int year, int month, int day ) {
        String formattedYear = String.format( "%02d", year );
        String formattedMonth = String.format( "%02d", month + 1 ); //Android starts counting months from 0
        String formattedDay = String.format( "%02d", day );

        if( isStartDay == true ) {
            ( ( CreateItinerary ) getActivity( ) ).setDate( true, year, month, day );

            TextView textViewStartDay = getActivity( ).findViewById( R.id.textViewStartDay );
            textViewStartDay.setText( formattedDay + " / " + formattedMonth + " / " + formattedYear );
        } else {
            ( ( CreateItinerary ) getActivity( ) ).setDate( false, year, month, day );

            TextView textViewEndDay = getActivity( ).findViewById( R.id.textViewEndDay );
            textViewEndDay.setText( formattedDay + " / " + formattedMonth + " / " + formattedYear );
        }
    }
}