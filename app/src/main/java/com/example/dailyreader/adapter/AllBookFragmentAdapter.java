package com.example.dailyreader.adapter;


import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dailyreader.ReadActivity;
import com.example.dailyreader.databinding.ItemLayoutBinding;
import com.example.dailyreader.module.Book;


import java.util.List;

public class AllBookFragmentAdapter extends RecyclerView.Adapter<AllBookFragmentAdapter.ViewHolder> {
    private static List<Book> books;
    public AllBookFragmentAdapter(List<Book> courseResults) {
        this.books = courseResults;
    }
    //creates a new viewholder that is constructed with a new View, inflated from a layout
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent,
                                         int viewType) {
        ItemLayoutBinding binding=
                ItemLayoutBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }
    // this method binds the view holder created with data that will be displayed
    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        final Book aBook = books.get(position);
        viewHolder.binding.bookName.setText(aBook.getBookName());
        viewHolder.binding.bookAuthor.setText(aBook.getBookAuthor());
        viewHolder.binding.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), ReadActivity.class);
                v.getContext().startActivity(intent);
            }
        });
    }




    @Override
    public int getItemCount() {
        return books.size();
    }
    public void addUnits(List<Book> results) {
        books = results;
        notifyDataSetChanged();
    }
    public static class ViewHolder extends RecyclerView.ViewHolder {
        private ItemLayoutBinding binding;
        public ViewHolder(ItemLayoutBinding binding){
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}

