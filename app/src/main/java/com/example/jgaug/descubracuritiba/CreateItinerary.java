package com.example.jgaug.descubracuritiba;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import java.util.Calendar;
import android.preference.PreferenceManager;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class CreateItinerary extends AppCompatActivity {

    public static final String EXTRA_MESSAGE1 = "com.example.myfirstapp.data_inicio";
    public static final String EXTRA_MESSAGE2 = "com.example.myfirstapp.data_fim";
    public static final String EXTRA_MESSAGE3 = "com.example.myfirstapp.hora_inicio";
    public static final String EXTRA_MESSAGE4 = "com.example.myfirstapp.hora_fim";
    private DatePicker datePicker;
    private Calendar calendar, calendar2;
    private TextView dateView;
    private int year, month, day;
    private TextView dateView2;
    private int year2, month2, day2;

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_create_itinerary );

        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor editor = settings.edit();
        editor.putString("parques", "Parques não");
        editor.putString("museus", "Museus não");
        editor.putString("locais historicos", "Locais históricos não");
        editor.putString("pracas", "Praças não");
        editor.putString("restaurantes", "Restaurantes não");
        editor.apply();

        dateView = (TextView) findViewById(R.id.textView13);
        //day = 22;
        //month = 07;
        //year = 2017;
        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        showDate1(year, month+1, day);

        editor.putInt("year1",year);
        editor.putInt("month1",month);
        editor.putInt("day1",day);
        editor.apply();

        dateView2 = (TextView) findViewById(R.id.textView14);
        //day2 = 22;
        //month2 = 07;
        //year2 = 2017;
        calendar2 = Calendar.getInstance();
        year2 = calendar2.get(Calendar.YEAR);
        month2 = calendar2.get(Calendar.MONTH);
        day2 = calendar2.get(Calendar.DAY_OF_MONTH);
        showDate2(year2, month2+1, day2);
        editor.putInt("year2",year2);
        editor.putInt("month2",month2);
        editor.putInt("day2",day2);
        editor.apply();

        TextView tv1=(TextView) findViewById(R.id.textView15);
        tv1.setText("00 : 00");

        TextView tv2=(TextView) findViewById(R.id.textView16);
        tv2.setText("00 : 00");
    }

    public void showTimePickerDialog(View v) {
        DialogFragment newFragment = new TimePickerFragment();
        newFragment.show(getSupportFragmentManager(), "timePicker");
    }

    public void showTimePickerDialog2(View v) {
        DialogFragment newFragment2 = new TimePickerFragment2();
        newFragment2.show(getSupportFragmentManager(), "timePicker");
    }

    public void onCheckboxClicked(View view){
        boolean checked = ((CheckBox) view).isChecked();

        switch(view.getId()){
            case R.id.checkBox_parques:
                if(checked){
                    SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                    SharedPreferences.Editor editor = settings.edit();
                    editor.putString("parques", "Parques sim");
                    editor.apply();
                    Toast.makeText( this, "Você selecionou parques", Toast.LENGTH_SHORT ).show( );
                }
                else{
                    Toast.makeText( this, "Você tirou parques", Toast.LENGTH_SHORT ).show( );
                }
                break;
            case R.id.checkBox_museus:
                if(checked){
                    SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                    SharedPreferences.Editor editor = settings.edit();
                    editor.putString("museus", "Museus sim");
                    editor.apply();
                    Toast.makeText( this, "Você selecionou museus", Toast.LENGTH_SHORT ).show( );
                }
                else{
                    Toast.makeText( this, "Você tirou museus", Toast.LENGTH_SHORT ).show( );
                }
                break;
            case R.id.checkBox_locais_hist:
                if(checked){
                    SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                    SharedPreferences.Editor editor = settings.edit();
                    editor.putString("locais historicos", "Locais históricos sim");
                    editor.apply();
                    Toast.makeText( this, "Você selecionou locais históricos", Toast.LENGTH_SHORT ).show( );
                }
                else{
                    Toast.makeText( this, "Você tirou locais históricos", Toast.LENGTH_SHORT ).show( );
                }
                break;
            case R.id.checkBox_pracas:
                if(checked){
                    SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                    SharedPreferences.Editor editor = settings.edit();
                    editor.putString("pracas", "Locais de compra sim");
                    editor.apply();
                    Toast.makeText( this, "Você selecionou locais de compra", Toast.LENGTH_SHORT ).show( );
                }
                else{
                    Toast.makeText( this, "Você tirou locais de compra", Toast.LENGTH_SHORT ).show( );
                }
                break;
            case R.id.checkBox_restaurantes:
                if(checked){
                    SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                    SharedPreferences.Editor editor = settings.edit();
                    editor.putString("restaurantes", "Restaurantes sim");
                    editor.apply();
                    Toast.makeText( this, "Você selecionou restaurantes", Toast.LENGTH_SHORT ).show( );
                }
                else{
                    Toast.makeText( this, "Você tirou restaurantes", Toast.LENGTH_SHORT ).show( );
                }
                break;
        }
    }

    @SuppressWarnings("deprecation")
    public void setDate(View view) {
        showDialog(999);
        Toast.makeText(getApplicationContext(), "ca",
                Toast.LENGTH_SHORT)
                .show();
    }

    @SuppressWarnings("deprecation")
    public void setDate2(View view) {
        showDialog(998);
        Toast.makeText(getApplicationContext(), "ca",
                Toast.LENGTH_SHORT)
                .show();
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        // TODO Auto-generated method stub
        if (id == 999) {
            return new DatePickerDialog(this,
                    myDateListener, year, month, day);
        }
        else if(id == 998){
            return new DatePickerDialog(this,
                    myDateListener2, year2, month2, day2);
        }
        return null;
    }

    private DatePickerDialog.OnDateSetListener myDateListener = new
            DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker arg0,
                                      int arg1, int arg2, int arg3) {
                    // TODO Auto-generated method stub
                    // arg1 = year
                    // arg2 = month
                    // arg3 = day
                    showDate1(arg1, arg2+1, arg3);
                }
            };

    private DatePickerDialog.OnDateSetListener myDateListener2 = new
            DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker arg0,
                                      int arg1, int arg2, int arg3) {
                    // TODO Auto-generated method stub
                    // arg1 = year
                    // arg2 = month
                    // arg3 = day
                    showDate2(arg1, arg2+1, arg3);
                }
            };

    private void showDate1(int year, int month, int day) {
        dateView.setText(new StringBuilder().append(day).append("/")
                .append(month).append("/").append(year));
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor editor = settings.edit();
        editor.putInt("year1",year);
        editor.putInt("month1",month);
        editor.putInt("day1",day);
        editor.commit();
    }

    private void showDate2(int year, int month, int day) {
        dateView2.setText(new StringBuilder().append(day).append("/")
                .append(month).append("/").append(year));
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor editor = settings.edit();
        editor.putInt("year2",year);
        editor.putInt("month2",month);
        editor.putInt("day2",day);
        editor.commit();
    }

    public void btnMakeItinerary( View view ) {
        Intent intent = new Intent( this, Itinerary.class );

        /*EditText editText1 = (EditText) findViewById(R.id.editText1);
        String data_inicio = editText1.getText().toString();
        intent.putExtra(EXTRA_MESSAGE1, data_inicio);

        EditText editText2 = (EditText) findViewById(R.id.editText2);
        String data_fim = editText2.getText().toString();
        intent.putExtra(EXTRA_MESSAGE2, data_fim);

        EditText editText3 = (EditText) findViewById(R.id.editText3);
        String hora_inicio = editText3.getText().toString();
        intent.putExtra(EXTRA_MESSAGE3, hora_inicio);

        EditText editText4 = (EditText) findViewById(R.id.editText4);
        String hora_fim = editText4.getText().toString();
        intent.putExtra(EXTRA_MESSAGE4, hora_fim);*/

        startActivity( intent );
    }
}
