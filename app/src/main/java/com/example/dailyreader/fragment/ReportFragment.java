package com.example.dailyreader.fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;

import com.anychart.APIlib;
import com.anychart.charts.Cartesian;
import com.anychart.core.cartesian.series.Column;
import com.anychart.enums.Anchor;
import com.anychart.enums.HoverMode;
import com.anychart.enums.Position;
import com.anychart.enums.TooltipPositionMode;
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
import com.example.dailyreader.databinding.ReportFragmentBinding;
import com.example.dailyreader.entity.ReadTime;
import com.example.dailyreader.viewmodel.ReadTimeViewModel;

import java.util.ArrayList;
import java.util.List;


public class ReportFragment extends Fragment {
    private ReportFragmentBinding reportBinding;
    private ReadTimeViewModel readTimeViewModel;


    public ReportFragment () {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        reportBinding = ReportFragmentBinding.inflate(inflater, container, false);
        View view = reportBinding.getRoot();

        //Report screen
        readTimeViewModel = new ViewModelProvider(this).get(ReadTimeViewModel.class);
        LiveData<List<ReadTime>> readTimeList = readTimeViewModel.getAllReadTimes();

        //AnyChart implement
        //setContentView(R.layout.activity_chart_common);

        AnyChartView anyChartView = view.findViewById(R.id.any_chart_view);
        anyChartView.setProgressBar(view.findViewById(R.id.progress_bar));
        APIlib.getInstance().setActiveAnyChartView(anyChartView);

        Pie pie = AnyChart.pie();

        /*pie.setOnClickListener(new ListenersInterface.OnClickListener(new String[]{"x", "value"}) {
            @Override
            public void onClick(Event event) {
                Toast.makeText(ReportFragment.this, event.getData().get("x") + ":" + event.getData().get("value"), Toast.LENGTH_SHORT).show;
            }
        });*/

        //get the data from the list
        /*for (LiveData<List<ReadTime>> readTime : readTimeList) {

        }*/

        List<DataEntry> data = new ArrayList<>();
        data.add(new ValueDataEntry("2022-05-01", 35));
        data.add(new ValueDataEntry("2022-05-02", 23));
        data.add(new ValueDataEntry("2022-05-03", 11));
        data.add(new ValueDataEntry("2022-05-04", 6));
        data.add(new ValueDataEntry("2022-05-05", 66));
        data.add(new ValueDataEntry("2022-05-06", 76));

        pie.data(data);

        pie.title("Your daily reading time");

        pie.labels().position("outside");

        pie.legend().title().enabled(true);
        pie.legend().title()
                .text("Minute")
                .padding(0d, 0d, 10d, 0d);

        pie.legend()
                .position("center-bottom")
                .itemsLayout(LegendLayout.HORIZONTAL)
                .align(Align.CENTER);

        anyChartView.setChart(pie);

        //create column chart
        AnyChartView anyChartView1 = view.findViewById(R.id.any_chart_view);
        APIlib.getInstance().setActiveAnyChartView(anyChartView1);

        Cartesian cartesian = AnyChart.column();


        List<DataEntry> data1 = new ArrayList<>();
        data1.add(new ValueDataEntry("2022-05-01", 35));
        data1.add(new ValueDataEntry("2022-05-02", 23));
        data1.add(new ValueDataEntry("2022-05-03", 11));
        data1.add(new ValueDataEntry("2022-05-04", 6));
        data1.add(new ValueDataEntry("2022-05-05", 66));
        data1.add(new ValueDataEntry("2022-05-06", 76));

        Column column = cartesian.column(data1);

        column.tooltip()
                .titleFormat("{%X}")
                .position(Position.CENTER_BOTTOM)
                .anchor(Anchor.CENTER_BOTTOM)
                .offsetX(0d)
                .offsetY(5d)
                .format("{%Value}{groupsSeparator: }");

        cartesian.animation(true);
        cartesian.title("Your daily reading time");

        cartesian.yScale().minimum(0d);

        cartesian.yAxis(0).labels().format("{%Value}{groupsSeparator: }");

        cartesian.tooltip().positionMode(TooltipPositionMode.POINT);
        cartesian.interactivity().hoverMode(HoverMode.BY_X);

        cartesian.xAxis(0).title("Date");
        cartesian.yAxis(0).title("Time (Minute)");


        Switch mSwitch = (Switch) view.findViewById(R.id.switch1);

        mSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    // The toggle is enabled
                    anyChartView.setChart(pie);

                } else {
                    // The toggle is disabled
                    anyChartView1.setChart(cartesian);


                }
            }
        });

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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        reportBinding = null;
    }
}
