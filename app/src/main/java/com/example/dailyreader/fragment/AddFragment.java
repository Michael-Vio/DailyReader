package com.example.dailyreader.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
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

import java.util.ArrayList;
import java.util.List;

public class AddFragment extends Fragment {

    private AddFragmentBinding addBinding;
    public AddFragment(){}
    private Pie pie;
    private AnyChartView chartView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the View for this fragment
        addBinding = AddFragmentBinding.inflate(inflater, container, false);
        View view = addBinding.getRoot();

        generatePieChart(view);

        return view;
    }

    public void generatePieChart(View view){

        //AnyChart implement
        //setContentView(R.layout.activity_chart_common);
        View chartView = view.findViewById(R.id.any_chart_view);

        //generate progressBar
        APIlib.getInstance().setActiveAnyChartView((AnyChartView) chartView);
        ((AnyChartView) chartView).setProgressBar(view.findViewById(R.id.progress_bar));

        Pie mPie = AnyChart.pie();

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

        APIlib.getInstance().setActiveAnyChartView((AnyChartView) chartView);
        ((AnyChartView) chartView).setChart(mPie);

        pie = mPie;

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
        addBinding = null;
    } }
