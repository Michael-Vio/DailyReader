package com.example.dailyreader.fragment;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.InputType;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.util.Pair;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentResultListener;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;

import com.anychart.APIlib;
import com.anychart.charts.Cartesian;
import com.anychart.core.cartesian.series.Column;
import com.anychart.enums.Anchor;
import com.anychart.enums.HoverMode;
import com.anychart.enums.Position;
import com.anychart.enums.TooltipPositionMode;
import com.example.dailyreader.MainActivity;
import com.example.dailyreader.R;
import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.chart.common.listener.Event;
import com.anychart.chart.common.listener.ListenersInterface;
import com.anychart.charts.Pie;
import com.anychart.enums.Align;
import com.anychart.enums.LegendLayout;
import com.example.dailyreader.databinding.AddFragmentBinding;
import com.example.dailyreader.databinding.ReportFragmentBinding;
import com.example.dailyreader.entity.ReadTime;
import com.example.dailyreader.viewmodel.ReadTimeViewModel;
import com.google.android.material.datepicker.CalendarConstraints;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

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
    int year; //今年
    int month; //当前月份
    int day; //今天

    String startDob = null;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the View for this fragment
        addBinding = AddFragmentBinding.inflate(inflater, container, false);
        View view = addBinding.getRoot();

        if(startDob == null){
            Toast.makeText(getContext(), "Please set the start date first!", Toast.LENGTH_LONG).show();
        }
        receiveStringDobAndGeneratePieChart(view);

        return view;
    }

    public void receiveStringDobAndGeneratePieChart(View view){

        //get the dob from another fragment
        getParentFragmentManager().setFragmentResultListener("requestKey", this, new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String key, @NonNull Bundle bundle) {
                // We use a String here, but any type that can be put in a Bundle is supported
                startDob = bundle.getString("bundleKey");
                // Do something with the result...
                generatePieChart(view,startDob);
            }
        });
    }



    public void generateDateRangerPicker(View view){

        /*//获取当前的年月日
        Calendar calendar= Calendar.getInstance(); //获取日历的实例
        year=calendar.get(Calendar.YEAR);
        month=calendar.get(Calendar.MONTH);//实际月份需要加1
        day=calendar.get(Calendar.DATE);

        //监听日期选择器
        datePicker=(DatePicker)view.findViewById(R.id.datepicker);
        datePicker.init(year,month,day,new DatePicker.OnDateChangedListener(){
            @Override
            public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Log.e("datepicker—你选择的日期是：",year+"-"+(monthOfYear+1)+"-"+dayOfMonth);
            }
        });*/


        // now register the text view and the button with
        // their appropriate IDs
        //mPickDateButton = view.findViewById(R.id.pick_date_button);
        //mShowSelectedDateText = view.findViewById(R.id.show_selected_date);


        // create the instance of the calendar to set the
        // bounds
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT"));

        // now set the starting bound from current month to
        // previous MARCH
        calendar.set(Calendar.MONTH, Calendar.MAY);
        long may1 = calendar.getTimeInMillis();

        // now set the ending bound from current month to
        // DECEMBER
        calendar.set(Calendar.MONTH, Calendar.MAY);
        long may = calendar.getTimeInMillis();

        //Start here
        /*MaterialDatePicker.Builder materialDateBuilder = MaterialDatePicker.Builder.datePicker();
        // now define the properties of the
        // materialDateBuilder that is title text as SELECT A DATE
        materialDateBuilder.setTitleText("SELECT A DATE");

        CalendarConstraints.Builder calendarConstraintBuilder = new CalendarConstraints.Builder();
        calendarConstraintBuilder.setStart(may1);
        calendarConstraintBuilder.setEnd(may);
        materialDateBuilder.setCalendarConstraints(calendarConstraintBuilder.build());

        // now create the instance of the material date
        // picker
        final MaterialDatePicker materialDatePicker = materialDateBuilder.build();*/

        /*// handle select date button which opens the
        // material design date picker
        mPickDateButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // getSupportFragmentManager() to
                        // interact with the fragments
                        // associated with the material design
                        // date picker tag is to get any error
                        // in logcat
                        materialDatePicker.show(getChildFragmentManager(), "MATERIAL_DATE_PICKER");
                    }
                });

        // now handle the positive button click from the
        // material design date picker
        materialDatePicker.addOnPositiveButtonClickListener(
                new MaterialPickerOnPositiveButtonClickListener() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onPositiveButtonClick(Object selection) {

                        // if the user clicks on the positive
                        // button that is ok button update the
                        // selected date
                        mShowSelectedDateText.setText("Selected Date is : " + materialDatePicker.getHeaderText());

                        String inputFormat = "dd MMM yyyy";
                        String outputFormat = "dd/MM/yyyy";
                        String inputDate = materialDatePicker.getHeaderText(); //"13 Jun 2020";
                        dateStart = inputDate;
                        // in the above statement, getHeaderText
                        // is the selected date preview from the
                        // dialog
                    }
                });*/







        // create the instance of the CalendarConstraints
        // Builder
        CalendarConstraints.Builder calendarConstraintBuilder = new CalendarConstraints.Builder();

        // and set the start and end constraints (bounds)
        calendarConstraintBuilder.setStart(may1);
        calendarConstraintBuilder.setEnd(may);

        // instantiate the Material date picker dialog
        // builder
        MaterialDatePicker.Builder<Pair<Long, Long>> materialDateBuilder = MaterialDatePicker.Builder.dateRangePicker();
        materialDateBuilder.setTitleText("SELECT A DATE");

        // now pass the constrained calendar builder to
        // material date picker Calendar constraints
        materialDateBuilder.setCalendarConstraints(calendarConstraintBuilder.build());

        // now build the material date picker dialog
        final MaterialDatePicker materialDatePicker = materialDateBuilder.build();

        // handle the Select date button to open the
        // material date picker
        mPickDateButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // show the material date picker with
                        // supportable fragment manager to
                        // interact with dialog material date
                        // picker dialog fragments
                        materialDatePicker.show(getChildFragmentManager(), "MATERIAL_DATE_PICKER");
                    }
                });

        materialDatePicker.addOnPositiveButtonClickListener(
                new MaterialPickerOnPositiveButtonClickListener() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onPositiveButtonClick(Object selection) {
                        // now update the selected date preview
                        mShowSelectedDateText.setText("Selected Date is : " + materialDatePicker.getHeaderText());
                        dateStart = materialDatePicker.getHeaderText();
                        Log.e("datepicker—你选择的日期是：",dateStart);
                    }
                });

    }

    public void generateDatePicker(View view){







    }

    public void generatePieChart(View view,String dob){

        //AnyChart implement
        //setContentView(R.layout.activity_chart_common);
        View chartView = view.findViewById(R.id.any_chart_view);

        //generate progressBar
        APIlib.getInstance().setActiveAnyChartView((AnyChartView) chartView);
        ((AnyChartView) chartView).setProgressBar(view.findViewById(R.id.progress_bar));

        Pie mPie = AnyChart.pie();

        //get the data from the list

        mPie.data(createDataWithDob(dob));

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

    public List<DataEntry> createDataWithDob(String dob)
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

        data1.add(new ValueDataEntry(startDob, 10));
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

    /*    public void generateDatePicker(View view){

        tvw=view.findViewById(R.id.textView1);
        eText=view.findViewById(R.id.editText1);
        eText.setInputType(InputType.TYPE_NULL);
        eText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);

                // date picker dialog
                picker = new DatePickerDialog(AddFragment.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                eText.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                            }
                        }, year, month, day);
                picker.show();
            }
        });
        btnGet=view.findViewById(R.id.button1);
        btnGet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvw.setText("Selected Date: "+ eText.getText());
            }
        });
    }*/

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        addBinding = null;
    } }

