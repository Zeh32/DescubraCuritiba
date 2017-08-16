package com.example.jgaug.descubracuritiba;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class CreateItinerary extends AppCompatActivity {

    public static final String EXTRA_MESSAGE1 = "com.example.myfirstapp.data_inicio";
    public static final String EXTRA_MESSAGE2 = "com.example.myfirstapp.data_fim";
    public static final String EXTRA_MESSAGE3 = "com.example.myfirstapp.hora_inicio";
    public static final String EXTRA_MESSAGE4 = "com.example.myfirstapp.hora_fim";

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

    public void btnMakeItinerary( View view ) {
        Intent intent = new Intent( this, Itinerary.class );

        EditText editText1 = (EditText) findViewById(R.id.editText1);
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
        intent.putExtra(EXTRA_MESSAGE4, hora_fim);

        startActivity( intent );
    }
}
