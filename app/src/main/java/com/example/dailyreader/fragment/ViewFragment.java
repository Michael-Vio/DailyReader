package com.example.dailyreader.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.anychart.APIlib;
import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.charts.Cartesian;
import com.anychart.core.cartesian.series.Column;
import com.anychart.enums.Anchor;
import com.anychart.enums.HoverMode;
import com.anychart.enums.Position;
import com.anychart.enums.TooltipPositionMode;
import com.example.dailyreader.R;

import androidx.fragment.app.Fragment;

import com.example.dailyreader.databinding.ViewFragmentBinding;

import java.util.ArrayList;
import java.util.List;

public class ViewFragment extends Fragment {
    private ViewFragmentBinding binding;
    public ViewFragment(){}
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the View for this fragment using the binding
        binding = ViewFragmentBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        generateColChart(view);

        return view;
    }

    public void generateColChart(View view){

        //create column chart
        View chartView = view.findViewById(R.id.any_chart_view1);

        //generate progressBar
        APIlib.getInstance().setActiveAnyChartView((AnyChartView) chartView);
        ((AnyChartView) chartView).setProgressBar(view.findViewById(R.id.progress_bar1));

        Cartesian mCartesian = AnyChart.column();

        Column column = mCartesian.column(createData());

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


        APIlib.getInstance().setActiveAnyChartView((AnyChartView) chartView);
        ((AnyChartView) chartView).setChart(mCartesian);


    }

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
        binding = null;
    } }
