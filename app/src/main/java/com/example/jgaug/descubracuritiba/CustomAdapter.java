package com.example.jgaug.descubracuritiba;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.jgaug.descubracuritiba.Helpers.DailyItinerary;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class CustomAdapter extends RecyclerView.Adapter {
    private LayoutInflater layoutinflater;
    private DailyItinerary listStorage;
    private Context context;
    private OnItemClickListener itemClickListener;

    public CustomAdapter( Context context, DailyItinerary dailyItinerary ) {
        this.context = context;
        this.layoutinflater = ( LayoutInflater ) context.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
        this.listStorage = dailyItinerary;
    }

    public void setOnItemClickListener( final CustomAdapter.OnItemClickListener itemClickListener ) {
        this.itemClickListener = itemClickListener;
    }

    public interface OnItemClickListener {
        void onClickDetalhes( int position );
        void onClickClima( int position );
        void onClickNavegar( int position );
        void onClickChangeTransportMode( int position );
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder( ViewGroup parent, int viewType ) {
        final LayoutInflater inflater = LayoutInflater.from( parent.getContext( ) );
        final View view = inflater.inflate( R.layout.itinerary_list_item, parent, false );

        return new ViewHolder( view );
    }

    @Override
    public void onBindViewHolder( final RecyclerView.ViewHolder holder, int position ) {
        final ViewHolder listViewHolder = ( ViewHolder ) holder;

        StorageReference mStorageRef = FirebaseStorage.getInstance( ).getReference( );
        mStorageRef.child( listStorage.getPlaces( ).get( position ).getImage( ) ).getDownloadUrl( ).addOnSuccessListener( new OnSuccessListener< Uri >( ) {
            @Override
            public void onSuccess( Uri uri ) {
                GlideUtil.loadImageFinal( context, uri.toString( ), ( ( ViewHolder ) holder ).placeImage );
            }
        } );

        listViewHolder.placeDetalhes.setOnClickListener( view -> itemClickListener.onClickDetalhes( listViewHolder.getAdapterPosition( ) ) );
        listViewHolder.placeClima.setOnClickListener( view -> itemClickListener.onClickClima( listViewHolder.getAdapterPosition( ) ) );
        listViewHolder.placeNavegar.setOnClickListener( view -> itemClickListener.onClickNavegar( listViewHolder.getAdapterPosition( ) ) );
        listViewHolder.changeTransportImage.setOnClickListener( view -> itemClickListener.onClickChangeTransportMode( listViewHolder.getAdapterPosition( ) ) );

        listViewHolder.placeName.setText( listStorage.getPlaces( ).get( position ).getName( ) );
        listViewHolder.placeDescription.setText( listStorage.getPlaces( ).get( position ).getDescription( ) );
        listViewHolder.placeVisitTime.setText( listStorage.getPlaces( ).get( position ).getVisitPeriod( ) );

        if( position == ( listStorage.getPlaces( ).size( ) - 1 ) ) {
            listViewHolder.travelTimeLayout.setVisibility( View.GONE );
        } else {
            String travelTime = "";
            if( listStorage.getPlaces( ).get( position ).isGoingOnFoot( ) ) {
                listViewHolder.transportImage.setImageResource( R.drawable.shoes );
                travelTime = listStorage.getPlaces( ).get( position + 1 ).getTravelTimeFromPreviousPlaceOnFoot( ) + " minuto(s) a pé";
            } else {
                listViewHolder.transportImage.setImageResource( R.drawable.car );
                travelTime = listStorage.getPlaces( ).get( position + 1 ).getTravelTimeFromPreviousPlaceByCar( ) + " minuto(s) de carro";
            }

            listViewHolder.travelTime.setText( travelTime );
        }
    }

    @Override
    public int getItemCount( ) {
        return listStorage.getPlaces( ).size( );
    }

    private static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView placeImage;
        TextView placeName;
        TextView placeDescription;
        TextView placeVisitTime;
        LinearLayout placeDetalhes;
        LinearLayout placeClima;
        LinearLayout placeNavegar;
        TextView travelTime;
        RelativeLayout travelTimeLayout;
        ImageView transportImage;
        ImageView changeTransportImage;

        private ViewHolder( View itemView ) {
            super( itemView );

            placeImage = itemView.findViewById( R.id.place_image );
            placeName = itemView.findViewById( R.id.place_name );
            placeDescription = itemView.findViewById( R.id.place_description );
            placeVisitTime = itemView.findViewById( R.id.place_visit_time );
            placeDetalhes = itemView.findViewById( R.id.btn_detalhes );
            placeClima = itemView.findViewById( R.id.btn_clima );
            placeNavegar = itemView.findViewById( R.id.btn_navegar );
            travelTime = itemView.findViewById( R.id.travelTime );
            travelTimeLayout = itemView.findViewById( R.id.travelTimeLayout );
            transportImage = itemView.findViewById( R.id.transport );
            changeTransportImage = itemView.findViewById( R.id.changeTransport );
        }
    }
}