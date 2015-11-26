package uk.ac.gla.bikepool;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;
import java.util.HashMap;

public class BikePoolMap extends FragmentActivity {

    private GoogleMap mMap; // Might be null if Google Play services APK is not available.
    private HashMap<Marker, BikePool> mMarkersHashMap;
    private ArrayList<BikePool> bikePoolsArray = new ArrayList<>();
    //store the Polylines(animal traces) HashMap has relation marker id(key) polyline(value)
    private HashMap<String, Polyline> animalTraceHash;
    HashMap<String, ArrayList<LatLng>> allTracks;
    BitmapDescriptor activeMarker;
    BitmapDescriptor inActiveMarker;
    Button joinPoolButton;
    int whichPoolSelected = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bike_pool_map);
        setUpMapIfNeeded();
        activeMarker = BitmapDescriptorFactory.fromResource(R.drawable.marker);
        inActiveMarker = BitmapDescriptorFactory.fromResource(R.drawable.marker);

        joinPoolButton = (Button) findViewById(R.id.join_pool_btn);
        joinPoolButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Joined pool " + Integer.toString(whichPoolSelected),
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        setUpMapIfNeeded();
    }

    /**
     * Sets up the map if it is possible to do so (i.e., the Google Play services APK is correctly
     * installed) and the map has not already been instantiated.. This will ensure that we only ever
     * call {@link #setUpMap()} once when {@link #mMap} is not null.
     * <p/>
     * If it isn't installed {@link SupportMapFragment} (and
     * {@link com.google.android.gms.maps.MapView MapView}) will show a prompt for the user to
     * install/update the Google Play services APK on their device.
     * <p/>
     * A user can return to this FragmentActivity after following the prompt and correctly
     * installing/updating/enabling the Google Play services. Since the FragmentActivity may not
     * have been completely destroyed during this process (it is likely that it would only be
     * stopped or paused), {@link #onCreate(Bundle)} may not be called again so we should call this
     * method in {@link #onResume()} to guarantee that it will be called.
     */
    private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
                    .getMap();
            // Check if we were successful in obtaining the map.
            if (mMap != null) {
                setUpMap();
                plotMarkers(createBikePools());
                mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                    @Override
                    public boolean onMarkerClick(com.google.android.gms.maps.model.Marker marker) {
                        if (marker.getTitle().equals("start") && !mMarkersHashMap.get(marker).isMarkerClicked()) {
                            for (Marker m: mMarkersHashMap.keySet()){
                                if(mMarkersHashMap.get(m).isMarkerClicked() && m.getTitle().equals("start") &&
                                        mMarkersHashMap.get(m).getId() != mMarkersHashMap.get(marker).getId()){
                                    BikePool pool = mMarkersHashMap.get(m);
                                    pool.setMarkerClicked(false);
                                    m.setIcon(BitmapDescriptorFactory.fromBitmap(writeTextOnDrawable(R.drawable.marker_grey,
                                            pool.getStartTime(), Integer.toString(pool.getMembersNo()))));
                                    m.setAlpha((float)0.5);
                                    whichPoolSelected = 0;
                                    joinPoolButton.setBackgroundColor(Color.GRAY);
                                }
                            }
                            BikePool pool =  mMarkersHashMap.get(marker);

                            whichPoolSelected =pool.getId();
                            pool.setMarkerClicked(true);
                            marker.setIcon(BitmapDescriptorFactory.fromBitmap(writeTextOnDrawable(R.drawable.marker,
                                    pool.getStartTime(), Integer.toString(pool.getMembersNo()))));
                            marker.setAlpha(1);
                            joinPoolButton.setBackgroundColor(Color.GREEN);

                        }
                        return true;
                    }
                });

                mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {

                    @Override
                    public void onMapClick(LatLng latLng) {
                        for (Marker m: mMarkersHashMap.keySet()){
                            if(mMarkersHashMap.get(m).isMarkerClicked() && m.getTitle().equals("start")){
                                BikePool pool = mMarkersHashMap.get(m);
                                pool.setMarkerClicked(false);
                                m.setIcon(BitmapDescriptorFactory.fromBitmap(writeTextOnDrawable(R.drawable.marker_grey,
                                        pool.getStartTime(), Integer.toString(pool.getMembersNo()))));
                                m.setAlpha((float)0.5);
                                whichPoolSelected = 0;
                                joinPoolButton.setBackgroundColor(Color.GRAY);
                            }
                        }
                    }

                });
            }
        }
    }




    private void plotMarkers(ArrayList<BikePool> pools) {
        mMarkersHashMap = new HashMap<>();
        if(pools.size() > 0) {
            for (BikePool bikepool : pools) {
                // Create user marker with custom icon and other options
                MarkerOptions markerOption = new MarkerOptions().position(new LatLng(bikepool.getStartLocation().getLatitude(), bikepool.getStartLocation().getLongitude()));
                markerOption.icon(BitmapDescriptorFactory.fromBitmap(writeTextOnDrawable(R.drawable.marker_grey,
                        bikepool.getStartTime(), Integer.toString(bikepool.getMembersNo()))));

                Marker currentStartMarker = mMap.addMarker(markerOption);
                mMarkersHashMap.put(currentStartMarker, bikepool);
                currentStartMarker.setTitle("start");
                currentStartMarker.setAlpha((float)0.5);

                MarkerOptions markerOption2 = new MarkerOptions().position(new LatLng(bikepool.getFinishLocation().getLatitude(), bikepool.getFinishLocation().getLongitude()));
                markerOption2.icon(BitmapDescriptorFactory.fromBitmap(writeTextOnDrawable(R.drawable.finish, "","")));
                Marker currentEndMarker = mMap.addMarker(markerOption2);
                currentEndMarker.setTitle("end");
                mMarkersHashMap.put(currentEndMarker, bikepool);

                int color = 0;
                switch (bikepool.getId()){
                    case 1:
                        color = Color.BLUE;
                        break;
                    case 2:
                        color = Color.RED;
                        break;
                    case 3:
                        color = Color.GREEN;
                        break;
                }

                Polyline line = mMap.addPolyline(new PolylineOptions()
                        .width(5)
                        .color(color));
                line.setPoints(bikepool.getRoutePoints());
                bikepool.setRoute(line);

            }
        }
    }

    private Bitmap writeTextOnDrawable(int drawableId, String startTime, String numberOfPeople) {

        Bitmap bm = BitmapFactory.decodeResource(getResources(), drawableId)
                .copy(Bitmap.Config.ARGB_8888, true);

        Typeface tf = Typeface.create("Helvetica", Typeface.BOLD);

        Paint paint = new Paint();
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.BLACK);
        paint.setTypeface(tf);
        paint.setTextAlign(Paint.Align.CENTER);
        paint.setTextSize(convertToPixels(this, 10));

        Rect textRect = new Rect();
        paint.getTextBounds(startTime, 0, startTime.length(), textRect);

        Canvas canvas = new Canvas(bm);

        //If the text is bigger than the canvas , reduce the font size
        if(textRect.width() >= (canvas.getWidth() - 4))     //the padding on either sides is considered as 4, so as to appropriately fit in the text
            paint.setTextSize(convertToPixels(this, 7));        //Scaling needs to be used for different dpi's

        //Calculate the positions
        int xPos = (canvas.getWidth() / 2)+5;     //-2 is for regulating the x position offset

        //"- ((paint.descent() + paint.ascent()) / 2)" is the distance from the baseline to the center.
        int yPos = (int) ((canvas.getHeight() / 2) - ((paint.descent() + paint.ascent()) / 2)) -30 ;

        canvas.drawText(startTime, xPos, yPos, paint);
        canvas.drawText(numberOfPeople+" people", xPos, yPos+20, paint);
        canvas.drawText("Click", xPos, yPos+40, paint);

        return  bm;
    }



    public static int convertToPixels(Context context, int nDP)
    {
        final float conversionScale = context.getResources().getDisplayMetrics().density;

        return (int) ((nDP * conversionScale) + 0.5f) ;

    }


    public ArrayList<BikePool> createBikePools() {
        Location location = new Location(Constants.locationProvider);
        location.setLatitude(55.853081);
        location.setLongitude(-4.245957);
        Location location2 = new Location(Constants.locationProvider);
        location2.setLatitude(55.878444);
        location2.setLongitude(-4.334868);
        BikePool pool1 = new BikePool(1,"", location, location2,"1" ,"2" , getRoute(1));

        Location location3 = new Location(Constants.locationProvider);
        location3.setLatitude(55.873544);
        location3.setLongitude(-4.291769);
        Location location4 = new Location(Constants.locationProvider);
        location4.setLatitude(55.852054);
        location4.setLongitude(-4.258801);
        BikePool pool2 = new BikePool(2,"", location3, location4,"3","4", getRoute(2));

        Location location5 = new Location(Constants.locationProvider);
        location5.setLatitude(55.872696);
        location5.setLongitude(-4.292622);
        Location location6 = new Location(Constants.locationProvider);
        location6.setLatitude(55.898506);
        location6.setLongitude(-4.298219);
        BikePool pool3 = new BikePool(3,"", location5, location6,"4","5", getRoute(3));

        bikePoolsArray.add(pool1);
        bikePoolsArray.add(pool2);
        bikePoolsArray.add(pool3);
        return bikePoolsArray;
    }

    public ArrayList<LatLng> getRoute(int routeId){
        switch (routeId){
            case 1:
                ArrayList<LatLng> routeA = new ArrayList<>();
                routeA.add(new LatLng(55.853081, -4.245957));
                routeA.add(new LatLng(55.854556, -4.250856));
                routeA.add(new LatLng(55.854506, -4.251590));
                routeA.add(new LatLng(55.856053, -4.257209));
                routeA.add(new LatLng(55.856664, -4.264569));
                routeA.add(new LatLng(55.856418, -4.269584));
                routeA.add(new LatLng(55.856592, -4.274310));
                routeA.add(new LatLng(55.857228, -4.279024));
                routeA.add(new LatLng(55.857451, -4.279269));
                routeA.add(new LatLng(55.857805, -4.281650));
                routeA.add(new LatLng(55.858574, -4.283871));
                routeA.add(new LatLng(55.858738, -4.285344));
                routeA.add(new LatLng(55.858566, -4.286136));
                routeA.add(new LatLng(55.858566, -4.286136));
                routeA.add(new LatLng(55.858566, -4.286136));
                routeA.add(new LatLng(55.860892, -4.295201));
                routeA.add(new LatLng(55.860892, -4.295201));
                routeA.add(new LatLng(55.862135, -4.295522));
                routeA.add(new LatLng(55.862939, -4.297461));
                routeA.add(new LatLng(55.862939, -4.297461));
                routeA.add(new LatLng(55.864669, -4.301608));
                routeA.add(new LatLng(55.865439, -4.302495));
                routeA.add(new LatLng(55.865439, -4.302495));
                routeA.add(new LatLng(55.865827, -4.305180));
                routeA.add(new LatLng(55.866451, -4.305186));
                routeA.add(new LatLng(55.867093, -4.304780));
                routeA.add(new LatLng(55.867884, -4.306189));
                routeA.add(new LatLng(55.868193, -4.307546));
                routeA.add(new LatLng(55.868143, -4.308865));
                routeA.add(new LatLng(55.867844, -4.311204));
                routeA.add(new LatLng(55.870066, -4.322557));
                routeA.add(new LatLng(55.870527, -4.322486));
                routeA.add(new LatLng(55.870614, -4.323483));
                routeA.add(new LatLng(55.871731, -4.325757));
                routeA.add(new LatLng(55.872181, -4.327710));
                routeA.add(new LatLng(55.873197, -4.327395));
                routeA.add(new LatLng(55.873600, -4.327588));
                routeA.add(new LatLng(55.875656, -4.327136));
                routeA.add(new LatLng(55.875671, -4.327308));
                routeA.add(new LatLng(55.876022, -4.327111));
                routeA.add(new LatLng(55.876079, -4.327481));
                routeA.add(new LatLng(55.875721, -4.327769));
                routeA.add(new LatLng(55.876491, -4.327743));
                routeA.add(new LatLng(55.877323, -4.330524));
                routeA.add(new LatLng(55.878444, -4.334868));
                return routeA;
            case 2:
                ArrayList<LatLng> routeB = new ArrayList<>();
                routeB.add(new LatLng(55.873544, -4.291769));
                routeB.add(new LatLng(55.872657, -4.290640));
                routeB.add(new LatLng(55.872079, -4.285788));
                routeB.add(new LatLng(55.871994, -4.284969));
                routeB.add(new LatLng(55.867953, -4.287703));
                routeB.add(new LatLng(55.866339, -4.289312));
                routeB.add(new LatLng(55.864880, -4.284745));
                routeB.add(new LatLng(55.864090, -4.285447));
                routeB.add(new LatLng(55.863130, -4.283306));
                routeB.add(new LatLng(55.863137, -4.282862));
                routeB.add(new LatLng(55.861465, -4.283241));
                routeB.add(new LatLng(55.861179, -4.283425));
                routeB.add(new LatLng(55.861717, -4.285840));
                routeB.add(new LatLng(55.859984, -4.287038));
                routeB.add(new LatLng(55.860050, -4.288469));
                routeB.add(new LatLng(55.859313, -4.289113));
                routeB.add(new LatLng(55.859893, -4.291882));
                routeB.add(new LatLng(55.858804, -4.292665));
                routeB.add(new LatLng(55.858110, -4.289636));
                routeB.add(new LatLng(55.856744, -4.290612));
                routeB.add(new LatLng(55.856620, -4.288304));
                routeB.add(new LatLng(55.855577, -4.283366));
                routeB.add(new LatLng(55.854361, -4.280676));
                routeB.add(new LatLng(55.853715, -4.278644));
                routeB.add(new LatLng(55.853664, -4.274590));
                routeB.add(new LatLng(55.854110, -4.269703));
                routeB.add(new LatLng(55.854352, -4.264925));
                routeB.add(new LatLng(55.853688, -4.258548));
                routeB.add(new LatLng(55.852092, -4.259120));
                routeB.add(new LatLng(55.852054, -4.258801));

                return routeB;
            case 3:
                ArrayList<LatLng> routeC = new ArrayList<>();
                routeC.add(new LatLng(55.872696, -4.292622));
                routeC.add(new LatLng(55.872924, -4.292487));
                routeC.add(new LatLng(55.874068, -4.294977));
                routeC.add(new LatLng(55.876740, -4.292063));
                routeC.add(new LatLng(55.877874, -4.290312));
                routeC.add(new LatLng(55.878086, -4.290493));
                routeC.add(new LatLng(55.878770, -4.292442));
                routeC.add(new LatLng(55.878987, -4.292243));
                routeC.add(new LatLng(55.879286, -4.292369));
                routeC.add(new LatLng(55.879534, -4.291846));
                routeC.add(new LatLng(55.879468, -4.291539));
                routeC.add(new LatLng(55.880004, -4.291655));
                routeC.add(new LatLng(55.880541, -4.292918));
                routeC.add(new LatLng(55.881537, -4.293513));
                routeC.add(new LatLng(55.881790, -4.293847));
                routeC.add(new LatLng(55.881755, -4.292322));
                routeC.add(new LatLng(55.881968, -4.292160));
                routeC.add(new LatLng(55.882762, -4.289976));
                routeC.add(new LatLng(55.882964, -4.289805));
                routeC.add(new LatLng(55.883076, -4.289354));
                routeC.add(new LatLng(55.883749, -4.288975));
                routeC.add(new LatLng(55.884093, -4.289724));
                routeC.add(new LatLng(55.884366, -4.289778));
                routeC.add(new LatLng(55.885226, -4.291158));
                routeC.add(new LatLng(55.885393, -4.292042));
                routeC.add(new LatLng(55.886178, -4.295498));
                routeC.add(new LatLng(55.887437, -4.297600));
                routeC.add(new LatLng(55.888095, -4.297302));
                routeC.add(new LatLng(55.889648, -4.298069));
                routeC.add(new LatLng(55.891525, -4.300334));
                routeC.add(new LatLng(55.892729, -4.301416));
                routeC.add(new LatLng(55.893382, -4.301281));
                routeC.add(new LatLng(55.894136, -4.300812));
                routeC.add(new LatLng(55.895461, -4.301552));
                routeC.add(new LatLng(55.897485, -4.305287));
                routeC.add(new LatLng(55.898274, -4.306622));
                routeC.add(new LatLng(55.899503, -4.305810));
                routeC.add(new LatLng(55.899144, -4.303040));
                routeC.add(new LatLng(55.899260, -4.302688));
                routeC.add(new LatLng(55.897859, -4.300694));
                routeC.add(new LatLng(55.898506, -4.298219));
                return routeC;
        }
        return new ArrayList<>();
    }

    /**
     * This is where we can add markers or lines, add listeners or move the camera. In this case, we
     * just add a marker near Africa.
     * <p/>
     * This should only be called once and when we are sure that {@link #mMap} is not null.
     */
    private void setUpMap() {
//        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
//        Criteria criteria = new Criteria();
//
//        Location location = null;
//        try {
//            location = locationManager.getLastKnownLocation(locationManager.getBestProvider(criteria, false));
//        } catch (SecurityException e) {
//            Log.e("PERMISSION_EXCEPTION", "PERMISSION_NOT_GRANTED");
//        }

        Location location = new Location("");
        location.setLatitude(55.872314);
        location.setLongitude(-4.288154);


        if (location != null){
            Log.e("LOCATION NOT NULL", "NOT NULL");
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(
                    new LatLng(location.getLatitude(), location.getLongitude()), 13));

            CameraPosition cameraPosition = new CameraPosition.Builder()
                    .target(new LatLng(location.getLatitude(), location.getLongitude()))      // Sets the center of the map to location user
                    .zoom(11)                   // Sets the zoom
                    .bearing(60)                // Sets the orientation of the camera to east
                    .tilt(0)                   // Sets the tilt of the camera to 30 degrees
                    .build();                   // Creates a CameraPosition from the builder
            mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

        }
    }
}
