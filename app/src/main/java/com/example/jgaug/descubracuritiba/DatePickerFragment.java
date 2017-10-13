package com.example.jgaug.descubracuritiba;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.widget.DatePicker;
import android.widget.TextView;

import java.util.Calendar;
import java.util.Date;

public class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {

    @NonNull
    public Dialog onCreateDialog( Bundle savedInstanceState ) {
        // Use the current date as the default date in the picker
        final Calendar c = Calendar.getInstance( );
        int year = c.get( Calendar.YEAR );
        int month = c.get( Calendar.MONTH );
        int day = c.get( Calendar.DAY_OF_MONTH );

        Date today = new Date( );
        c.setTime( today );
        long minDate = c.getTime( ).getTime( ); //Set de minimum date to today

        c.add( Calendar.DAY_OF_YEAR, 14 ); //Add a limit for 14 days to the pickerDialog
        long maxDate = c.getTime( ).getTime( );

        // Create a new instance of DatePickerDialog and return it
        DatePickerDialog pickerDialog = new DatePickerDialog( getActivity( ), this, year, month, day );
        pickerDialog.getDatePicker( ).setMaxDate( maxDate );
        pickerDialog.getDatePicker( ).setMinDate( minDate );

        return pickerDialog;
    }

    public void onDateSet( DatePicker view, int year, int month, int day ) {
        String formattedYear = String.format( "%02d", year );
        String formattedMonth = String.format( "%02d", month );
        String formattedDay = String.format( "%02d", day );

        Bundle args = getArguments( );
        boolean isStartDay = args.getBoolean( "isStartDay" );
        if( isStartDay == true ) {
            TextView textViewStartDay = ( TextView ) getActivity( ).findViewById( R.id.textViewStartDay );
            textViewStartDay.setText( formattedDay + " / " + formattedMonth + " / " + formattedYear );
        } else {
            TextView textViewEndDay = ( TextView ) getActivity( ).findViewById( R.id.textViewEndDay );
            textViewEndDay.setText( formattedDay + " / " + formattedMonth + " / " + formattedYear );
        }
    }
}
