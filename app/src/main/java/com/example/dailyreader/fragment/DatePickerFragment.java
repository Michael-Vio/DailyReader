package com.example.dailyreader.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.dailyreader.R;
import com.example.dailyreader.databinding.DatePickerFragmentBinding;



public class DatePickerFragment extends Fragment {

    private DatePickerFragmentBinding dateBinding;
    public DatePickerFragment(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the View for this fragment
        dateBinding = DatePickerFragmentBinding.inflate(inflater, container, false);
        View view = dateBinding.getRoot();

        DatePicker simpleDatePicker = (DatePicker)view.findViewById(R.id.simpleDatePicker); // initiate a date picker

        Button submitButton = view.findViewById(R.id.submitButton);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // get the values for day of month , month and year from a date picker
                String day = "Day = " + simpleDatePicker.getDayOfMonth();
                String month = "Month = " + (simpleDatePicker.getMonth() + 1);
                String year = "Year = " + simpleDatePicker.getYear();

                int day1 = simpleDatePicker.getDayOfMonth();
                int month1 = (simpleDatePicker.getMonth() + 1);
                int year1 = simpleDatePicker.getYear();

                String dob =year1 + "-" + month1 + "-" +day1;
                // display the values by using a toast

                //changeDobFormat
                String dobAfterChange = changeDobFormat(day1,month1,year1);

                Toast.makeText(getContext(), dobAfterChange, Toast.LENGTH_LONG).show();

                sendStringDob(dobAfterChange);
            }
        });
        return view;
    }

    public void sendStringDob(String dob){

        // send dob to another fragment
        Bundle result = new Bundle();
        result.putString("bundleKey", dob);
        getParentFragmentManager().setFragmentResult("requestKey", result);

    }

    public String changeDobFormat(int day, int month, int year){

        String dayR;
        String monthR;
        String yearR;

        if (day<= 10){
            dayR = "0" +day;
        }
        else{
            dayR = String.valueOf(day);
        }

        if (month<= 10){
            monthR = "0" + month;
        }
        else{
            monthR = String.valueOf(month);
        }

        yearR = String.valueOf(year);

        return yearR + "-" + monthR + "-" + dayR;

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        dateBinding = null;
    }
}
