package com.example.dailyreader.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.PopupWindow;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.dailyreader.R;
import com.example.dailyreader.ReadActivity;
import com.example.dailyreader.entity.Book;
import com.example.dailyreader.viewmodel.BookViewModel;

import java.util.List;

public class AllBookFragmentAdapter extends RecyclerView.Adapter<AllBookFragmentAdapter.ViewHolder> {
    private List<Book> books;
    private final BookViewModel bookViewModel;
    private Context context;
    private View view;

    @SuppressLint("NotifyDataSetChanged")
    public AllBookFragmentAdapter(BookViewModel bookViewModel, List<Book> books) {
        this.books = books;
        this.bookViewModel = bookViewModel;
    }

    //creates a new viewHolder that is constructed with a new View, inflated from a layout
    @NonNull
    @Override
    public AllBookFragmentAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.all_book_item_layout, parent, false);
        AllBookFragmentAdapter.ViewHolder viewHolder = new ViewHolder(view);
        context = parent.getContext();

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull AllBookFragmentAdapter.ViewHolder holder, int position) {

        final Book book = books.get(position);
        holder.bookName.setText(book.getBookName());
        int readGoal = book.getReadGoal();
        if (readGoal != 0) {
            String goal = "Your daily reading goal is " + readGoal + " pages";
            holder.goalView.setText(goal);
        }

        holder.itemView.setTag(position);

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(), ReadActivity.class);
            intent.putExtra("bookInfo", book.getBid());
            v.getContext().startActivity(intent);
        });

        holder.itemDelete.setOnClickListener(v -> {
            bookViewModel.delete(book);
            notifyItemRemoved(holder.getLayoutPosition());
        });

        holder.setGoal.setOnClickListener(v -> showPopupWindow(book));

    }

    public void showPopupWindow(Book book) {
        View contentView = LayoutInflater.from(context).inflate(R.layout.set_goal_popup_window, null);
        PopupWindow popupWindow = new PopupWindow(contentView);
        popupWindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);

        popupWindow.setFocusable(true);
        popupWindow.update();

        NumberPicker readGoal = contentView.findViewById(R.id.read_goal);
        readGoal.setMinValue(0);
        readGoal.setMaxValue(100);
        Button ok = contentView.findViewById(R.id.ok_btn);
        ok.setOnClickListener(v -> {
            int goal = readGoal.getValue();
            if (goal != 0) {
                book.setReadGoal(goal);
                bookViewModel.update(book);
            }
        });

        popupWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        popupWindow.showAsDropDown(view.findViewById(R.id.set_goal));
    }

    @Override
    public int getItemCount() {

        return books.size();
    }

    public void setBooks(List<Book> books){

        this.books = books;
        notifyDataSetChanged();
    }

    protected static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView bookName;
        public Button setGoal;
        public ImageView itemDelete;
        public ImageView coverPage;
        public TextView goalView;

        public ViewHolder(View itemView) {
            super(itemView);
            bookName = itemView.findViewById(R.id.book_name);
            setGoal = itemView.findViewById(R.id.set_goal);
            itemDelete = itemView.findViewById(R.id.item_delete);
            coverPage = itemView.findViewById(R.id.cover_page);
            goalView = itemView.findViewById(R.id.goal_view);
        }

    }
}







