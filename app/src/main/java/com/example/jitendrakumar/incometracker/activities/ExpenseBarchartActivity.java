package com.example.jitendrakumar.incometracker.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.jitendrakumar.incometracker.R;
import com.example.jitendrakumar.incometracker.database.ExpenseDatabaseHelper;
import com.example.jitendrakumar.incometracker.database.IncomeDatabaseHelper;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

public class ExpenseBarchartActivity extends AppCompatActivity {

    BarChart barChart;
    ExpenseDatabaseHelper expenseDB;
    private float monthlyExpenses[] = new float[12];
    public static final String TAG = "incomes";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_expense_barchart );
        barChart = (BarChart) findViewById(R.id.barChart);
/*
        expenseDB = new ExpenseDatabaseHelper( this );
        for(int i=0;i<12;i++)
            monthlyExpenses[i]= (float) 0.0;

        for(int j=1;j<=12;j++)
        {
         //   monthlyExpenses[j-1] =  expenseDB.getMonthlyIncome(j);
            Log.d( TAG, "onCreate: incomes "+monthlyExpenses[j-1]+"\n" );
        }
   */
        ArrayList<BarEntry> entries = new ArrayList<>();
        entries.add(new BarEntry(12f, 0));
        entries.add(new BarEntry(5f, 1));
        entries.add(new BarEntry(6f, 2));
        entries.add(new BarEntry(19f, 3));
        entries.add(new BarEntry(8f, 4));
        entries.add(new BarEntry(5f, 5));
        entries.add(new BarEntry(12f, 6));
        entries.add(new BarEntry(19f, 7));
        entries.add(new BarEntry(9f, 8));
        entries.add(new BarEntry(19f, 9));
        entries.add(new BarEntry(5f, 10));
        entries.add(new BarEntry(15f, 11));

        BarDataSet bardataset = new BarDataSet(entries, "Cells");

        ArrayList<String> labels = new ArrayList<String>();
        labels.add("Jan");
        labels.add("Feb");
        labels.add("Mar");
        labels.add("Apr");
        labels.add("May");
        labels.add("Jun");
        labels.add("Jul");
        labels.add("Aug");
        labels.add("Sep");
        labels.add("Oct");
        labels.add("Nov");
        labels.add("Dec");

        BarData data = new BarData(labels, bardataset);
        barChart.setData(data); // set the data and list of lables into chart

        barChart.setDescription("Income Report for the year 2018");  // set the description

        bardataset.setColors( ColorTemplate.COLORFUL_COLORS);

        barChart.animateY(5000);


    }
}
