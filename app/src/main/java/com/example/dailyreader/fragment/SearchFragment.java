package com.example.dailyreader.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.dailyreader.R;
import com.example.dailyreader.ReadActivity;
import com.example.dailyreader.databinding.SearchFragmentBinding;
import com.example.dailyreader.entity.Book;
import com.example.dailyreader.viewmodel.BookViewModel;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;


public class SearchFragment extends Fragment {
    private SearchFragmentBinding binding;

    private BookViewModel bookViewModel;


    public SearchFragment () {}

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = SearchFragmentBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        bookViewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().getApplication()).create(BookViewModel.class);
        binding.searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {
                CompletableFuture<Book> result = bookViewModel.findByNameFuture(query);
                Book book = null;
                try {
                    book = result.get();
                } catch (ExecutionException | InterruptedException e) {
                    e.printStackTrace();
                }

                if (book != null) {
                    binding.bookName.setText(book.getBookName());
                    binding.coverPage.setImageResource(R.drawable.read_book_image);
                    Book finalBook = book;
                    binding.cardView.setOnClickListener(v -> {
                        Intent intent = new Intent(v.getContext(), ReadActivity.class);
                        intent.putExtra("bookInfo", finalBook.getBid());
                        v.getContext().startActivity(intent);
                    });
                } else {
                    Toast.makeText(getContext(), "No such book exists", Toast.LENGTH_SHORT).show();
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}
