package com.example.jgaug.descubracuritiba;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

public class FirstScreen extends AppCompatActivity {

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );

        //Set to fullscreen
        requestWindowFeature( Window.FEATURE_NO_TITLE );
        getWindow( ).setFlags( WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN );

        setContentView( R.layout.activity_first_screen );

//        mStorageRef = FirebaseStorage.getInstance().getReference();
//        Object object = new String("Test");
//        DatabaseReference mFirebaseRef = FirebaseDatabase.getInstance().getReference();
//        mFirebaseRef.push().setValue(object, new DatabaseReference.CompletionListener() {
//            @Override
//            public void onComplete(DatabaseError databaseError, DatabaseReference reference) {
//                if (databaseError != null) {
//                    Log.e(TAG, "Failed to write message", databaseError.toException());
//                }
//            }
//        });
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
}
