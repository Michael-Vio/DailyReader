package com.example.dailyreader.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dailyreader.adapter.AllBookFragmentAdapter;
import com.example.dailyreader.databinding.AllBookFragmentBinding;
import com.example.dailyreader.module.Book;

import java.util.ArrayList;
import java.util.List;


public class AllBookFragment extends Fragment {
    private AllBookFragmentBinding allBookBinding;
    private RecyclerView.LayoutManager layoutManager;
    private List<Book> books;
    private AllBookFragmentAdapter adapter;


    public AllBookFragment () {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        allBookBinding = allBookBinding.inflate(inflater, container, false);
        View view = allBookBinding.getRoot();
        books = new ArrayList<Book>();
        books = Book.createContactsList();
        adapter = new AllBookFragmentAdapter(books);
        allBookBinding.allBookRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(),LinearLayoutManager.VERTICAL));

        allBookBinding.allBookRecyclerView.setAdapter(adapter);
        layoutManager = new LinearLayoutManager(getContext());
        allBookBinding.allBookRecyclerView.setLayoutManager(layoutManager);


        return view;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        allBookBinding = null;
    }


}
