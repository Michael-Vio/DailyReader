package com.example.dailyreader;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Vector;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MapActivity extends AppCompatActivity
        implements OnMapReadyCallback{

    private GoogleMap mMap;
    MarkerOptions marker;
    LatLng currentLocation;
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
        Intent intent = getIntent();
        double[] address = intent.getDoubleArrayExtra("address");
        currentLocation = new LatLng(address[0], address[1]);
        markerOptions = new Vector<>();
        markerOptions.add(new MarkerOptions().title("Your location").position(currentLocation));


    }

    // Get a handle to the GoogleMap object and display marker.
    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;
        // Add a marker in Monash Clayton, Australia,
        // and move the map's camera to the same location.

        for (MarkerOptions mark : markerOptions){
            mMap.addMarker(mark);
        }

        OkHttpClient client = new OkHttpClient().newBuilder().build();
        StringBuilder stringBuilder = new StringBuilder("https://maps.googleapis.com/maps/api/place/nearbysearch/json?");
        stringBuilder.append("&location=" + currentLocation.latitude + "%2C" + currentLocation.longitude);
        stringBuilder.append("&radius=5000");
        stringBuilder.append("&type=library");
        stringBuilder.append("&key=" + getResources().getString(R.string.map_api_key));

        Request request = new Request.Builder()
                .url(stringBuilder.toString())
                .method("GET", null)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String theResponse = response.body().string();

                    MapActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Log.d("ms", theResponse);
                            try {
                                JSONObject jsonObject = null;
                                jsonObject = new JSONObject(theResponse);

                                JSONArray jsonArray = jsonObject.getJSONArray("results");

                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                                    JSONObject getLocation = jsonObject1.getJSONObject("geometry").getJSONObject("location");

                                    String latitude = getLocation.getString("lat");
                                    String longitude = getLocation.getString("lng");

                                    JSONObject getName = jsonArray.getJSONObject(i);
                                    String name = getName.getString("name");

                                    LatLng latLng = new LatLng(Double.parseDouble(latitude), Double.parseDouble(longitude));
                                    mMap.addMarker(new MarkerOptions().title(name).position(latLng));
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    });

                }
            }
        });

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLocation, 12));
    }


}