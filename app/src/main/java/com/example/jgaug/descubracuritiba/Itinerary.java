package com.example.jgaug.descubracuritiba;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class Itinerary extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_itinerary);

        Intent intent = getIntent();

        String data_inicio = intent.getStringExtra(CreateItinerary.EXTRA_MESSAGE1);
        TextView textView3 = (TextView) findViewById(R.id.textView3);
        textView3.setText(data_inicio);

        String data_fim = intent.getStringExtra(CreateItinerary.EXTRA_MESSAGE2);
        TextView textView4 = (TextView) findViewById(R.id.textView4);
        textView4.setText(data_fim);

        String horario_inicio = intent.getStringExtra(CreateItinerary.EXTRA_MESSAGE3);
        TextView textView5 = (TextView) findViewById(R.id.textView5);
        textView5.setText(horario_inicio);

        String horario_fim = intent.getStringExtra(CreateItinerary.EXTRA_MESSAGE4);
        TextView textView6 = (TextView) findViewById(R.id.textView6);
        textView6.setText(horario_fim);

        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String parques = settings.getString("parques", null);
        TextView textView7 = (TextView) findViewById(R.id.textView7);
        textView7.setText(parques);

        String museus = settings.getString("museus", null);
        TextView textView8 = (TextView) findViewById(R.id.textView8);
        textView8.setText(museus);

        String locais_historicos = settings.getString("locais historicos", null);
        TextView textView9 = (TextView) findViewById(R.id.textView9);
        textView9.setText(locais_historicos);

        String pracas = settings.getString("pracas", null);
        TextView textView10 = (TextView) findViewById(R.id.textView10);
        textView10.setText(pracas);

        String restaurantes = settings.getString("restaurantes", null);
        TextView textView11 = (TextView) findViewById(R.id.textView11);
        textView11.setText(restaurantes);
    }
}
