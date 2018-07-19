package com.example.jitendrakumar.incometracker.activities;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.jitendrakumar.incometracker.R;
import com.example.jitendrakumar.incometracker.database.BorrowDatabaseHelper;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

public class BorrowBarchartActivity extends AppCompatActivity {

    BarChart barChart;
    BorrowDatabaseHelper borrowDatabaseHelper;
    private float monthlyBorrows[] = new float[12];
    public static final String TAG = "incomes";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_borrow_barchart );

        barChart = (BarChart) findViewById( R.id.barChart );
            borrowDatabaseHelper = new BorrowDatabaseHelper( this );
            for(int i=0;i<12;i++)
                monthlyBorrows[i]= (float) 0.0;

            Cursor res = borrowDatabaseHelper.getMonthlyBorrow();
            if(res.getCount() == 0)
            {
                Toast.makeText( BorrowBarchartActivity.this, "Nothing Found in Databse!!!", Toast.LENGTH_SHORT ).show();

            }
            else {
                int i = 0;
                while (res.moveToNext()) {
                    int month = res.getInt( 0 );
                    float amount = res.getFloat( 1 );
                    monthlyBorrows[month-1] = amount;
                    Log.d( TAG, "onCreate: month " + month+"amount"+amount );

                }
            }
            ArrayList<BarEntry> entries = new ArrayList<>();
            entries.add(new BarEntry(monthlyBorrows[0], 0));
            entries.add(new BarEntry(monthlyBorrows[1], 1));
            entries.add(new BarEntry(monthlyBorrows[2], 2));
            entries.add(new BarEntry(monthlyBorrows[3], 3));
            entries.add(new BarEntry(monthlyBorrows[4], 4));
            entries.add(new BarEntry(monthlyBorrows[5], 5));
            entries.add(new BarEntry(monthlyBorrows[6], 6));
            entries.add(new BarEntry(monthlyBorrows[7], 7));
            entries.add(new BarEntry(monthlyBorrows[8], 8));
            entries.add(new BarEntry(monthlyBorrows[9], 9));
            entries.add(new BarEntry(monthlyBorrows[10], 10));
            entries.add(new BarEntry(monthlyBorrows[11], 11));

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

