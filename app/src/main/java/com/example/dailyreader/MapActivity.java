package com.example.dailyreader;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.mapbox.geojson.Point;
import com.mapbox.maps.CameraOptions;
import com.mapbox.maps.MapView;
import com.mapbox.maps.Style;

public class MapActivity extends AppCompatActivity {
    private MapView mapView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map_fragment);
        //lat and long are hardcoded here to Monash Caulfield but could be provided at run time
        final Point point = Point.fromLngLat(145.045837, -37.876823 );

        mapView = findViewById(R.id.mapView);

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

        CameraOptions cameraPosition = new CameraOptions.Builder()
                .zoom(13.0)
                .center(point)
                .build();

        mapView.getMapboxMap().loadStyleUri(Style.MAPBOX_STREETS);
        mapView.getMapboxMap().setCamera(cameraPosition); }

}