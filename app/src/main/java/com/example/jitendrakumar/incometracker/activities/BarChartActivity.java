package com.example.jitendrakumar.incometracker.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.jitendrakumar.incometracker.R;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

public class BarChartActivity extends AppCompatActivity {
      BarChart barChart;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_bar_chart );

        BarChart barChart = (BarChart) findViewById(R.id.barChart);

        ArrayList<BarEntry> entries = new ArrayList<>();
        entries.add(new BarEntry(8f, 0));
        entries.add(new BarEntry(2f, 1));
        entries.add(new BarEntry(5f, 2));
        entries.add(new BarEntry(20f, 3));
        entries.add(new BarEntry(15f, 4));
        entries.add(new BarEntry(19f, 5));
        entries.add(new BarEntry(8f, 6));
        entries.add(new BarEntry(2f, 7));
        entries.add(new BarEntry(5f, 8));
        entries.add(new BarEntry(20f, 9));
        entries.add(new BarEntry(15f, 10));
        entries.add(new BarEntry(19f, 11));

        BarDataSet bardataset = new BarDataSet(entries, "Cells");

        ArrayList<String> labels = new ArrayList<String>();
        labels.add("Dec");
        labels.add("Nov");
        labels.add("Oct");
        labels.add("Sep");
        labels.add("Aug");
        labels.add("Jul");
        labels.add("Jun");
        labels.add("May");
        labels.add("Apr");
        labels.add("Mar");
        labels.add("Feb");
        labels.add("Jan");

       /* BarData data = new BarData(labels , bardataset);
        barChart.setData(data); // set the data and list of lables into chart

        barChart.setDescription("Expenditure for the year 2018");  // set the description

        bardataset.setColors( ColorTemplate.COLORFUL_COLORS);

        barChart.animateY(5000);

*/
    }
}
