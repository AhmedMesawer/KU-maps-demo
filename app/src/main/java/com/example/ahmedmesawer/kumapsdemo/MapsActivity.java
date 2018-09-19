package com.example.ahmedmesawer.kumapsdemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.maps.DirectionsApi;
import com.google.maps.GeoApiContext;
import com.google.maps.PendingResult;
import com.google.maps.model.DirectionsResult;
import com.google.maps.model.TravelMode;

import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap map;
    private LatLng from = new LatLng(29.3415092, 47.9096156);
    private LatLng to;
    private static final String TAG = MapsActivity.class.getSimpleName();
    private boolean isFirstTime = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.

    }

    @Override
    protected void onResume() {
        super.onResume();

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    private GeoApiContext geoApiContext() {
        return new GeoApiContext.Builder()
                .apiKey("AIzaSyCKApqTbni8xCmZb-lJ6yFGk-_yaWngE3I")
                .build();
    }

    private void getDirections() {
        if (from != null && to != null) {
            DirectionsApi.newRequest(geoApiContext())
                    .mode(TravelMode.DRIVING)
                    .origin(new com.google.maps.model.LatLng(from.latitude, from.longitude))
                    .destination(new com.google.maps.model.LatLng(to.latitude, to.longitude))
                    .setCallback(new PendingResult.Callback<DirectionsResult>() {
                        @Override
                        public void onResult(DirectionsResult result) {
                            Log.d(TAG, "onResult: success");
                            runOnUiThread(() -> addMarkersToMaps(result));
                        }

                        @Override
                        public void onFailure(Throwable e) {
                            Log.d(TAG, "onFailure: " + e.getMessage());
                        }
                    });
        }
    }

    private void addMarkersToMaps(DirectionsResult result) {
        map.addMarker(new MarkerOptions().position(
                new LatLng(result.routes[0].legs[0].startLocation.lat,
                        result.routes[0].legs[0].startLocation.lng))
                .title(result.routes[0].legs[0].startAddress));

        map.addMarker(new MarkerOptions().position(
                new LatLng(result.routes[0].legs[0].endLocation.lat,
                        result.routes[0].legs[0].endLocation.lng))
                .title(result.routes[0].legs[0].endAddress)
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_flag)))
                .setSnippet(getEndLocationTitle(result));
        map.addPolyline(getPolyline(result));
        isFirstTime = false;
    }

    private PolylineOptions getPolyline(DirectionsResult result) {
        List<LatLng> decodePath = PolyUtil.decode(result.routes[0].overviewPolyline.decodePath());
        return new PolylineOptions().addAll(decodePath);
    }

    private String getEndLocationTitle(DirectionsResult result) {
        return "Time : " + result.routes[0].legs[0].duration.humanReadable +
                "Distance : " + result.routes[0].legs[0].distance.humanReadable;
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
        map = googleMap;

//        map.addMarker(new MarkerOptions().position(from).title("Student Parking"));
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(from, 20));
        map.animateCamera(CameraUpdateFactory.zoomTo(17), 2000, null);

        if (isFirstTime) {
            Intent intent = getIntent();
            if (intent != null) {
                if (intent.getExtras() != null) {
                    to = intent.getParcelableExtra("to");
                    getDirections();
                }
            }
        }
    }
}
