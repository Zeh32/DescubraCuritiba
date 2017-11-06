package com.example.jgaug.descubracuritiba;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.example.jgaug.descubracuritiba.Helpers.Place;

import java.util.ArrayList;
import java.util.List;

public class Itinerary extends AppCompatActivity {
    //private List< Place > places;

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_itinerary );

        Toolbar toolbar = ( Toolbar ) findViewById( R.id.toolbar );
        setSupportActionBar( toolbar );

        // Create the adapter that will return a fragment for each section of the activity.
        SectionsPagerAdapter mSectionsPagerAdapter = new SectionsPagerAdapter( getSupportFragmentManager( ) );

        // Set up the ViewPager with the sections adapter.
        ViewPager mViewPager = ( ViewPager ) findViewById( R.id.container );
        mViewPager.setAdapter( mSectionsPagerAdapter );

        TabLayout tabLayout = ( TabLayout ) findViewById( R.id.tabs );
        tabLayout.setupWithViewPager( mViewPager );

        //TODO: receber o array de places da activity CreateItinerary
    }

    @Override
    public boolean onCreateOptionsMenu( Menu menu ) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater( ).inflate( R.menu.menu_itinerary, menu );
        return true;
    }

    @Override
    public boolean onOptionsItemSelected( MenuItem item ) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId( );

        //noinspection SimplifiableIfStatement
        if( id == R.id.action_settings ) {
            return true;
        }

        return super.onOptionsItemSelected( item );
    }

    public List< Place > getPlacesToVisit( int day ) {
        List< Place > placesToVisit = new ArrayList<>( );
        placesToVisit.add( new Place( "Jardim botanico.jpg", "1 - Jardim Botânico", "Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat." ) );
        placesToVisit.add( new Place( "opera de arame.jpg", "2 - Ópera de Arame", "Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat." ) );
        placesToVisit.add( new Place( "largo da ordem.jpg", "3 - Largo da Ordem", "Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat." ) );
        placesToVisit.add( new Place( "praca tiradentes.jpg", "4 - Praça Tiradentes", "lalalalala" ) );

        return placesToVisit;
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter( FragmentManager fm ) {
            super( fm );
        }

        @Override
        public Fragment getItem( int position ) {
            return ItineraryFragment.newInstance( position );
        }

        @Override
        public int getCount( ) {
            return getIntent( ).getIntExtra( "numberOfDays", 1 );
        }

        @Override
        public CharSequence getPageTitle( int position ) {
            return "Dia " + ( position + 1 );
        }
    }
}