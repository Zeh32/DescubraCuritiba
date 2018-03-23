package com.example.jgaug.descubracuritiba;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
//    private int travelMode = 0;
//    private String[] times = new String[ 20 ];
//    private int[] modes = new int[ 20 ];
//    private String mode = "driving";

    public CustomAdapter( Context context, DailyItinerary dailyItinerary ) {
        this.context = context;
        this.layoutinflater = ( LayoutInflater ) context.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
        this.listStorage = dailyItinerary;
        //this.modes = modes;
    }

    public void setOnItemClickListener( final CustomAdapter.OnItemClickListener itemClickListener ) {
        this.itemClickListener = itemClickListener;
    }

    public interface OnItemClickListener {
        void onClickDetalhes( int position );

        void onClickClima( int position );

        void onClickNavegar( int position );

        //void onClickMode( int position );
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

        listViewHolder.placeDetalhes.setOnClickListener( new View.OnClickListener( ) {
            @Override
            public void onClick( View view ) {
                itemClickListener.onClickDetalhes( listViewHolder.getAdapterPosition( ) );
            }
        } );
        listViewHolder.placeClima.setOnClickListener( new View.OnClickListener( ) {
            @Override
            public void onClick( View view ) {
                itemClickListener.onClickClima( listViewHolder.getAdapterPosition( ) );
            }
        } );
        listViewHolder.placeNavegar.setOnClickListener( new View.OnClickListener( ) {
            @Override
            public void onClick( View view ) {
                itemClickListener.onClickNavegar( listViewHolder.getAdapterPosition( ) );
            }
        } );
//        listViewHolder.changeMode.setOnClickListener( new View.OnClickListener( ) {
//            @Override
//            public void onClick( View v ) {
//                itemClickListener.onClickMode( listViewHolder.getAdapterPosition( ) );
//            }
//        } );

//        if( modes[ position ] == 1 ) {
//            mode = "walking";
//        } else {
//            mode = "driving";
//        }

        listViewHolder.placeName.setText( listStorage.getPlaces( ).get( position ).getName( ) );
        listViewHolder.placeDescription.setText( listStorage.getPlaces( ).get( position ).getDescription( ) );
        listViewHolder.placeVisitTime.setText( listStorage.getPlaces( ).get( position ).getVisitPeriod( ) );

        if( position < ( listStorage.getPlaces( ).size( ) - 1 ) ) {
            String travelTime = listStorage.getPlaces( ).get( position + 1 ).getTravelTimeFromPreviousPlace( ) + " minutos de carro";
            listViewHolder.travelTime.setText( travelTime );
        } else if( position == ( listStorage.getPlaces( ).size( ) - 1 ) ) {
            listViewHolder.travelTimeLayout.setVisibility( View.GONE );
        }
//        if( position < ( listStorage.getPlaces( ).size( ) - 1 ) ) {
//            populateTimeTravel( listStorage.getPlaces( ).get( position ).getLatitude( ) + "," + listStorage.getPlaces( ).get( position ).getLongitude( ), listStorage.getPlaces( ).get( position + 1 ).getLatitude( ) + "," + listStorage.getPlaces( ).get( position + 1 ).getLongitude( ), listViewHolder, position );
//        } else if( position == ( listStorage.getPlaces( ).size( ) - 1 ) ) {
//            listViewHolder.travelTimeLayout.setVisibility( View.GONE );
//        }
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
        LinearLayout travelTimeLayout;
        //ImageView imageVehicle;
        //Button changeMode;

        public ViewHolder( View itemView ) {
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
            //imageVehicle = itemView.findViewById( R.id.imageVehicle );
            //changeMode = itemView.findViewById( R.id.changeMode );
        }
    }

//    private void populateTimeTravel( String latlonOrigem, String latlonDest, ViewHolder listviewholder, int position ) {
//        final distanciaApi distanciaEndpoint = new DescubraCuritibaApi( ).distanciaApi( );
//
//        retrofit2.Call< DistanciaResponse > call = distanciaEndpoint.getDistancia( "pt-BR", mode, latlonOrigem, latlonDest, "AIzaSyA2yt9xJV1gwgqJTpn-zUKnKIMK44iRCJA" );
//        call.enqueue( new Callback< DistanciaResponse >( ) {
//            @Override
//            public void onResponse( @NonNull Call< DistanciaResponse > call, @NonNull Response< DistanciaResponse > response ) {
//                DistanciaResponse distanciaResponse = response.body( );
//
//                List< Row > lista = distanciaResponse.getRows( );
//                Row row = lista.get( 0 );
//                List< Element > elementList = row.getElements( );
//                Element element = elementList.get( 0 );
//                Duration duration = element.getDuration( );
//                if( modes[ position ] == 0 ) {
//                    listviewholder.travelTime.setText( duration.getText( ) + " " + "de carro" );
//                    listviewholder.imageVehicle.setImageResource( R.drawable.car );
//                    listviewholder.changeMode.setText( "A pé" );
//                } else {
//                    listviewholder.travelTime.setText( duration.getText( ) + " " + "a pé" );
//                    listviewholder.imageVehicle.setImageResource( R.drawable.footprints );
//                    listviewholder.changeMode.setText( "Carro" );
//                }
//                times[ position ] = duration.getText( );
//            }
//
//            @Override
//            public void onFailure( @NonNull Call< DistanciaResponse > call, @NonNull Throwable t ) {
//
//            }
//        } );
//    }
}
