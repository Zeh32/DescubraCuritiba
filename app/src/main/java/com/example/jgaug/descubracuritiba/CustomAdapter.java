package com.example.jgaug.descubracuritiba;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class CustomAdapter extends BaseAdapter {

    private LayoutInflater layoutinflater;
    private List< Place > listStorage;
    private Context context;
    private StorageReference mStorageRef;

    public CustomAdapter( Context context, List< Place > customizedListView ) {
        this.context = context;
        this.layoutinflater = ( LayoutInflater ) context.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
        this.listStorage = customizedListView;
    }

    @Override
    public int getCount( ) {
        return listStorage.size( );
    }

    @Override
    public Object getItem( int position ) {
        return position;
    }

    @Override
    public long getItemId( int position ) {
        return position;
    }

    @Override
    public View getView( int position, View convertView, ViewGroup parent ) {
        final ViewHolder listViewHolder;
        mStorageRef = FirebaseStorage.getInstance().getReference();

        if( convertView == null ) {
            listViewHolder = new ViewHolder( );
            convertView = layoutinflater.inflate( R.layout.itinerary_list_item, parent, false );
            listViewHolder.placeImage = ( ImageView ) convertView.findViewById( R.id.place_image );
            listViewHolder.placeName = ( TextView ) convertView.findViewById( R.id.place_name );
            listViewHolder.placeDescription = ( TextView ) convertView.findViewById( R.id.place_description );

            convertView.setTag( listViewHolder );
        } else {
            listViewHolder = ( ViewHolder ) convertView.getTag( );
        }
        //listViewHolder.placeImage.setImageResource( listStorage.get( position ).getScreenShot( ) );

        try {
            final File localFile = File.createTempFile("images", "jpg");
            StorageReference imageRef = mStorageRef.child(listStorage.get( position ).getImage( ));
            imageRef.getFile(localFile)
                    .addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                            Bitmap bitmap = BitmapFactory.decodeFile(localFile.getAbsolutePath());
                            listViewHolder.placeImage.setImageBitmap(bitmap);
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    // Handle failed download
                    // ...
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }

        listViewHolder.placeName.setText( listStorage.get( position ).getName( ) );
        listViewHolder.placeDescription.setText( listStorage.get( position ).getDescription( ) );

        return convertView;
    }

    private static class ViewHolder {
        ImageView placeImage;
        TextView placeName;
        TextView placeDescription;
    }
}
