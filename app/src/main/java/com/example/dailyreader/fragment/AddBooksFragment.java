package com.example.dailyreader.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.dailyreader.databinding.AddBooksFragmentBinding;


public class AddBooksFragment extends Fragment {
    private AddBooksFragmentBinding addBooksBinding;

    public AddBooksFragment () {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        addBooksBinding = AddBooksFragmentBinding.inflate(inflater, container, false);
        View view = addBooksBinding.getRoot();
        return view;
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        addBooksBinding = null;
    }
}