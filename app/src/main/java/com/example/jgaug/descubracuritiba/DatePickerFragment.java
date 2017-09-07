package com.example.jgaug.descubracuritiba;

/**
 * Created by Administrador on 22/08/2017.
 */

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.widget.DatePicker;
import android.widget.TextView;

import java.util.Calendar;

public class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {

    @NonNull
    public Dialog onCreateDialog( Bundle savedInstanceState ) {
        // Use the current date as the default date in the picker
        final Calendar c = Calendar.getInstance( );
        int year = c.get( Calendar.YEAR );
        int month = c.get( Calendar.MONTH );
        int day = c.get( Calendar.DAY_OF_MONTH );

        // Create a new instance of DatePickerDialog and return it
        return new DatePickerDialog( getActivity( ), this, year, month, day );
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
