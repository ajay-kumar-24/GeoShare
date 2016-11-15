package team.gis.geoshare;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Point;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.Projection;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.concurrent.ExecutionException;

public class ViewFragment extends Fragment implements OnMapReadyCallback,GoogleMap.OnMarkerClickListener, GoogleMap.OnMapClickListener,GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener, GoogleMap.OnMyLocationButtonClickListener {

    // Google Map
    private GoogleMap googleMap;
    private FloatingActionButton fab;
    private Context context;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_maps, container, false);

        fab = (FloatingActionButton) view.findViewById(R.id.fab);
        fab.setVisibility(View.GONE);
        context = getActivity();
        try {
            // Loading map
            initializeMap();

        } catch (Exception e) {
            e.printStackTrace();
        }
        buildGoogleApiClient();
        get_user_id();
        return view;
    }
    GoogleApiClient client;
    synchronized void buildGoogleApiClient() {
        client = new GoogleApiClient.Builder(context)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
    }
    /*
    public void get_user_id() {
        Bundle extras = getActivity().getIntent().getExtras();
        if (extras != null) {
            user_id = extras.getInt("user_id");
            System.out.println("user id in new activity is " + user_id);
        }
    }
    /**
     * function to load map. If map is not created it will create it for you
     */
    private void initializeMap() {
        if (googleMap == null) {
            SupportMapFragment mapFrag = ((SupportMapFragment) this.getChildFragmentManager().findFragmentById(R.id.map));
            mapFrag.getMapAsync(this);

            // check if map is created successfully or not
            if (googleMap == null) {
                Toast.makeText(getActivity(),
                        "Sorry! unable to create maps", Toast.LENGTH_SHORT)
                        .show();
            }
        }
    }
    GoogleMap mMap;
    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
//        AppIndex.AppIndexApi.start(client, getIndexApiAction());
    }
    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        //  AppIndex.AppIndexApi.end(client, getIndexApiAction());
        client.disconnect();
    }
    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;
        mMap = googleMap;
        mMap.setOnMyLocationButtonClickListener(this);
        //addMarker();
    }

    private void addMarker(Location location) {
//  convert the location object to a LatLng object that can be used by the map API
        LatLng currentPosition = new LatLng(location.getLatitude(), location.getLongitude());

// zoom to the current location
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(currentPosition, 16));

// add a marker to the map indicating our current position
        mMap.addMarker(new MarkerOptions()
                .position(currentPosition)
                .snippet("Lat:" + location.getLatitude() + "Lng:" + location.getLongitude())
                .title("Current location")
                .icon(BitmapDescriptorFactory.defaultMarker( BitmapDescriptorFactory.HUE_BLUE))
        );
        mMap.setOnMarkerClickListener(this);
    }
    private void showPopup(final Activity context, Point p, Marker marker) {
        try {

            PopupWindow pw;
            int popupWidth = 300;
            int popupHeight = 300;
            //  Toast.makeText(this, popupHeight+"  ", Toast.LENGTH_SHORT).show();

            // Inflate the popup_layout.xml
            LinearLayout viewGroup = (LinearLayout) context.findViewById(R.id.pop_up);
            LayoutInflater layoutInflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View layout = layoutInflater.inflate(R.layout.reviews_pop_message, viewGroup);
            // t1 = (TextView) findViewById(R.id.post_tex);
            // Creating the PopupWindow
            //t1.append((CharSequence)"HELLOOOO");
            final PopupWindow popup = new PopupWindow(context);
            //Context q = layoutInflater.getContext();

            popup.setContentView(layout);
            popup.setWidth(popupWidth);
            popup.setHeight(popupHeight);
            popup.setFocusable(true);
            ((TextView) popup.getContentView().findViewById(R.id.tv_reviews_pop_messageText)).setText(marker.getTitle().toString());
            // Some offset to align the popup a bit to the right, and a bit down, relative to button's position.
            int OFFSET_X = 30;
            int OFFSET_Y = 30;

            // Clear the default translucent background
            //popup.setBackgroundDrawable(new BitmapDrawable());

            // Displaying the popup at the specified location, + offsets.
            popup.showAtLocation(layout, Gravity.NO_GRAVITY, p.x + OFFSET_X, p.y + OFFSET_Y);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    int user_id;
    public void get_user_id(){
        Bundle extras = getActivity().getIntent().getExtras();
        if (extras != null) {
            user_id = extras.getInt("user_id");
            System.out.println("user id in new activity is "+user_id);
            //The key argument here must match that used in the other activity
        }
    }
    @Override
    public void onLocationChanged(Location location) {
        mMap.clear();
        String param = "get_nn?user="+user_id+"&lat="+String.valueOf(location.getLatitude())+"&lon="+String.valueOf(location.getLongitude());
        //param = Uri.encode(param);
        new send_request().execute(param);
        send_request ob = new send_request();
        String names = null;
        JSONObject json1 = new JSONObject();
        try {
            names = ob.execute(param).get();
            System.out.println("names "+ names);
            json1 = new JSONObject(names);
            //String para = (String) json1.get("results");

            JSONArray res = json1.getJSONArray("results");

            for(int i =0; i< res.length();i++)
            {
                JSONObject obj = new JSONObject(res.getString(i));
                LatLng loc = new LatLng(Double.parseDouble(obj.getString("lat")),Double.parseDouble(obj.getString("lon")));
                mMap.addMarker(new MarkerOptions()

                        .position(loc)
                        .title(obj.getString("post"))

                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
                System.out.println("para"+res.get(i));
                System.out.println("HELI"+res.get(i).getClass());

            }




        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        catch (Throwable t)
        {

        }
        if (mMap != null) {
            addMarker(location);
        }
        String str = "Latitude: " + location.getLatitude() + "Longitude: " + location.getLongitude() + "alti :" + location.getAltitude();
        //Toast.makeText(getBaseContext(), str, Toast.LENGTH_LONG).show();
    }



    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 15: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {


                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }


    @Override
    public void onConnected(@Nullable Bundle bundle) {
        mLocationRequest = LocationRequest.create();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        mLocationRequest.setInterval(1000); // Update location every second
        //Toast.makeText(getBaseContext(), "HELLOO", Toast.LENGTH_LONG).show();
        if (ActivityCompat.checkSelfPermission(context, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(getActivity(), new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    15);

        }
        LocationServices.FusedLocationApi.requestLocationUpdates(client, mLocationRequest,  this);


    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onMapClick(LatLng latLng) {

    }
    GoogleApiClient mGoogleApiClient;
    LocationRequest mLocationRequest;
    Point p;
    @Override
    public boolean onMarkerClick(Marker marker) {
        //marker.getLocationOnScreen(location);
        Projection projection = mMap.getProjection();
        LatLng markerLocation = marker.getPosition();

        Point screenPosition = projection.toScreenLocation(markerLocation);
        p = screenPosition;
        //Initialize the Point with x, and y positions

        Toast.makeText(context, p.toString()+"  ", Toast.LENGTH_SHORT).show();
        //showPopup(GeoMaps.this, p);
        showPopup(getActivity(),p,marker);
        return false;
    }

    @Override
    public boolean onMyLocationButtonClick() {
        Toast.makeText(context, "MyLocation button clicked", Toast.LENGTH_SHORT).show();
        return false;
    }
}
