package com.example.jgaug.descubracuritiba;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.jgaug.descubracuritiba.Helpers.Place;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class CustomAdapter extends RecyclerView.Adapter {
    private LayoutInflater layoutinflater;
    private List< Place > listStorage;
    private Context context;
     OnItemClickListener itemClickListener;

    public CustomAdapter( Context context, List< Place > customizedListView ) {
        this.context = context;
        this.layoutinflater = ( LayoutInflater ) context.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
        this.listStorage = customizedListView;
    }

    public void setOnItemClickListener(final CustomAdapter.OnItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public interface OnItemClickListener {
        void onClickDetalhes(int position);

        void onClickClima(int position);

        void onClickNavegar(int position);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        final View view = inflater.inflate(R.layout.itinerary_list_item, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        final ViewHolder listViewHolder = (ViewHolder) holder;

        StorageReference mStorageRef = FirebaseStorage.getInstance( ).getReference( );
        mStorageRef.child(listStorage.get(position).getImage()).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                GlideUtil.loadImageFinal(context, uri.toString() , ((ViewHolder) holder).placeImage);
            }
        });
//        try {
//            final File localFile = File.createTempFile( "images", "jpg" );
//            StorageReference imageRef = mStorageRef.child( listStorage.get( position ).getImage( ) );
//            imageRef.getFile( localFile ).addOnSuccessListener( new OnSuccessListener< FileDownloadTask.TaskSnapshot >( ) {
//                @Override
//                public void onSuccess( FileDownloadTask.TaskSnapshot taskSnapshot ) {
//                    Bitmap bitmap = BitmapFactory.decodeFile( localFile.getAbsolutePath( ) );
//                    listViewHolder.placeImage.setImageBitmap( bitmap );
//                }
//            } ).addOnFailureListener( new OnFailureListener( ) {
//                @Override
//                public void onFailure( @NonNull Exception exception ) {
//                    // Handle failed download
//                    // ...
//                }
//            } );
//        } catch( IOException e ) {
//            e.printStackTrace( );
//        }

        listViewHolder.placeDetalhes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                itemClickListener.onClickDetalhes(listViewHolder.getAdapterPosition());
            }
        });
        listViewHolder.placeClima.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                itemClickListener.onClickClima(listViewHolder.getAdapterPosition());
            }
        });
        listViewHolder.placeNavegar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                itemClickListener.onClickNavegar(listViewHolder.getAdapterPosition());
            }
        });
        listViewHolder.placeName.setText( listStorage.get( position ).getName( ) );
        listViewHolder.placeDescription.setText( listStorage.get( position ).getDescription( ) );
    }

    @Override
    public int getItemCount() {
        return listStorage.size( );
    }

    private static class ViewHolder extends RecyclerView.ViewHolder{
        ImageView placeImage;
        TextView placeName;
        TextView placeDescription;
        LinearLayout placeDetalhes;
        LinearLayout placeClima;
        LinearLayout placeNavegar;

        public ViewHolder(View itemView) {
            super(itemView);

            placeImage = (ImageView) itemView.findViewById(R.id.place_image);
            placeName = (TextView) itemView.findViewById(R.id.place_name);
            placeDescription = (TextView) itemView.findViewById(R.id.place_description);
            placeDetalhes = (LinearLayout) itemView.findViewById(R.id.btn_detalhes);
            placeClima = (LinearLayout) itemView.findViewById(R.id.btn_clima);
            placeNavegar = (LinearLayout) itemView.findViewById(R.id.btn_navegar);

        }
    }

}
