package com.example.dailyreader.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.dailyreader.R;
import com.example.dailyreader.databinding.SyncFragmentBinding;


public class SyncFragment extends Fragment {
    private SyncFragmentBinding syncBinding;
    EditText title;
    EditText location;
    EditText description;
    Button addEvent;

    public SyncFragment () {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        syncBinding = SyncFragmentBinding.inflate(inflater, container, false);
        View view = syncBinding.getRoot();

        title = view.findViewById(R.id.etTitle);
        location = view.findViewById(R.id.etLocaton);
        description = view.findViewById(R.id.etDescription);
        addEvent = view.findViewById(R.id.btnAdd);

        syncBinding.btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Add some checking Inside the addEvent button's onClickListener to ensure that the title,
//            location, and description edittexts are not empty. Otherwise, show an error Toast message.

                if (!title.getText().toString().isEmpty() && !location.getText().toString().isEmpty() && !description
                        .getText().toString().isEmpty()) {

//                    Initialize the Android calendar Intent.
                    Intent intent = new Intent(Intent.ACTION_INSERT);
                    intent.setData(CalendarContract.Events.CONTENT_URI);

//                    Pass the title, location, and description values inside the intent using PutExtra.
                    intent.putExtra(CalendarContract.Events.TITLE, title.getText().toString());
                    intent.putExtra(CalendarContract.Events.EVENT_LOCATION, location.getText().toString());
                    intent.putExtra(CalendarContract.Events.DESCRIPTION, description.getText().toString());

//                    Set the event type so that it will be able available all day without start and end time.
                    intent.putExtra(CalendarContract.Events.ALL_DAY, true);

//                    Include some emails of guests that you want them to be part of this event.
                    intent.putExtra(Intent.EXTRA_EMAIL, "test@yahoo.com, test2@yahoo.com, test3@yahoo.com");

//                    do checking to ensure that there is an application that can handle this type of action.
//                            Otherwise, show an error Toast Message.
                    if (intent.resolveActivity(getActivity().getPackageManager()) != null) {
                        startActivity(intent);
                    }else {
                        Toast.makeText(getActivity(), "There is no app that support this action", Toast.LENGTH_SHORT).show();
                    }

                }else{
                    Toast.makeText(getActivity(), "Please fill all the fields",
                            Toast.LENGTH_SHORT).show();
                }
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
