package com.example.dailyreader.fragment;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.util.Pair;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;

import com.anychart.APIlib;
import com.example.dailyreader.R;
import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.charts.Pie;
import com.anychart.enums.Align;
import com.anychart.enums.LegendLayout;
import com.example.dailyreader.databinding.AddFragmentBinding;
import com.example.dailyreader.entity.ReadTime;
import com.example.dailyreader.repository.ReadTimeRepository;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.datepicker.CalendarConstraints;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.TimeZone;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class AddFragment extends Fragment {


    private AddFragmentBinding addBinding;
    public AddFragment(){}
    private Pie pie;
    private AnyChartView chartView;

    // date picker
    DatePickerDialog picker;
    EditText eText;
    Button btnGet;
    TextView tvw;

    // date ranger picker
    private Button mPickDateButton;
    private TextView mShowSelectedDateText;

    private TextView mDatePicker;

    //date for chart
    private String dateStart;

    DatePicker datePicker;
    int year; //
    int month; //
    int day; //

    String startDob = null;
    String endDob = null;
    //String arrTwoDateString[] = {"",""};
    int readTime = 0;
    private DatabaseReference mDatabase;


    ReadTime rt = null;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the View for this fragment
        addBinding = AddFragmentBinding.inflate(inflater, container, false);
        View view = addBinding.getRoot();



        receiveStringDobAndGeneratePieChart(view);


        //readTimeRepository = new ReadTimeRepository(getActivity().getApplication());

        return view;
    }

    public int getReadTimeFromRoom(String sDob){


        ReadTimeRepository readTimeRepository = new ReadTimeRepository(getActivity().getApplication());
        //Log.d("sDob", sDob);
        CompletableFuture<ReadTime> readTimeCompletableFuture = readTimeRepository.findByDateFuture(sDob);
        try {
            rt = readTimeCompletableFuture.get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        if (rt != null) {
            int time = rt.getReadTime();
            Log.d("ReadTime", String.valueOf(time));
            return time;

        }else{
            int time = 0;
            Log.d("timeNull", String.valueOf(time));
            return time;
        }

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
            //Toast.makeText(getContext(), "Please set the start date first!", Toast.LENGTH_LONG).show();
            return null;
        }
    }

    public String changeDateToString(Date date){

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(date);

    }

    public long calculateTwoDays(String sDob, String eDOb){

        Date sDate = changeStringToDate(sDob);
        Date eDate = changeStringToDate(eDOb);

        long diff = eDate.getTime() - sDate.getTime();
        long seconds = diff / 1000;
        long minutes = seconds / 60;
        long hours = minutes / 60;
        long days = (hours / 24) + 1;
        Log.d("days", "" + days);
        return days;

    }
    public void receiveStringDobAndGeneratePieChart(View view){

        //get the dob from another fragment
        getParentFragmentManager().setFragmentResultListener("requestKey2", this, new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String key, @NonNull Bundle bundle) {

                //get date string from submission
                try {

                    startDob = bundle.getStringArrayList("bundleKey2").get(0);
                    endDob = bundle.getStringArrayList("bundleKey2").get(1);

                }catch (NullPointerException e){
                    e.printStackTrace();
                }

                Log.d("startDob111", startDob);
                Log.d("endDob111", endDob);

                long diffDay = calculateTwoDays(startDob,endDob);

                List<DataEntry> dataStr = new ArrayList<>();

                for (int i = 0;i < diffDay; i++){

                    dataStr.add(new ValueDataEntry(startDob,getReadTimeFromRoom(startDob)));
                    startDob = add1daysToDate(startDob);

                }

                generatePieChart(view, dataStr);

                //getReadTimeFromRoom(startDob);

                //getDataFromDatabaseAndGeneratePieChart(view, endDob);



            }
        });
    }

    public String add1daysToDate(String str){

        Date newDate = changeStringToDate(str);
        newDate = new Date(newDate.getTime() + 86400000L);

        String newStr = changeDateToString(newDate);



        /*Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_YEAR, +6);*/
        return newStr;
    }

    public int getDataFromDatabaseAndGeneratePieChart(View view,String dob){

        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        String userId = Objects.requireNonNull(firebaseAuth.getCurrentUser()).getUid();
        String date = dob; //


        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("User").child(userId).child("ReadTimeRecords").child(date).child("readTime");

        myRef.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("firebase", "Error getting data", task.getException());

                }
                else {

                    Log.d("firebase", String.valueOf(task.getResult().getValue()));
                    readTime = Integer.valueOf(String.valueOf(task.getResult().getValue()));
                    Log.d("readTimeIngetDataFromDatabase", String.valueOf(readTime));

                    //generatePieChart(view,startDob,readTime);


                }
            }

        });
        return readTime;



    }

    public void generatePieChart(View view,List<DataEntry> data){

        //AnyChart implement
        //setContentView(R.layout.activity_chart_common);
        View chartView = view.findViewById(R.id.any_chart_view);

        //generate progressBar
        APIlib.getInstance().setActiveAnyChartView((AnyChartView) chartView);
        ((AnyChartView) chartView).setProgressBar(view.findViewById(R.id.progress_bar));

        Pie mPie = AnyChart.pie();

        //get the data from the list


        mPie.data(data);

        mPie.title("Your daily reading time");

        mPie.labels().position("outside");

        mPie.legend().title().enabled(true);
        mPie.legend().title()
                .text("Minute")
                .padding(0d, 0d, 10d, 0d);

        mPie.legend()
                .position("center-bottom")
                .itemsLayout(LegendLayout.HORIZONTAL)
                .align(Align.CENTER);

        APIlib.getInstance().setActiveAnyChartView((AnyChartView) chartView);
        ((AnyChartView) chartView).setChart(mPie);

        pie = mPie;

    }

    public List<DataEntry> createDataWithDob(String dob, int readTime)
    {
        String[] number = dob.split("[-]");

        String year = number[0];
        String month = number[1];
        String day = number[2];

        int numberDay = Integer.valueOf(day);

        String day1 = String.valueOf(numberDay+1);
        String day2 = String.valueOf(numberDay+2);
        String day3 = String.valueOf(numberDay+3);
        String day4 = String.valueOf(numberDay+4);

        String date1 = year + "-" + month + "-" + day1;
        String date2 = year + "-" + month + "-" + day2;
        String date3 = year + "-" + month + "-" + day3;
        String date4 = year + "-" + month + "-" + day4;


        List<DataEntry> data1 = new ArrayList<>();

        data1.add(new ValueDataEntry(dob, readTime));
        data1.add(new ValueDataEntry(date1, 22));
        data1.add(new ValueDataEntry(date2, 32));
        data1.add(new ValueDataEntry(date3, 44));
        data1.add(new ValueDataEntry(date4, 43));

        /*data1.add(new ValueDataEntry("2022-05-01", 35));
        data1.add(new ValueDataEntry("2022-05-02", 23));
        data1.add(new ValueDataEntry("2022-05-03", 11));
        data1.add(new ValueDataEntry("2022-05-04", 6));
        data1.add(new ValueDataEntry("2022-05-05", 66));
        data1.add(new ValueDataEntry("2022-05-06", 76));
*/
        return data1;

    }

    public List<DataEntry> createData()
    {
        List<DataEntry> data1 = new ArrayList<>();

        data1.add(new ValueDataEntry(startDob, 111));
        data1.add(new ValueDataEntry("2022-05-01", 35));
        data1.add(new ValueDataEntry("2022-05-02", 23));
        data1.add(new ValueDataEntry("2022-05-03", 11));
        data1.add(new ValueDataEntry("2022-05-04", 6));
        data1.add(new ValueDataEntry("2022-05-05", 66));
        data1.add(new ValueDataEntry("2022-05-06", 76));



        return data1;

    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        addBinding = null;
    } }

