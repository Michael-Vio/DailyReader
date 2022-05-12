package com.example.dailyreader.fragment;

import android.content.Intent;
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

        openMap.setOnClickListener(v -> {
            if (!location.getText().toString().isEmpty()){
                if (location.getText().toString().contentEquals("monash library")) {
                    Intent intent = new Intent();
                    intent.setClass(getActivity(), MapActivity.class);
                    startActivity(intent);
                }
                else{
                    Toast.makeText(getActivity(), "Invalid location.",
                            Toast.LENGTH_SHORT).show();
                }
            }else {
                Toast.makeText(getActivity(), "please input location.",
                        Toast.LENGTH_SHORT).show();
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