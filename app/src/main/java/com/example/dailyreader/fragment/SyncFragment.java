package com.example.dailyreader.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import com.example.dailyreader.databinding.SyncFragmentBinding;
import com.example.dailyreader.entity.Book;
import com.example.dailyreader.viewmodel.BookViewModel;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;


public class SyncFragment extends Fragment {
    private SyncFragmentBinding syncBinding;

    public SyncFragment () {}

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        syncBinding = SyncFragmentBinding.inflate(inflater, container, false);
        View view = syncBinding.getRoot();
        // load all book names with read goal from database
        BookViewModel bookViewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().getApplication()).create(BookViewModel.class);
        List<Book> books = new ArrayList<>();
        CompletableFuture<List<Book>> bookList = bookViewModel.getBookList();
        try {
            books = bookList.get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        StringBuilder readGoals = new StringBuilder();
        for (Book book : books) {
            if (book.getReadGoal() != 0) {
                readGoals.append(book.getBookName()).append(": ").append(book.getReadGoal()).append(" Pages\n");
            }
        }
        // set the reading goals to event description if they exist
        if (!readGoals.toString().isEmpty()) {
            syncBinding.eventDescriptionContent.setText(readGoals.toString());
        }


        syncBinding.synBtn.setOnClickListener(v -> {
//                Add some checking Inside the addEvent button's onClickListener to ensure that the title,
//            location, and description edittexts are not empty. Otherwise, show an error Toast message.
            if (!syncBinding.eventDescriptionContent.getText().equals("None"))  {
//                    Initialize the Android calendar Intent.
                Intent intent = new Intent(Intent.ACTION_INSERT);
                intent.setData(CalendarContract.Events.CONTENT_URI);

//                    Pass the title, location, and description values into the intent using PutExtra.
                intent.putExtra(CalendarContract.Events.TITLE, syncBinding.eventTitleContent.getText());
                intent.putExtra(CalendarContract.Events.EVENT_LOCATION, syncBinding.eventLocationContent.getText());
                intent.putExtra(CalendarContract.Events.DESCRIPTION, syncBinding.eventDescriptionContent.getText());

//                    Set the event type so that it will be able available all day without start and end time.
                intent.putExtra(CalendarContract.Events.ALL_DAY, true);

//                    Include some emails of guests that you want them to be part of this event.
//                    intent.putExtra(Intent.EXTRA_EMAIL, "test@yahoo.com, test2@yahoo.com, test3@yahoo.com");

//                    do checking to ensure that there is an application that can handle this type of action.
//                            Otherwise, show an error Toast Message.
                if (intent.resolveActivity(requireActivity().getPackageManager()) != null) {
                    startActivity(intent);
                }else {
                    Toast.makeText(getActivity(), "Please install the Google Calendar first.", Toast.LENGTH_SHORT).show();
                }

            }else{
                Toast.makeText(getActivity(), "No reading goals to synchronize.",
                        Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        syncBinding = null;
    }
}
