package com.example.dailyreader.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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
    private BookViewModel bookViewModel;
    private Context context;
    private View view;

    @SuppressLint("NotifyDataSetChanged")
    public AllBookFragmentAdapter(BookViewModel bookViewModel, List<Book> books) {
        this.books = books;
        this.bookViewModel = bookViewModel;
    }

    //creates a new viewholder that is constructed with a new View, inflated from a layout
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


        holder.coverPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), ReadActivity.class);
                intent.putExtra("bookInfo", book.getUid());
                v.getContext().startActivity(intent);
            }
        });

        holder.itemDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bookViewModel.delete(book);
                notifyDataSetChanged();
            }
        });

        holder.setGoal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopupWindow(holder, book);
//                Toast.makeText(v.getContext(),  readingGoal, Toast.LENGTH_SHORT);
            }
        });

    }

    // this method binds the view holder created with data that will be displayed

    public void showPopupWindow(ViewHolder holder, Book book) {
        View contentView = LayoutInflater.from(context).inflate(R.layout.set_goal_popup_window, null);
        PopupWindow popupWindow = new PopupWindow(contentView);
        popupWindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);

        popupWindow.setFocusable(true);
        popupWindow.update();

        EditText goal = contentView.findViewById(R.id.goal);
        Button ok = contentView.findViewById(R.id.ok_btn);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String input = goal.getText().toString();
                if (!input.isEmpty()) {
                    int goal = Integer.parseInt(input);
                    if (goal > 0 && goal <= 100) {
                        book.setReadGoal(goal);
                        bookViewModel.update(book);
                    }
                }
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







