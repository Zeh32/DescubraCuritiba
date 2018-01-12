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

import com.example.jgaug.descubracuritiba.Helpers.Place;
import com.example.jgaug.descubracuritiba.Fragments.ItineraryFragment;
import com.example.jgaug.descubracuritiba.Helpers.PlaceGroup;
import com.example.jgaug.descubracuritiba.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Itinerary extends AppCompatActivity {
    private ArrayList<Place> places;

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

        places = getIntent().getParcelableArrayListExtra("places");

//        int i = 0;

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
        placesToVisit.add( new Place( "Jardim Botânico", "Jardim botanico.jpg",-25.4431219,-49.2449701, true, 5, 120, new ArrayList< Integer >( Arrays.asList( PlaceGroup.LANDMARKS, PlaceGroup.PARKS ) ), "O Jardim Botânico de Curitiba foi inaugurado em 1991, com uma área de 245 mil m². Seus jardins geométricos e a estufa de três abóbadas tornaram-se um dos principais cartões postais de Curitiba"  ) );
        placesToVisit.add( new Place( "Ópera de Arame", "opera de arame.jpg", -25.384578, -49.2761655, true, 3, 60, new ArrayList< Integer >( Collections.singletonList( PlaceGroup.LANDMARKS ) ), "A Ópera de Arame foi construída em estrutura tubular e teto de policarbonato transparente. O projeto é do arquiteto Domingos Bongestabs, professor do departamento de Arquitetura e Urbanismo da UFPR, o mesmo autor do projeto da Unilivre. Tem capacidade para 2.400 espectadores e um palco de 400m² destinado a apresentações artísticas e culturais."  ) );
        placesToVisit.add( new Place( "Largo da Ordem", "largo da ordem.jpg", -25.4278063, -49.2722547, true, 5, 60, new ArrayList< Integer >( Collections.singletonList( PlaceGroup.LANDMARKS ) ), "O Largo da Ordem é o coração do Centro Histórico de Curitiba e onde se encontra a Igreja da Ordem Terceira de São Francisco das Chagas, a mais antiga de Curitiba. Nos séculos 18, 19 e boa parte do século 20, o Largo era uma área de intenso comércio." ) );
        placesToVisit.add( new Place("Estádio Joaquim Américo Guimarães", "estadio joaquim americo.jpg", -25.4482116, -49.2769866, false, 2, 60, new ArrayList< Integer >( Collections.singletonList( PlaceGroup.LANDMARKS ) ), "Conhecido como Arena da Baixada, o espaço foi o primeiro palco do futebol brasileiro a adotar o naming rights com o título de Kyocera Arena entre 2005 e 1º de abril de 2008 e com a escolha de Curitiba para ser uma das sedes da Copa do Mundo de 2014, a Arena, entre 2012 e 2014, foi reformada, com a ampliação de capacidade de modo a atender os padrões exigidos pela FIFA, passando a ter 42.370 lugares (capacidade de operação conforme CNEF/CBF 2014 é idêntico a capacidade oficial).") );

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