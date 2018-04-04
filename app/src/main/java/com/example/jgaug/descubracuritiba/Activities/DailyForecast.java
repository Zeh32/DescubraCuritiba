package com.example.jgaug.descubracuritiba.Activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.zetterstrom.com.forecast.models.DataPoint;

import com.example.jgaug.descubracuritiba.R;

import java.math.RoundingMode;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;

public class DailyForecast extends AppCompatActivity {

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_daily_forecast );

        fillForecastData( );
    }

    private void fillForecastData( ) {
        DataPoint dailyForecast = ( DataPoint ) getIntent( ).getSerializableExtra( "dailyForecast" );
        SimpleDateFormat sdf = new SimpleDateFormat( "dd/MM/yyyy - EEEE", Locale.getDefault( ) );

        TextView date = findViewById( R.id.date );
        date.setText( sdf.format( dailyForecast.getTime( ) ) );

        setForecastIcon( dailyForecast.getIcon( ).getText( ) );

        //Set temperature with pattern #.0
        DecimalFormat decimalFormat = new DecimalFormat( "#.0" );
        decimalFormat.setRoundingMode( RoundingMode.CEILING );

        String roundedTemperature = decimalFormat.format( ( dailyForecast.getTemperatureMax( ) + dailyForecast.getTemperatureMin( ) ) / 2 );
        TextView temperature = findViewById( R.id.temperature );
        temperature.setText( roundedTemperature + " ºC" );

        TextView temperatureHigh = findViewById( R.id.temperature_high );
        temperatureHigh.setText( decimalFormat.format( dailyForecast.getTemperatureMax( ) ) + " ºC" );

        TextView temperatureLow = findViewById( R.id.temperature_low );
        temperatureLow.setText( decimalFormat.format( dailyForecast.getTemperatureMin( ) ) + " ºC" );

        //Set other data with pattern #.###
        decimalFormat.applyPattern( "#.###" );

        TextView precipitationProbability = findViewById( R.id.precipitation_probability );
        precipitationProbability.setText( decimalFormat.format( dailyForecast.getPrecipProbability( ) * 100 ) + "%" );

        TextView precipitationIntensity = findViewById( R.id.precipitation_intensity );
        precipitationIntensity.setText( decimalFormat.format( dailyForecast.getPrecipIntensity( ) ) + " mm/h" );

        TextView cloudCover = findViewById( R.id.cloud_cover );
        cloudCover.setText( decimalFormat.format( dailyForecast.getCloudCover( ) * 100 ) + "%" );

        TextView humidity = findViewById( R.id.humidity );
        humidity.setText( decimalFormat.format( dailyForecast.getHumidity( ) * 100 ) + "%" );

        TextView windSpeed = findViewById( R.id.wind_speed );
        windSpeed.setText( decimalFormat.format( dailyForecast.getWindSpeed( ) ) + " km/h" );

        TextView pressure = findViewById( R.id.pressure );
        pressure.setText( decimalFormat.format( dailyForecast.getPressure( ) * 100 ) + " Pa" ); //default unit is in HecPa

        //Set sunrise and sunset time to show just hour and minutes
        TextView sunriseTime = findViewById( R.id.sunrise_time );
        sunriseTime.setText( DateFormat.getTimeInstance( DateFormat.SHORT ).format( dailyForecast.getSunriseTime( ) ) );

        TextView sunsetTime = findViewById( R.id.sunset_time );
        sunsetTime.setText( DateFormat.getTimeInstance( DateFormat.SHORT ).format( dailyForecast.getSunsetTime( ) ) );
    }

    private void setForecastIcon( String iconText ) {
        ImageView forecastIcon = findViewById( R.id.imageViewForecastIcon );

        switch( iconText ) {
            case "clear-day":
                forecastIcon.setImageResource( R.drawable.weather_sun );
                break;
            case "rain":
                forecastIcon.setImageResource( R.drawable.weather_rain );
                break;
            case "cloudy":
                forecastIcon.setImageResource( R.drawable.weather_cloudy );
                break;
            case "partly-cloudy-day":
                forecastIcon.setImageResource( R.drawable.weather_partly_cloudy_day );
                break;
            case "partly-cloudy-night":
                forecastIcon.setImageResource( R.drawable.weather_partly_cloudy_night );
                break;
            default:
                Toast.makeText( this, "Ícone não encontrado!.", Toast.LENGTH_SHORT ).show( );
                break;
        }
    }
}