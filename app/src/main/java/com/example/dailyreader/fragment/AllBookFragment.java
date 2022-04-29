package com.example.dailyreader.fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dailyreader.adapter.AllBookFragmentAdapter;
import com.example.dailyreader.databinding.AllBookFragmentBinding;
import com.example.dailyreader.entity.Book;
import com.example.dailyreader.viewmodel.BookViewModel;

import java.util.ArrayList;
import java.util.List;


public class AllBookFragment extends Fragment {
    private AllBookFragmentBinding binding;
    private AllBookFragmentAdapter adapter;
    private BookViewModel bookViewModel;


    public AllBookFragment () {}

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = AllBookFragmentBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        binding.allBookRecyclerView.setLayoutManager(layoutManager);
        bookViewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(getActivity().getApplication()).create(BookViewModel.class);
        adapter = new AllBookFragmentAdapter(bookViewModel, new ArrayList<>());
        binding.allBookRecyclerView.setAdapter(adapter);


        bookViewModel.getAllBooks().observe(getViewLifecycleOwner(), new Observer<List<Book>>() {
            @Override
            public void onChanged(List<Book> books) {

                adapter.setBooks(books);
            }
        });

        binding.allBookRecyclerView.addItemDecoration(new DividerItemDecoration(requireContext(),LinearLayoutManager.VERTICAL));

        binding.allBookRecyclerView.setHasFixedSize(true);

        return view;
    }




    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }


}
