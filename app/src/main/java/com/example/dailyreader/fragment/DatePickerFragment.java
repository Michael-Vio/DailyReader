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
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.dailyreader.R;
import com.example.dailyreader.databinding.DatePickerFragmentBinding;

import java.util.Calendar;


public class DatePickerFragment extends Fragment {

    private DatePickerFragmentBinding dateBinding;
    public DatePickerFragment(){}

    // for end date
    public int startDay;
    public int startMonth;
    public int startYear;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the View for this fragment
        dateBinding = DatePickerFragmentBinding.inflate(inflater, container, false);
        View view = dateBinding.getRoot();

        //set start date
        //set start date
        //set start date

        DatePicker simpleDatePicker = (DatePicker)view.findViewById(R.id.simpleDatePicker); // initiate a date picker

        // set the min date and max date
        //setMinAndMaxDate(simpleDatePicker,2022,4,1);

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

                startDay = day1;
                startMonth = month1;
                startYear = year1;

                String dob =year1 + "-" + month1 + "-" +day1;
                // display the values by using a toast

                //changeDobFormat
                String dobAfterChange = changeDobFormat(day1,month1,year1);

                Toast.makeText(getContext(), dobAfterChange, Toast.LENGTH_LONG).show();

                sendStringDob(dobAfterChange);


            }
        });

        /*//set end date
        //set end date
        //set end date
        DatePicker simpleDatePickerForEnd = (DatePicker)view.findViewById(R.id.simpleDatePickerForEnd); // initiate a date picker

        // set the min date and max date
        Calendar mCalendar = Calendar.getInstance();
        int endDay = startDay + 5;
        mCalendar.set(startYear, startMonth-1, startDay);
        simpleDatePickerForEnd.setMinDate(mCalendar.getTimeInMillis());
        mCalendar.set(startYear, startMonth-1, endDay);
        simpleDatePickerForEnd.setMaxDate(mCalendar.getTimeInMillis());

        Button submitButtonForEnd = view.findViewById(R.id.submitButtonForEnd);
        submitButtonForEnd.setOnClickListener(new View.OnClickListener() {
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
        });*/

        return view;
    }

    private void replaceFragment(Fragment nextFragment) {
        FragmentManager fragmentManager = getChildFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.date_picker_fragment, nextFragment);
        fragmentTransaction.commit();
    }

    public void setMinAndMaxDate(DatePicker datePicker,int year, int month, int day){

        // set the min date and max date
        Calendar mCalendar = Calendar.getInstance();
        mCalendar.set(year, month-1, day);
        datePicker.setMinDate(mCalendar.getTimeInMillis());
        mCalendar.set(2022, 4, 31);
        datePicker.setMaxDate(mCalendar.getTimeInMillis());

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

        if (day< 10){
            dayR = "0" +day;
        }
        else{
            dayR = String.valueOf(day);
        }

        if (month< 10){
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
