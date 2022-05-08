package com.example.dailyreader.fragment;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;
import com.example.dailyreader.MainActivity;
import com.example.dailyreader.databinding.LogoutFragmentBinding;
import com.example.dailyreader.repository.ReadTimeRepository;
import com.google.firebase.auth.FirebaseAuth;


public class LogoutFragment extends Fragment {
    private LogoutFragmentBinding logoutBinding;


    public LogoutFragment () {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        logoutBinding = LogoutFragmentBinding.inflate(inflater, container, false);
        View view = logoutBinding.getRoot();
        logoutBinding.backButton.setOnClickListener(v ->{
            FirebaseAuth.getInstance().signOut();
            ReadTimeRepository readTimeRepository = new ReadTimeRepository(getActivity().getApplication());
            readTimeRepository.deleteAll();
            Intent i = new Intent(getActivity(), MainActivity.class);
            startActivity(i);(
                    (Activity) getActivity()).overridePendingTransition(0,0);
        });
        return view;
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        logoutBinding = null;
    }
}