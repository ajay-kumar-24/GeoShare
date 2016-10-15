package team.gis.geoshare;

import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.drive.Drive;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.Console;
import java.util.ArrayList;
import java.util.List;

import static team.gis.geoshare.R.drawable.pointer;

public class GeoMaps extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_geo_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
      /*  GoogleApiClient mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this /* FragmentActivity ,
                        new GoogleApiClient.OnConnectionFailedListener() {
                            @Override
                            public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

                            }
                        } /* OnConnectionFailedListener ).build();*/

        // mMap.setMyLocationEnabled(true);
        //mMap.setMyLocationEnabled(true);
       /* LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Criteria criteria = new Criteria();

       Location location = locationManager.getLastKnownLocation(locationManager.getBestProvider(criteria, false));
*/
        // Add a marker in Sydney and move the camera

        //Location l = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        LatLng sydney = new LatLng(34.0224,-118.2851);
        LatLng [] ln = new LatLng[]{
                new LatLng(34.0224,-118.2849),new LatLng(34.0224,-118.2853) ,new LatLng(34.0224,-118.2858),
                new LatLng(34.0224,-118.2900),new LatLng(34.0224,-118.2800)
        };
        String [] st  = new String[]{"Free coffee found here","Met Ross here","Loc 3"};
        CircleOptions circleOptions = new CircleOptions()
                .center(sydney)
                .radius(100)
                .strokeWidth(10)
                .fillColor(Color.argb(50, 0, 0, 255))
                .clickable(true);
        Circle circle = mMap.addCircle(circleOptions);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney,17));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(17));
         // In meters

// Get back the mutable Circle


        Drawable iconDrawable = getResources().getDrawable(pointer);

        Bitmap iconBmp = ((BitmapDrawable) iconDrawable).getBitmap();
        List<MarkerOptions> markers = new ArrayList<MarkerOptions>();
        for(int ix = 0; ix < ln.length; ix++) {
           /* markers.add(new MarkerOptions()

                    .position(ln[ix])

                    .icon(BitmapDescriptorFactory.fromBitmap(iconBmp)));*/

            float[] distance = new float[2];

            Location.distanceBetween( ln[ix].latitude, ln[ix].longitude,
                    circle.getCenter().latitude, circle.getCenter().longitude, distance);
            if( distance[0] < circle.getRadius()  ) {
                mMap.addMarker(new MarkerOptions()

                        .position(ln[ix])

                        .icon(BitmapDescriptorFactory.fromBitmap(iconBmp)))
                        .setTitle(st[ix]);
            }

        }
/*
        LatLngBounds.Builder b = new LatLngBounds.Builder();
        for (MarkerOptions m : markers) {
            b.include(m.getPosition());
        }
        LatLngBounds bounds = b.build();
//Change the padding as per needed
        CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, 25,25,8);
        mMap.animateCamera(cu);*/







    }

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("GeoMaps Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        AppIndex.AppIndexApi.start(client, getIndexApiAction());
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.end(client, getIndexApiAction());
        client.disconnect();
    }
}
