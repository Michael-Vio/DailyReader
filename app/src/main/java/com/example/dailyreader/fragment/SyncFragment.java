package com.example.dailyreader.fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.dailyreader.databinding.SyncFragmentBinding;


public class SyncFragment extends Fragment {
    private SyncFragmentBinding syncBinding;


    public SyncFragment () {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        syncBinding = SyncFragmentBinding.inflate(inflater, container, false);
        View view = syncBinding.getRoot();
        return view;
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        syncBinding = null;
    }
}
