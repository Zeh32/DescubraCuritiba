package com.example.jgaug.descubracuritiba;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.text.format.DateFormat;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.Calendar;

public class TimePickerFragment extends DialogFragment implements TimePickerDialog.OnTimeSetListener {

    @NonNull
    public Dialog onCreateDialog( Bundle savedInstanceState ) {
        // Use the current time as the default values for the picker
        final Calendar c = Calendar.getInstance( );
        int hour = c.get( Calendar.HOUR_OF_DAY );
        int minute = c.get( Calendar.MINUTE );

        // Create a new instance of TimePickerDialog and return it
        return new TimePickerDialog( getActivity( ), this, hour, minute, DateFormat.is24HourFormat( getActivity( ) ) );
    }

    public void onTimeSet( TimePicker view, int hourOfDay, int minute ) {
        String formattedHour = String.format( "%02d", hourOfDay );
        String formattedMinute = String.format( "%02d", minute );

        Bundle args = getArguments( );
        boolean isStartTime = args.getBoolean( "isStartTime" );
        if( isStartTime == true ) {
            TextView textViewStartTime = ( TextView ) getActivity( ).findViewById( R.id.textViewStartTime );
            textViewStartTime.setText( formattedHour + " : " + formattedMinute );
        } else {
            TextView textViewEndTime = ( TextView ) getActivity( ).findViewById( R.id.textViewEndTime );
            textViewEndTime.setText( formattedHour + " : " + formattedMinute );
        }
    }
}
