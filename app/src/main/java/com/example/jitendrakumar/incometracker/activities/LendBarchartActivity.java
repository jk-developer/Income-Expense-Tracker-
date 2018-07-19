package com.example.jitendrakumar.incometracker.activities;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.jitendrakumar.incometracker.R;
import com.example.jitendrakumar.incometracker.database.LendDatabaseHelper;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

public class LendBarchartActivity extends AppCompatActivity {

    BarChart barChart;
    LendDatabaseHelper lendDatabaseHelper;
    private float monthlyLends[] = new float[12];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_lend_barchart );

            barChart = (BarChart) findViewById( R.id.barChart );
            lendDatabaseHelper = new LendDatabaseHelper( this );
            for(int i=0;i<12;i++)
                monthlyLends[i]= (float) 0.0;

            Cursor res = lendDatabaseHelper.getMonthlyLend();
            if(res.getCount() == 0)
            {
                Toast.makeText( LendBarchartActivity.this, "Nothing Found in Databse!!!", Toast.LENGTH_SHORT ).show();

            }
            else {
                int i = 0;
                while (res.moveToNext()) {
                    int month = res.getInt( 0 );
                    float amount = res.getFloat( 1 );
                    monthlyLends[month-1] = amount;

                }
            }
            ArrayList<BarEntry> entries = new ArrayList<>();
            entries.add(new BarEntry(monthlyLends[0], 0));
            entries.add(new BarEntry(monthlyLends[1], 1));
            entries.add(new BarEntry(monthlyLends[2], 2));
            entries.add(new BarEntry(monthlyLends[3], 3));
            entries.add(new BarEntry(monthlyLends[4], 4));
            entries.add(new BarEntry(monthlyLends[5], 5));
            entries.add(new BarEntry(monthlyLends[6], 6));
            entries.add(new BarEntry(monthlyLends[7], 7));
            entries.add(new BarEntry(monthlyLends[8], 8));
            entries.add(new BarEntry(monthlyLends[9], 9));
            entries.add(new BarEntry(monthlyLends[10], 10));
            entries.add(new BarEntry(monthlyLends[11], 11));

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

            barChart.setDescription("Expense Report for the year 2018");  // set the description

            bardataset.setColors( ColorTemplate.COLORFUL_COLORS);

            barChart.animateY(5000);


        }

    }

