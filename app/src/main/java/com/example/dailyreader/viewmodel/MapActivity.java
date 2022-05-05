package com.example.dailyreader.viewmodel;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.dailyreader.R;
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

        CameraOptions cameraPosition = new CameraOptions.Builder()
                .zoom(13.0)
                .center(point)
                .build();

        mapView.getMapboxMap().loadStyleUri(Style.MAPBOX_STREETS);
        mapView.getMapboxMap().setCamera(cameraPosition); }

}