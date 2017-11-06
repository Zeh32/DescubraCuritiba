package com.example.jgaug.descubracuritiba;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.example.jgaug.descubracuritiba.Helpers.Place;
import com.example.jgaug.descubracuritiba.Helpers.PlaceGroup;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;


public class FirstScreen extends AppCompatActivity {

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );

        //Set to fullscreen
        requestWindowFeature( Window.FEATURE_NO_TITLE );
        getWindow( ).setFlags( WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN );

        setContentView( R.layout.activity_first_screen );
    }

    public void btnCreateItinerary( View view ) {
        Intent intent = new Intent( this, CreateItinerary.class );
        startActivity( intent );
    }

    public void btnManageSavedItineraries( View view ) {
        Toast.makeText( this, "Não implementado", Toast.LENGTH_SHORT ).show( );
    }

    public void btnPlacesToVisit( View view ) {
        Toast.makeText( this, "Não implementado", Toast.LENGTH_SHORT ).show( );
    }

    private void getPlaces( ) {
        final FirebaseDatabase database = FirebaseDatabase.getInstance( );
        DatabaseReference ref = database.getReference( "" );

        // Attach a listener to read the data at our posts reference
        ref.addValueEventListener( new ValueEventListener( ) {
            @Override
            public void onDataChange( DataSnapshot dataSnapshot ) {
                ArrayList< Place > children = dataSnapshot.child( "places" ).getValue( new GenericTypeIndicator< ArrayList< Place > >( ) { } );
            }

            @Override
            public void onCancelled( DatabaseError databaseError ) {
                System.out.println( "The read failed: " + databaseError.getCode( ) );
            }
        } );
    }

    private void addPlaces( ) {
        final FirebaseDatabase database = FirebaseDatabase.getInstance( );
        DatabaseReference ref = database.getReference( "" );
        DatabaseReference postsRef = ref.child( "places" );

        ArrayList< Place > places = new ArrayList<>( );
        places.add( new Place( "Jardim Botânico", true, 5, 120, new ArrayList< Integer >( Arrays.asList( PlaceGroup.LANDMARKS, PlaceGroup.PARKS ) ), "descrição" ) );
        places.add( new Place( "Bosque Alemão", true, 3, 90, new ArrayList< Integer >( Collections.singletonList( PlaceGroup.PARKS ) ), "descrição" ) );
        places.add( new Place( "Parque Tanguá", true, 5, 120, new ArrayList< Integer >( Arrays.asList( PlaceGroup.LANDMARKS, PlaceGroup.PARKS ) ), "descrição" ) );
        places.add( new Place( "Parque Barigui", true, 4, 150, new ArrayList< Integer >( Collections.singletonList( PlaceGroup.PARKS ) ), "descrição" ) );
        places.add( new Place( "Bosque João Paulo II", true, 3, 90, new ArrayList< Integer >( Collections.singletonList( PlaceGroup.PARKS ) ), "descrição" ) );
        places.add( new Place( "Parque Tingui", true, 4, 120, new ArrayList< Integer >( Collections.singletonList( PlaceGroup.PARKS ) ), "descrição" ) );
        places.add( new Place( "Parque São Lourenço", true, 3, 90, new ArrayList< Integer >( Collections.singletonList( PlaceGroup.PARKS ) ), "descrição" ) );
        places.add( new Place( "Praça do Japão", true, 3, 60, new ArrayList< Integer >( Collections.singletonList( PlaceGroup.PARKS ) ), "descrição" ) );
        places.add( new Place( "Zoológico Municipal de Curitiba", true, 3, 120, new ArrayList< Integer >( Collections.singletonList( PlaceGroup.PARKS ) ), "descrição" ) );
        places.add( new Place( "Praça da Espanha", true, 2, 60, new ArrayList< Integer >( Collections.singletonList( PlaceGroup.PARKS ) ),"descrição" ) );
        places.add( new Place( "Bosque Reinhard Maack", true, 1, 60, new ArrayList< Integer >( Collections.singletonList( PlaceGroup.PARKS ) ),"descrição" ) );
        places.add( new Place( "Passeio Público", true, 4, 90, new ArrayList< Integer >( Arrays.asList( PlaceGroup.LANDMARKS, PlaceGroup.PARKS ) ),"descrição" ) );

        places.add( new Place( "Praça Osório", true, 3, 60, new ArrayList< Integer >( Arrays.asList( PlaceGroup.LANDMARKS, PlaceGroup.PARKS ) ),"descrição" ) );
        places.add( new Place( "Praça Santos Andrade", true, 2, 30, new ArrayList< Integer >( Arrays.asList( PlaceGroup.LANDMARKS, PlaceGroup.PARKS ) ),"descrição" ) );
        places.add( new Place( "Ópera de Arame", true, 3, 60, new ArrayList< Integer >( Collections.singletonList( PlaceGroup.LANDMARKS ) ), "descrição" ) );
        places.add( new Place( "Largo da Ordem", true, 5, 60, new ArrayList< Integer >( Collections.singletonList( PlaceGroup.LANDMARKS ) ), "descrição" ) );
        places.add( new Place( "Estádio Joaquim Américo Guimarães", false, 2, 60, new ArrayList< Integer >( Collections.singletonList( PlaceGroup.LANDMARKS ) ), "descrição" ) );
        places.add( new Place( "Estádio Major Antônio Couto Pereira", false, 2, 60, new ArrayList< Integer >( Collections.singletonList( PlaceGroup.LANDMARKS ) ), "descrição" ) );
        places.add( new Place( "Estádio Vila Capanema", false, 2, 60, new ArrayList< Integer >( Collections.singletonList( PlaceGroup.LANDMARKS ) ), "descrição" ) );
        places.add( new Place( "Catedral Metropolitana de Curitiba", false, 5, 30, new ArrayList< Integer >( Collections.singletonList( PlaceGroup.LANDMARKS ) ), "descrição" ) );
        places.add( new Place( "Torre Panorâmica de Curitiba", false, 3, 60, new ArrayList< Integer >( Collections.singletonList( PlaceGroup.LANDMARKS ) ), "descrição" ) );
        places.add( new Place( "Memorial de Curitiba", false, 2, 30, new ArrayList< Integer >( Collections.singletonList( PlaceGroup.LANDMARKS ) ), "descrição" ) );
        places.add( new Place( "Mesquita Imam Ali ibn Abi Talib", false, 1, 30, new ArrayList< Integer >( Collections.singletonList( PlaceGroup.LANDMARKS ) ), "descrição" ) );
        places.add( new Place( "Praça Tiradentes", true, 5, 30, new ArrayList< Integer >( Arrays.asList( PlaceGroup.LANDMARKS, PlaceGroup.PARKS ) ), "descrição" ) );
        places.add( new Place( "Praça Rui Barbosa", true, 5, 60, new ArrayList< Integer >( Arrays.asList( PlaceGroup.LANDMARKS, PlaceGroup.PARKS ) ), "descrição" ) );

        places.add( new Place( "Museu Oscar Niemeyer", false, 5, 120, new ArrayList< Integer >( Arrays.asList( PlaceGroup.LANDMARKS, PlaceGroup.MUSEUMS ) ), "descrição" ) );
        places.add( new Place( "Museu Egípcio e Rosacruz", false, 1, 90, new ArrayList< Integer >( Collections.singletonList( PlaceGroup.MUSEUMS ) ), "descrição" ) );
        places.add( new Place( "Museu Paranaense", false, 2, 90, new ArrayList< Integer >( Collections.singletonList( PlaceGroup.MUSEUMS ) ), "descrição" ) );
        places.add( new Place( "Museu do Automóvel de Curitiba", false, 1, 90, new ArrayList< Integer >( Collections.singletonList( PlaceGroup.MUSEUMS ) ), "descrição" ) );
        places.add( new Place( "Museu Ferroviário", false, 2, 90, new ArrayList< Integer >( Collections.singletonList( PlaceGroup.MUSEUMS ) ), "descrição" ) );
        places.add( new Place( "Museu do Expedicionário", false, 2, 90, new ArrayList< Integer >( Collections.singletonList( PlaceGroup.MUSEUMS ) ), "descrição" ) );
        places.add( new Place( "Museu de Arte Contemporânea do Paraná", false, 1, 90, new ArrayList< Integer >( Collections.singletonList( PlaceGroup.MUSEUMS ) ), "descrição" ) );

        places.add( new Place( "Mercadoteca", false, 2, 120, new ArrayList< Integer >( Collections.singletonList( PlaceGroup.FOOD ) ), "descrição" ) );
        places.add( new Place( "Mercado Municipal de Curitiba", false, 4, 90, new ArrayList< Integer >( Arrays.asList( PlaceGroup.LANDMARKS, PlaceGroup.PARKS ) ), "descrição" ) );
        places.add( new Place( "Rua XV de Novembro", true, 5, 120, new ArrayList< Integer >( Arrays.asList( PlaceGroup.LANDMARKS, PlaceGroup.FOOD, PlaceGroup.SHOPPING ) ), "descrição" ) );
        places.add( new Place( "Shopping Estação", false, 3, 150, new ArrayList< Integer >( Arrays.asList( PlaceGroup.FOOD, PlaceGroup.SHOPPING ) ), "descrição" ) );
        places.add( new Place( "Shopping Curitiba", false, 3, 150, new ArrayList< Integer >( Arrays.asList( PlaceGroup.FOOD, PlaceGroup.SHOPPING ) ), "descrição" ) );
        places.add( new Place( "Shopping Palladium", false, 3, 150, new ArrayList< Integer >( Arrays.asList( PlaceGroup.FOOD, PlaceGroup.SHOPPING ) ), "descrição" ) );
        places.add( new Place( "Shopping Barigui", false, 3, 150, new ArrayList< Integer >( Arrays.asList( PlaceGroup.FOOD, PlaceGroup.SHOPPING ) ), "descrição" ) );
        places.add( new Place( "Shopping Palladium", false, 3, 150, new ArrayList< Integer >( Arrays.asList( PlaceGroup.FOOD, PlaceGroup.SHOPPING ) ), "descrição" ) );
        places.add( new Place( "Shopping Cidade", false, 1, 90, new ArrayList< Integer >( Arrays.asList( PlaceGroup.FOOD, PlaceGroup.SHOPPING ) ), "descrição" ) );
        places.add( new Place( "Shopping Total", false, 2, 90, new ArrayList< Integer >( Arrays.asList( PlaceGroup.FOOD, PlaceGroup.SHOPPING ) ), "descrição" ) );
        places.add( new Place( "Shopping Crystal", false, 2, 90, new ArrayList< Integer >( Arrays.asList( PlaceGroup.FOOD, PlaceGroup.SHOPPING ) ), "descrição" ) );
        places.add( new Place( "Shopping Mueller", false, 3, 150, new ArrayList< Integer >( Arrays.asList( PlaceGroup.FOOD, PlaceGroup.SHOPPING ) ), "descrição" ) );
        places.add( new Place( "Shopping Pátio Batel", false, 3, 150, new ArrayList< Integer >( Arrays.asList( PlaceGroup.FOOD, PlaceGroup.SHOPPING ) ), "descrição" ) );
        places.add( new Place( "Shopping Jardim das Américas", false, 1, 90, new ArrayList< Integer >( Arrays.asList( PlaceGroup.FOOD, PlaceGroup.SHOPPING ) ), "descrição" ) );

        postsRef.setValue( places );
    }
}
