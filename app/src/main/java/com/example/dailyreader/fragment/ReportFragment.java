package com.example.dailyreader.fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.anychart.charts.Cartesian;
import com.example.dailyreader.R;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.charts.Pie;
import com.example.dailyreader.databinding.ReportFragmentBinding;
import com.example.dailyreader.repository.ReadTimeRepository;

import java.util.ArrayList;
import java.util.List;


public class ReportFragment extends Fragment {

    private ReportFragmentBinding reportBinding;




    private Cartesian cartesian;
    private Pie pie;
    private AnyChartView chartView;
    private boolean cartesianSet;

    // date picker submit button
    Button submit;
    String startDob;



    public ReportFragment () {}

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        reportBinding = ReportFragmentBinding.inflate(inflater, container, false);
        View view = reportBinding.getRoot();

        //pie chart
        Button addButton = view.findViewById(R.id.addButton);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceFragment(new AddFragment());

            }
        });

        //col chart
        Button viewButton = view.findViewById(R.id.viewButton);
        viewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceFragment(new ViewFragment());
            }
        });

        //date picker
        Button setStartDate = view.findViewById(R.id.setStartDate);
        setStartDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceFragment(new DatePickerFragment());
            }
        });

        //Report screen
        /*readTimeViewModel = new ViewModelProvider(this).get(ReadTimeViewModel.class);
        LiveData<List<ReadTime>> readTimeList = readTimeViewModel.getAllReadTimes();*/

        //generatePieChart(view);
        //generateColChart(view);




        //Button button = view.findViewById(R.id.changeChart);
        //button.setOnClickListener(new View.OnClickListener() {

           // @Override
            //public void onClick(View v) {
                /*// Perform action on click


                //FragmentManager fragmentManager = getFragmentManager();

                //fragmentManager.beginTransaction().replace(R.id.nav_report_fragment1, fragmentManager).commit();*/

           // }
       // });




        /*Switch mSwitch = (Switch) view.findViewById(R.id.switch1);

        mSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    // The toggle is enabled

                    generateColChart(view);

                } else {
                    // The toggle is disabled
                    generatePieChart(view);


                }
            }
        });*/

        /*f(mSwitch.isChecked()){
            anyChartView.setChart(pie);;
        }
        else{
            anyChartView.setChart(cartesian);
        }*/

        //Generate pie chart
        //anyChartView.setChart(pie);

        //Generate column chart
        //anyChartView.setChart(cartesian);

        /*APIlib.getInstance().setActiveAnyChartView(anyChartView);
        pie.title("First chart");
        APIlib.getInstance().setActiveAnyChartView(anyChartView1);
        cartesian.title("Second chart");*/




        return view;
    }

    private void replaceFragment(Fragment nextFragment) {
        FragmentManager fragmentManager = getChildFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container_view, nextFragment);
        fragmentTransaction.commit();
    }

    /*public void generatePieChart(View view){

        //AnyChart implement
        //setContentView(R.layout.activity_chart_common);
        chartView = view.findViewById(R.id.any_chart_view);
        chartView.setProgressBar(view.findViewById(R.id.progress_bar));

        Pie mPie = AnyChart.pie();

        pie.setOnClickListener(new ListenersInterface.OnClickListener(new String[]{"x", "value"}) {
            @Override
            public void onClick(Event event) {
                Toast.makeText(ReportFragment.this, event.getData().get("x") + ":" + event.getData().get("value"), Toast.LENGTH_SHORT).show;
            }
        });

        //get the data from the list

        mPie.data(createData());

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

        APIlib.getInstance().setActiveAnyChartView(chartView);
        chartView.setChart(mPie);

        pie = mPie;

    }

    public void generateColChart(View view){

        //create column chart
        chartView = view.findViewById(R.id.any_chart_view);

        Cartesian mCartesian = AnyChart.column();

        Column column = cartesian.column(createData());

        column.tooltip()
                .titleFormat("{%X}")
                .position(Position.CENTER_BOTTOM)
                .anchor(Anchor.CENTER_BOTTOM)
                .offsetX(0d)
                .offsetY(5d)
                .format("{%Value}{groupsSeparator: }");

        mCartesian.animation(true);
        mCartesian.title("Your daily reading time");

        mCartesian.yScale().minimum(0d);

        mCartesian.yAxis(0).labels().format("{%Value}{groupsSeparator: }");

        mCartesian.tooltip().positionMode(TooltipPositionMode.POINT);
        mCartesian.interactivity().hoverMode(HoverMode.BY_X);

        mCartesian.xAxis(0).title("Date");
        mCartesian.yAxis(0).title("Time (Minute)");


        chartView.setChart(cartesian);
        cartesian = mCartesian;

    }

    public void changeChart(View view) {
        cartesianSet = !cartesianSet;
        if (cartesianSet) {
            generateColChart(view);
            chartView.setChart(cartesian);
        } else {
            generatePieChart(view);
            chartView.setChart(pie);
        }
    }*/

    public List<DataEntry> createData()
    {
        List<DataEntry> data1 = new ArrayList<>();
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
        reportBinding = null;
    }
}
