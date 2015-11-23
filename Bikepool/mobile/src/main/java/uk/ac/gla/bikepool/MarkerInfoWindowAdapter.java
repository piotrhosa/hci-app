package uk.ac.gla.bikepool;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;

import java.util.HashMap;

public class MarkerInfoWindowAdapter implements GoogleMap.InfoWindowAdapter {

    Context context;
    private HashMap<Marker, BikePool> mMarkersHashMap;
    public MarkerInfoWindowAdapter(Context context, HashMap mMarkersHashMap) {
        this.context = context;
        this.mMarkersHashMap = mMarkersHashMap;
    }

    @Override
    public View getInfoWindow(Marker marker) {
        return null;
    }

    @Override
    public View getInfoContents(Marker marker) {
        View v  = LayoutInflater.from(context).inflate(R.layout.infowindow_layout, null);

        BikePool bikePool = mMarkersHashMap.get(marker);

        ImageView markerIcon = (ImageView) v.findViewById(R.id.marker_icon);

        TextView markerLabel = (TextView)v.findViewById(R.id.marker_label);

        //markerIcon.setImageResource(manageMarkerIcon(bikePool.getmIcon()));

        markerLabel.setText(bikePool.getName());

        return v;
    }
}