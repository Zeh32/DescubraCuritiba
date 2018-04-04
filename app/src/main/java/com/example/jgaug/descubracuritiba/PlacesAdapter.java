package com.example.jgaug.descubracuritiba;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.jgaug.descubracuritiba.Helpers.DailyItinerary;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class PlacesAdapter extends RecyclerView.Adapter {
    private LayoutInflater layoutinflater;
    private DailyItinerary dailyItinerary;
    private Context context;
    private OnItemClickListener itemClickListener;

    public PlacesAdapter( Context context, DailyItinerary dailyItinerary ) {
        this.context = context;
        this.layoutinflater = ( LayoutInflater ) context.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
        this.dailyItinerary = dailyItinerary;
    }

    public void setOnItemClickListener( final PlacesAdapter.OnItemClickListener itemClickListener ) {
        this.itemClickListener = itemClickListener;
    }

    public interface OnItemClickListener {
        void onClickDetalhes( int position );
        void onClickGo( int position );
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder( @NonNull ViewGroup parent, int viewType ) {
        final LayoutInflater inflater = LayoutInflater.from( parent.getContext( ) );
        final View view = inflater.inflate( R.layout.places_list_item, parent, false );

        return new ViewHolder( view );
    }

    @Override
    public void onBindViewHolder( @NonNull final RecyclerView.ViewHolder holder, int position ) {
        final ViewHolder listViewHolder = ( ViewHolder ) holder;

        StorageReference mStorageRef = FirebaseStorage.getInstance( ).getReference( );
        mStorageRef.child( dailyItinerary.getPlaces( ).get( position ).getImage( ) ).getDownloadUrl( ).addOnSuccessListener( new OnSuccessListener< Uri >( ) {
            @Override
            public void onSuccess( Uri uri ) {
                GlideUtil.loadImageFinal( context, uri.toString( ), listViewHolder.placePhoto );
            }
        } );

        listViewHolder.buttonDetails.setOnClickListener( view -> itemClickListener.onClickDetalhes( listViewHolder.getAdapterPosition( ) ) );
        listViewHolder.buttonGo.setOnClickListener( view -> itemClickListener.onClickGo( listViewHolder.getAdapterPosition( ) ) );

        listViewHolder.placeNameList.setText( dailyItinerary.getPlaces( ).get( position ).getName( ) );
    }

    @Override
    public int getItemCount( ) {
        return dailyItinerary.getPlaces( ).size( );
    }

    private static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView placePhoto;
        TextView placeNameList;
        Button buttonDetails;
        Button buttonGo;

        private ViewHolder( View itemView ) {
            super( itemView );

            placePhoto = itemView.findViewById(R.id.place_photo);
            placeNameList = itemView.findViewById(R.id.place_name_list);
            buttonDetails = itemView.findViewById(R.id.buttonDetails);
            buttonGo = itemView.findViewById(R.id.buttonGo);
        }
    }
}
