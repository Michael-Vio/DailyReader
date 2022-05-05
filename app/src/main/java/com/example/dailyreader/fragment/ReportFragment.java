package com.example.dailyreader.fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;

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

        Pie pie = AnyChart.pie();

        /*pie.setOnClickListener(new ListenersInterface.OnClickListener(new String[]{"x", "value"}) {
            @Override
            public void onClick(Event event) {
                Toast.makeText(ReportFragment.this, event.getData().get("x") + ":" + event.getData().get("value"), Toast.LENGTH_SHORT).show;
            }
        });*/

        List<DataEntry> data = new ArrayList<>();
        data.add(new ValueDataEntry("Apples", 6371664));
        data.add(new ValueDataEntry("Pears", 789622));
        data.add(new ValueDataEntry("Bananas", 7216301));
        data.add(new ValueDataEntry("Grapes", 1486621));
        data.add(new ValueDataEntry("Oranges", 1200000));

        pie.data(data);

        pie.title("Fruits imported in 2015 (in kg)");

        pie.labels().position("outside");

        pie.legend().title().enabled(true);
        pie.legend().title()
                .text("Retail channels")
                .padding(0d, 0d, 10d, 0d);

        pie.legend()
                .position("center-bottom")
                .itemsLayout(LegendLayout.HORIZONTAL)
                .align(Align.CENTER);

        anyChartView.setChart(pie);



        return view;
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        reportBinding = null;
    }
}
