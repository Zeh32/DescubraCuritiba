package com.example.jgaug.descubracuritiba;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.jgaug.descubracuritiba.Api.DescubraCuritibaApi;
import com.example.jgaug.descubracuritiba.Api.Response.Distance;
import com.example.jgaug.descubracuritiba.Api.Response.DistanciaResponse;
import com.example.jgaug.descubracuritiba.Api.Response.Duration;
import com.example.jgaug.descubracuritiba.Api.Response.Element;
import com.example.jgaug.descubracuritiba.Api.Response.Row;
import com.example.jgaug.descubracuritiba.Api.endpoint.distanciaApi;
import com.example.jgaug.descubracuritiba.Helpers.DailyItinerary;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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

        listViewHolder.placeName.setText( listStorage.getPlaces( ).get( position ).getName( ) );
        listViewHolder.placeDescription.setText( listStorage.getPlaces( ).get( position ).getDescription( ) );
        listViewHolder.placeVisitTime.setText( listStorage.getPlaces( ).get( position ).getVisitPeriod( ) );

        if( position < ( listStorage.getPlaces( ).size( ) - 1 ) ) {
            populateTimeTravel( listStorage.getPlaces( ).get( position ).getLatitude( ) + "," + listStorage.getPlaces().get(position).getLongitude(),
                    listStorage.getPlaces( ).get( position + 1 ).getLatitude( ) + "," + listStorage.getPlaces( ).get( position + 1 ).getLongitude( ), listViewHolder );
        } else if( position == ( listStorage.getPlaces( ).size( ) - 1 ) ) {
            listViewHolder.travelTimeLayout.setVisibility( View.GONE );
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
        LinearLayout travelTimeLayout;

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
        }
    }

    private void populateTimeTravel( String latlonOrigem, String latlonDest, ViewHolder listviewholder ) {
        //TODO: consultar API do google maps aqui e obter tempo de deslocamento de carro
        final distanciaApi distanciaEndpoint = new DescubraCuritibaApi( ).distanciaApi( );
        final Integer[] distanciaAux = new Integer[ 1 ];
        String mode = "driving";

        retrofit2.Call<DistanciaResponse> call = distanciaEndpoint.getDistancia( "pt-BR", mode,latlonOrigem, latlonDest, "AIzaSyA2yt9xJV1gwgqJTpn-zUKnKIMK44iRCJA" );
        call.enqueue( new Callback< DistanciaResponse >( ) {
            @Override
            public void onResponse(@NonNull Call< DistanciaResponse > call, @NonNull Response< DistanciaResponse > response ) {
                DistanciaResponse distanciaResponse = response.body( );

                List<Row> lista = distanciaResponse.getRows( );
                Row row = lista.get( 0 );
                List<Element> elementList = row.getElements( );
                Element element = elementList.get( 0 );
                Duration duration = element.getDuration( );
                listviewholder.travelTime.setText( duration.getText() + " " + "de carro" );
//                distancia = distance.getValue( );
//
//                listaFinal.addAll(lista);
//
//                if(pesquisa == 2) { //total de pesquisas
//                    pesquisa = 0;
//
//                    1. Faça o itinerário
//                    2. Salve e passe para a próxima activity
//                }
//                pesquisa++;
            }

            @Override
            public void onFailure( @NonNull Call< DistanciaResponse > call, @NonNull Throwable t ) {

            }
        } );
    }
}
