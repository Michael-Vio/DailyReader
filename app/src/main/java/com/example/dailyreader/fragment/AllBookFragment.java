package com.example.dailyreader.fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.dailyreader.databinding.AllBookFragmentBinding;


public class AllBookFragment extends Fragment {
    private AllBookFragmentBinding allBookBinding;

    public AllBookFragment () {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        allBookBinding = AllBookFragmentBinding.inflate(inflater, container, false);
        View view = allBookBinding.getRoot();
        return view;
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        allBookBinding = null;
    }
}

