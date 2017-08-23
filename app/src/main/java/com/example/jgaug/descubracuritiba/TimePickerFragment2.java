package com.example.jgaug.descubracuritiba;

/**
 * Created by Administrador on 22/08/2017.
 */

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.text.format.DateFormat;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.Calendar;

public class TimePickerFragment2 extends DialogFragment
        implements TimePickerDialog.OnTimeSetListener {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current time as the default values for the picker
        final Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);

        // Create a new instance of TimePickerDialog and return it
        return new TimePickerDialog(getActivity(), this, hour, minute,
                DateFormat.is24HourFormat(getActivity()));
    }

    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        // Do something with the time chosen by the user
        if(view.getCurrentMinute() < 10 && view.getCurrentHour() < 10){
            TextView tv1=(TextView) getActivity().findViewById(R.id.textView16);
            tv1.setText("0"+view.getCurrentHour()+" : 0"+view.getCurrentMinute());
        }
        else if(view.getCurrentMinute() < 10 && view.getCurrentHour() >= 10){
            TextView tv1=(TextView) getActivity().findViewById(R.id.textView16);
            tv1.setText(view.getCurrentHour()+" : 0"+view.getCurrentMinute());
        }
        else if(view.getCurrentMinute() >= 10 && view.getCurrentHour() < 10){
            TextView tv1=(TextView) getActivity().findViewById(R.id.textView16);
            tv1.setText("0"+view.getCurrentHour()+" : "+view.getCurrentMinute());
        }
        else{
            TextView tv1=(TextView) getActivity().findViewById(R.id.textView16);
            tv1.setText(view.getCurrentHour()+" : "+view.getCurrentMinute());
        }

    }
}

