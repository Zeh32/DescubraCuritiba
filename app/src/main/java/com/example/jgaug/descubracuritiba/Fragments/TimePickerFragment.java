package com.example.jgaug.descubracuritiba.Fragments;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.text.format.DateFormat;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.jgaug.descubracuritiba.Activities.CreateItinerary;
import com.example.jgaug.descubracuritiba.R;

import java.util.Calendar;

public class TimePickerFragment extends DialogFragment implements TimePickerDialog.OnTimeSetListener {
    private boolean isStartTime;

    @NonNull
    public Dialog onCreateDialog( Bundle savedInstanceState ) {
        Bundle args = getArguments( );
        isStartTime = args.getBoolean( "isStartTime" );

        int defaultHourOfDay = isStartTime ? 9 : 19;
        int defaultMinute = 0;

        // Create a new instance of TimePickerDialog and return it
        return new TimePickerDialog( getActivity( ), this, defaultHourOfDay, defaultMinute, DateFormat.is24HourFormat( getActivity( ) ) );
    }

    public void onTimeSet( TimePicker view, int hourOfDay, int minute ) {
        String formattedHour = String.format( "%02d", hourOfDay );
        String formattedMinute = String.format( "%02d", minute );

        if( isStartTime ) {
            if( hourOfDay >= 16 ) {
                Toast.makeText( getActivity( ), "Não é possível definir um horário de início após às 16 horas.", Toast.LENGTH_LONG ).show( );
            } else if( hourOfDay < 5 ) {
                Toast.makeText( getActivity( ), "Não é possível definir um horário de início inferior às 5 horas.", Toast.LENGTH_LONG ).show( );
            } else {
                ( ( CreateItinerary ) getActivity( ) ).setTime( true, hourOfDay, minute );

                TextView textViewStartTime = getActivity( ).findViewById( R.id.textViewStartTime );
                textViewStartTime.setText( formattedHour + " : " + formattedMinute );
            }
        } else {
            if( hourOfDay >= 20 ) {
                Toast.makeText( getActivity( ), "Não é possível definir um horário de término após às 20 horas.", Toast.LENGTH_LONG ).show( );
            } else {
                Calendar endTime = ( Calendar ) ( ( CreateItinerary ) getActivity( ) ).getStartTime( ).clone( );
                endTime.set( Calendar.HOUR_OF_DAY, hourOfDay );
                endTime.set( Calendar.MINUTE, minute );
                endTime.set( Calendar.SECOND, 0 );

                long diff = endTime.getTimeInMillis( ) - ( ( CreateItinerary ) getActivity( ) ).getStartTime( ).getTimeInMillis();
                long minutes = ( diff / ( 60 * 1000 ) );

                if( minutes >= 180 ) {
                    ( ( CreateItinerary ) getActivity( ) ).setTime( false, hourOfDay, minute );

                    TextView textViewEndTime = getActivity( ).findViewById( R.id.textViewEndTime );
                    textViewEndTime.setText( formattedHour + " : " + formattedMinute );
                } else {
                    Toast.makeText( getActivity( ), "Deve haver uma diferença de pelo menos 3 horas entre o horário de início e término", Toast.LENGTH_LONG ).show( );
                }
            }
        }
    }
}