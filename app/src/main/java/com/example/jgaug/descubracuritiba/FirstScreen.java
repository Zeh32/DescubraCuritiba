package com.example.jgaug.descubracuritiba;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.IOException;


public class FirstScreen extends AppCompatActivity {
    private static final String TAG = "FirstScreen";
    private StorageReference mStorageRef;

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );

        //Set to fullscreen
        requestWindowFeature( Window.FEATURE_NO_TITLE );
        getWindow( ).setFlags( WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN );

        setContentView( R.layout.activity_first_screen );

        mStorageRef = FirebaseStorage.getInstance().getReference();

        Object object = new String("Test");

        DatabaseReference mFirebaseRef = FirebaseDatabase.getInstance().getReference();
        /*mFirebaseRef.push().setValue(object, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference reference) {
                if (databaseError != null) {
                    Log.e(TAG, "Failed to write message", databaseError.toException());
                }
            }
        });*/
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
