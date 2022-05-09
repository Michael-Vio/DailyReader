package com.example.dailyreader.fragment;

import static android.content.ContentValues.TAG;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;

import com.example.dailyreader.R;
import com.example.dailyreader.databinding.DatePickerEndFragmentBinding;
import com.example.dailyreader.databinding.DatePickerFragmentBinding;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class DatePickerEndFragment extends Fragment {

    private DatePickerEndFragmentBinding dateBinding;
    public DatePickerEndFragment(){}

    // for end date
    public int startDay;
    public int startMonth;
    public int startYear;

    public String startDob;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the View for this fragment
        dateBinding = DatePickerEndFragmentBinding.inflate(inflater, container, false);
        View view = dateBinding.getRoot();

        //set start date
        //set start date
        //set start date

        DatePicker simpleDatePicker = (DatePicker)view.findViewById(R.id.simpleDateEndPicker); // initiate a date picker

        // set the min date and max date

        receiveStringDobAndSetMinAndMaxDate(view, simpleDatePicker);

        Button submitEndButton = view.findViewById(R.id.submitEndButton);
        submitEndButton.setOnClickListener(new View.OnClickListener() {
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


                sendStringEndDob(dobAfterChange);
            }
        });


        return view;
    }

    public void sendStringEndDob(String dob){

        // send dob to another fragment
        Bundle result = new Bundle();
        result.putString("bundleKey1", dob);
        getParentFragmentManager().setFragmentResult("requestKey1", result);

    }

    public String receiveStringDobAndSetMinAndMaxDate(View view, DatePicker simpleDatePicker){

        String dob;
        //get the dob from another fragment
        getParentFragmentManager().setFragmentResultListener("requestKey", this, new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String key, @NonNull Bundle bundle) {
                // We use a String here, but any type that can be put in a Bundle is supported

                startDob = bundle.getString("bundleKey");
                Log.d("startDob1", String.valueOf(startDob));

                Date startDob1 = changeStringToDate(startDob);
                startDob1 = add1daysToDate(startDob1);
                Log.d("startDob1", String.valueOf(startDob1));

                Date endDate1 = add7daysToDate(startDob1);
                Log.d("endDate1", String.valueOf(endDate1));
                setMinAndMaxDate(simpleDatePicker,startDob1,endDate1 );

                //setMinAndMaxDate(simpleDatePicker)


            }
        });
        return startDob;
    }

    public Date changeStringToDate(String str){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try{
            Date date = sdf.parse(str);
            Log.d("date", String.valueOf(date));
            return date;

        }catch (ParseException e){
            e.printStackTrace();
            System.out.println();
            Toast.makeText(getContext(), "Please set the start date first!", Toast.LENGTH_LONG).show();
            return null;
        }
    }

    public Date add7daysToDate(Date date){

        Date newDate = new Date(date.getTime() + 432000000L);

        /*Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_YEAR, +6);*/
        return newDate;
    }

    public Date add1daysToDate(Date date){

        Date newDate = new Date(date.getTime() + 86400000L);

        /*Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_YEAR, +6);*/
        return newDate;
    }


    public void setMinAndMaxDate(DatePicker datePicker, Date startDob, Date endDob){

        // set the min date and max date
        Calendar mCalendar = Calendar.getInstance();
        mCalendar.setTime(startDob);
        datePicker.setMinDate(mCalendar.getTimeInMillis());
        mCalendar.setTime(endDob);
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
