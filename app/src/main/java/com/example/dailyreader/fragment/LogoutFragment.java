package com.example.dailyreader.fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.dailyreader.databinding.LogoutFragmentBinding;


public class LogoutFragment extends Fragment {
    private LogoutFragmentBinding logoutBinding;


    public LogoutFragment () {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        logoutBinding = LogoutFragmentBinding.inflate(inflater, container, false);
        View view = logoutBinding.getRoot();
        return view;
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        logoutBinding = null;
    }
}