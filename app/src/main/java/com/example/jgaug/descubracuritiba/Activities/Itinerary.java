package com.example.jgaug.descubracuritiba.Activities;

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

import com.example.jgaug.descubracuritiba.Fragments.ItineraryFragment;
import com.example.jgaug.descubracuritiba.Helpers.DailyItinerary;
import com.example.jgaug.descubracuritiba.Helpers.DailyItineraryList;
import com.example.jgaug.descubracuritiba.R;

public class Itinerary extends AppCompatActivity {
    private DailyItineraryList itinerary;

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        itinerary = ( DailyItineraryList ) getIntent( ).getSerializableExtra( "itinerary" );

        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_itinerary );

//        Toolbar toolbar = findViewById( R.id.toolbar );
//        setSupportActionBar( toolbar );

        // Create the adapter that will return a fragment for each section of the activity.
        SectionsPagerAdapter mSectionsPagerAdapter = new SectionsPagerAdapter( getSupportFragmentManager( ) );

        // Set up the ViewPager with the sections adapter.
        ViewPager mViewPager = findViewById( R.id.container );
        mViewPager.setAdapter( mSectionsPagerAdapter );

        TabLayout tabLayout = findViewById( R.id.tabs );
        tabLayout.setupWithViewPager( mViewPager );
    }

//    @Override
//    public boolean onCreateOptionsMenu( Menu menu ) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater( ).inflate( R.menu.menu_itinerary, menu );
//        return true;
//    }

//    @Override
//    public boolean onOptionsItemSelected( MenuItem item ) {
//        // Handle action bar item clicks here. The action bar will automatically handle clicks
//        // on the Home/Up button, so long as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId( );
//
//        //noinspection SimplifiableIfStatement
//        if( id == R.id.action_settings ) {
//            return true;
//        }
//
//        return super.onOptionsItemSelected( item );
//    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        private SectionsPagerAdapter( FragmentManager fm ) {
            super( fm );
        }

        @Override
        public Fragment getItem( int position ) {
            return ItineraryFragment.newInstance( position );
        }

        @Override
        public int getCount( ) {
            return itinerary.getItinerary( ).size( );
        }

        @Override
        public CharSequence getPageTitle( int position ) {
            return "Dia " + ( position + 1 );
        }
    }

    public DailyItinerary getDailyItinerary( int day ) {
        return itinerary.getItinerary( ).get( day );
    }
}