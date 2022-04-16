package com.example.dailyreader.fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.dailyreader.databinding.WeatherFragmentBinding;


public class WeatherFragment extends Fragment {
    private WeatherFragmentBinding weatherBinding;


    public WeatherFragment () {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        weatherBinding = WeatherFragmentBinding.inflate(inflater, container, false);
        View view = weatherBinding.getRoot();
        return view;
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        weatherBinding = null;
    }
}