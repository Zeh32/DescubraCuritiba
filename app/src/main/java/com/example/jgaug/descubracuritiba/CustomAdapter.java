package com.example.jgaug.descubracuritiba;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class CustomAdapter extends BaseAdapter {

    private LayoutInflater layoutinflater;
    private List< Place > listStorage;
    private Context context;

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
        ViewHolder listViewHolder;

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
