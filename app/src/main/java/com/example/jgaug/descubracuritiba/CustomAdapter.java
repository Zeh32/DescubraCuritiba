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

    public CustomAdapter( Context context, DailyItinerary customizedListView ) {
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
        mStorageRef.child(listStorage.getPlaces().get(position).getImage()).getDownloadUrl().addOnSuccessListener( new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                GlideUtil.loadImageFinal(context, uri.toString() , ((ViewHolder) holder).placeImage);
            }
        });

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

        listViewHolder.placeName.setText( listStorage.getPlaces().get( position ).getName( ) );
        listViewHolder.placeDescription.setText( listStorage.getPlaces().get( position ).getDescription( ) );
        listViewHolder.placeVisitTime.setText( listStorage.getPlaces().get( position ).getVisitPeriod( ) );
    }

    @Override
    public int getItemCount() {
        return listStorage.getPlaces().size();
    }

    private static class ViewHolder extends RecyclerView.ViewHolder{
        ImageView placeImage;
        TextView placeName;
        TextView placeDescription;
        TextView placeVisitTime;
        LinearLayout placeDetalhes;
        LinearLayout placeClima;
        LinearLayout placeNavegar;

        public ViewHolder(View itemView) {
            super(itemView);

            placeImage = (ImageView) itemView.findViewById(R.id.place_image);
            placeName = (TextView) itemView.findViewById(R.id.place_name);
            placeDescription = (TextView) itemView.findViewById(R.id.place_description);
            placeVisitTime = ( TextView ) itemView.findViewById( R.id.place_visit_time );
            placeDetalhes = (LinearLayout) itemView.findViewById(R.id.btn_detalhes);
            placeClima = (LinearLayout) itemView.findViewById(R.id.btn_clima);
            placeNavegar = (LinearLayout) itemView.findViewById(R.id.btn_navegar);

        }
    }

}
