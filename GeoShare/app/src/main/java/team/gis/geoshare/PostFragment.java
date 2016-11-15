package team.gis.geoshare;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class PostFragment extends Fragment implements  OnMapReadyCallback,View.OnClickListener {

    // Google Map
    private GoogleMap googleMap;
    private FloatingActionButton fab;
    private GoogleApiClient client;
    Context context;
    private double lat;
    private double lon;
    private int user_id;
    @Nullable
    @Override

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_maps, container, false);
        context = this.getActivity();
        get_user_id();
        fab = (FloatingActionButton) view.findViewById(R.id.fab);
        fab.setOnClickListener(this);

        try {
            // Loading map
            initializeMap();

        } catch (Exception e) {
            e.printStackTrace();
        }
        //buildGoogleApiClient();

        return view;
    }
    public void get_user_id(){
        Bundle extras = getActivity().getIntent().getExtras();
        if (extras != null) {
            user_id = extras.getInt("user_id");
            System.out.println("user id in new activity is "+user_id);
            //The key argument here must match that used in the other activity
        }
    }
//    synchronized void buildGoogleApiClient() {
//        client = new GoogleApiClient.Builder(context).addConnectionCallbacks(context)
//        .addOnConnectionFailedListener(context)
//        .addApi(LocationServices.API)
//        .build();
//    }
    /**
     * function to load map. If map is not created it will create it for you
     */
    private void initializeMap() {
        if (googleMap == null) {

           // View view = inflater.inflate(R.layout.layout_maps, null, false);
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

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;
        addMarker();
    }

    private void addMarker() {



        Location loc = get_location_manager();

        lat = loc.getLatitude();
        lon = loc.getLongitude();
        System.out.println();

        // create marker
        MarkerOptions marker = new MarkerOptions().position(new LatLng(lat, lon)).title("Hello Maps ");
        marker.icon(BitmapDescriptorFactory.fromResource(R.drawable.pointer));
        // adding marker
        googleMap.addMarker(marker);

        CameraPosition cameraPosition = new CameraPosition.Builder().target(
                new LatLng(lat, lon)).zoom(12).build();

        googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
        googleMap.getUiSettings().setMyLocationButtonEnabled(true);
    }

    @Override
    public void onClick(View view) {
//        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
//        LayoutInflater inflater = getActivity().getLayoutInflater();
//        final View dialogView = inflater.inflate(R.layout.layout_post, null);
//        dialogBuilder.setView(dialogView);
//
//        EditText postText = (EditText) dialogView.findViewById(R.id.post_text);
//        Button postButton = (Button) dialogView.findViewById(R.id.button_post);
//        postButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                // handle what you want to do with the post text that you enter
//            }
//        });
//
//
//        AlertDialog b = dialogBuilder.create();
//        b.show();
        Intent i = new Intent(getActivity(), post_activity.class);
        i.putExtra("lat",lat);
        i.putExtra("lon",lon);
        i.putExtra("user_id",user_id);
        startActivity(i);
    }



    public Location get_location_manager() {
        String locationProvider = LocationManager.NETWORK_PROVIDER;
// Or use LocationManager.GPS_PROVIDER
        LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        Location lastKnownLocation;
        if (ActivityCompat.checkSelfPermission(context, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            ActivityCompat.requestPermissions(getActivity(), new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    15);

        }
        lastKnownLocation = locationManager.getLastKnownLocation(locationProvider);
        System.out.println(lastKnownLocation.getLatitude() + ' '+lastKnownLocation.getLongitude());
        return lastKnownLocation;
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


}
