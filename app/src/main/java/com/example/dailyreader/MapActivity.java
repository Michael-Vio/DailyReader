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

import java.util.Vector;

public class MapActivity extends AppCompatActivity
        implements OnMapReadyCallback{

    private GoogleMap mMap;
    MarkerOptions marker;
    LatLng centerLoaction;
    Vector<MarkerOptions> markerOptions;

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

        centerLoaction = new LatLng(-37.914077628933796, 145.13362531362912);

//        marker = new MarkerOptions().title("Monash Clayton Library!")
//                .position(new LatLng(-37.92774565018945, 145.11770682897463))
//                .snippet("Open duering: 10:00 - 20:30"); -37.914314629905554, 145.13507370653124

        markerOptions = new Vector<>();

        markerOptions.add(new MarkerOptions().title("Monash Clayton Library!")
                .position(new LatLng(-37.92774565018945, 145.11770682897463))
                .snippet("Open duering: 10:00 - 20:30"));

        markerOptions.add(new MarkerOptions().title("Sir Louis Matheson Library!")
                .position(new LatLng(-37.912858612729295, 145.1342749559564))
                .snippet("Open duering: 10:00 - 20:30"));

        markerOptions.add(new MarkerOptions().title("Law Library!")
                .position(new LatLng(-37.914314629905554, 145.13507370653124))
                .snippet("Open duering: 10:00 - 20:30"));

    }

    // Get a handle to the GoogleMap object and display marker.
    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;
        // Add a marker in Monash Clayton, Australia,
        // and move the map's camera to the same location.
//        LatLng monash = new LatLng(-37.907803, 145.133957);
//        googleMap.addMarker(new MarkerOptions()
//                .position(monash)
//                .title("Monash Clayton"));

        for (MarkerOptions mark : markerOptions){
            mMap.addMarker(mark);
        }

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(centerLoaction, 12));
    }


}