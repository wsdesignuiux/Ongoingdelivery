package com.example.vaibhav.ongoingdelivery;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.Point;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.ahmadrosid.lib.drawroutemap.DrawMarker;
import com.ahmadrosid.lib.drawroutemap.DrawRouteMaps;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;

public class OngoingdeliveryActivity extends AppCompatActivity implements OnMapReadyCallback {
    private double radius = 1000;

    private static final String KEY_CAMERA_POSITION = "camera_position";
    private static final String KEY_LOCATION = "location";

    LinearLayout ll1;

    private GoogleMap mMap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ongoingdelivery);

        MapFragment mapFragment = (MapFragment) getFragmentManager()
                .findFragmentById(R.id.googleMap);
        mapFragment.getMapAsync(this);



        ll1 = findViewById(R.id.ll1);
        Thread background = new Thread() {
            public void run() {

                try {
                    // Thread will sleep for 5 seconds
                    sleep(4*1000);

                    ll1.setVisibility(View.GONE);

                    // After 5 seconds redirect to another intent


                    //Remove activity
                    finish();

                } catch (Exception e) {

                }
            }
        };

        // start thread
        background.start();


    }



    @SuppressLint("MissingPermission")
    @Override
    public void onMapReady(GoogleMap googleMap) {

        googleMap.setMyLocationEnabled(false);
        mMap = googleMap;


        LatLng origin = new LatLng(-7.788969, 110.338382);
        LatLng destination = new LatLng(-7.781200, 110.349709);

        DrawRouteMaps.getInstance(this)
                .draw(origin, destination, mMap);
        DrawMarker.getInstance(this).draw(mMap, origin, R.drawable.ic_caronmap, "Origin Location");
        DrawMarker.getInstance(this).draw(mMap, destination, R.drawable.ic_caronmap, "Destination Location");

        LatLngBounds bounds = new LatLngBounds.Builder()
                .include(origin)
                .include(destination).build();
        Point displaySize = new Point();
        getWindowManager().getDefaultDisplay().getSize(displaySize);
        mMap.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds, displaySize.x, 250, 30));

        LatLng latLng = new LatLng(-7.788969, 110.338382);

        double lat = -7.781200;
        double lng = 110.349709;


        googleMap.addCircle(new CircleOptions()
                .center(new LatLng(lat, lng))
                .radius(radius)
                .strokeColor(Color.BLUE)
                .strokeWidth(0f)
                .fillColor(Color.YELLOW)
                .fillColor(Color.parseColor("#000000")));


        // create marker
        MarkerOptions marker = new MarkerOptions().position(latLng).title("Job accepted by Jordan Hen");
        marker.icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_img2));
        // adding marker
        googleMap.addMarker(marker);


        //    marker.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ROSE));

        CameraPosition cameraPosition = new CameraPosition.Builder().target(
                latLng).zoom(13).build();



        googleMap.animateCamera(


                CameraUpdateFactory.newCameraPosition(cameraPosition));
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}