package com.example.dailyreader.fragment;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import com.example.dailyreader.MapActivity;
import com.example.dailyreader.databinding.FragmentMapBinding;
import java.io.IOException;
import java.util.List;

public class MapFragment extends Fragment {

    private FragmentMapBinding mapBinding;

    public MapFragment () {}
    EditText location;
    Button openMap;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mapBinding = FragmentMapBinding.inflate(inflater, container, false);
        View view = mapBinding.getRoot();

        location = mapBinding.outputLocation;
        openMap = mapBinding.mapBtn;
        // check whether the location is valid, if valid show it on the map
        openMap.setOnClickListener(v -> {
            if (!location.getText().toString().isEmpty()){
                Intent intent = new Intent();
                Geocoder geocoder = new Geocoder(getContext());
                List<Address> addresses;
                double latitude;
                double longitude;
                try {
                    addresses = geocoder.getFromLocationName(location.getText().toString(), 1);
                    if(addresses.size() > 0) {
                        latitude = addresses.get(0).getLatitude();
                        longitude = addresses.get(0).getLongitude();
                        double[] address = {latitude, longitude};
                        intent.putExtra("address", address);
                        intent.setClass(getActivity(), MapActivity.class);
                        startActivity(intent);
                    }
                } catch (IOException e) {
                    Toast.makeText(getContext(), "Address not exists", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return view;
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mapBinding = null;
    }

}