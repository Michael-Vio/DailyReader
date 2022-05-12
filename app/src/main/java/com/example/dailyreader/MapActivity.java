package com.example.dailyreader;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapActivity extends AppCompatActivity implements OnMapReadyCallback{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Set the layout file as the content view.
        setContentView(R.layout.map_fragment);

        // Get a handle to the fragment and register the callback.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.maps);
        //lat and long are hardcoded here to Monash Caulfield but could be provided at run time
//        final Point point = Point.fromLngLat(145.045837, -37.876823 );

        mapFragment.getMapAsync(this);


//        mapView.addMarker(new MarkerOptions()
//                .position(new LatLng(48.85819, 2.29458))
//                .title("Eiffel Tower"));

//        mapView?.getMapboxMap()?.loadStyleUri(
//                Style.MAPBOX_STREETS,
//                object : Style.OnStyleLoaded {
//            override fun onStyleLoaded(style: Style) {
//                addAnnotationToMap()
//            }
//        }
//)
//
//        CameraOptions cameraPosition = new CameraOptions.Builder()
//                .zoom(13.0)
//                .center(point)
//                .build();
//
//        mapView.getMapboxMap().loadStyleUri(Style.MAPBOX_STREETS);
//        mapView.getMapboxMap().setCamera(cameraPosition); }

    }

    // Get a handle to the GoogleMap object and display marker.
    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        // Add a marker in Monash Clayton, Australia,
        // and move the map's camera to the same location.
        LatLng monash = new LatLng(-37.907803, 145.133957);
        googleMap.addMarker(new MarkerOptions()
                .position(monash)
                .title("Monash Clayton"));
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(monash));
    }



}