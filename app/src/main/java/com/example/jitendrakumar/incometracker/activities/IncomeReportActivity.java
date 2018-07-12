package com.example.jitendrakumar.incometracker.activities;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.CheckBox;
import android.widget.Toast;

import com.example.jitendrakumar.incometracker.R;
import com.example.jitendrakumar.incometracker.adapters.MyIncomeAdapter;
import com.example.jitendrakumar.incometracker.database.IncomeDatabaseHelper;
import com.example.jitendrakumar.incometracker.models.IncomeData;

import java.util.ArrayList;

public class IncomeReportActivity extends AppCompatActivity {
    RecyclerView rvIncomeReport;
    IncomeDatabaseHelper MyincomeDB;
    ArrayList<IncomeData> arrayList = new ArrayList<>( );
    IncomeData incomeData;
    MyIncomeAdapter myIncomeAdapter;
    CheckBox checkBox;
    public static final String TAG = "date";
    private double totalIncome = 0.00 ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_income_report );
        MyincomeDB = new IncomeDatabaseHelper( IncomeReportActivity.this );
        rvIncomeReport = (RecyclerView) findViewById( R.id.rvIncomeReport );
        checkBox = (CheckBox) findViewById( R.id.checkBox );

        ArrayList<IncomeData> myincomelist = new ArrayList<>();
        myincomelist = getArrayList();

        myIncomeAdapter = new MyIncomeAdapter( myincomelist, this );

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager( getApplicationContext() );
        rvIncomeReport.setLayoutManager( mLayoutManager );
        rvIncomeReport.setItemAnimator( new DefaultItemAnimator() );
        rvIncomeReport.setHasFixedSize( true );
        rvIncomeReport.setAdapter( myIncomeAdapter );

    }

    public ArrayList<IncomeData> getArrayList(){
        Cursor res = MyincomeDB.getAllIncomeData();
        if(res.getCount() == 0)
        {
            Toast.makeText( IncomeReportActivity.this, "Nothing Found in Databse!!!", Toast.LENGTH_SHORT ).show();
            return null;
        }
        else
        {
            while (res.moveToNext()){
                int incId = res.getInt( 0 );
                String incType =  res.getString( 1 );
                Double incAmount =  res.getDouble( 2 );
                int incYear = res.getInt( 3 );
                int incMonth = res.getInt( 4 );
                int incDay = res.getInt( 5 );
                int incHour = res.getInt( 6 );
                int incMinute = res.getInt( 7 );
                Log.d( TAG, "getArrayList: details"+incId + incType+ incAmount+incYear+incMonth+incDay+incHour+incMinute);
                String Date = Integer.toString( incDay )+"/"+Integer.toString( incMonth )+"/"+Integer.toString( incYear );
                Log.d( TAG, "getArrayList: date "+Date );
                String Time = Integer.toString( incHour )+":"+Integer.toString( incMinute );
                Log.d( TAG, "getArrayList: time"+Time );
                incomeData = new IncomeData(incId, incType, incAmount,Date, Time);
                arrayList.add( incomeData);
                totalIncome = totalIncome +incAmount;
            }

        }
        return arrayList;
    }
/*
    public void updateList(IncomeData incData) {
                 MyincomeDB.updateIncomeData(String.valueOf( incData.getIncomeId() ), incData.getInputType(), incData.getInputAmount(),
                         incData.getIncomeYear(), incData.getIncomeMonth(), incData.getIncomeDay(), incData.getIncomeHour(), incData.getIncomeMinute());
    }

    public void deleteList(IncomeData incomeData){
        MyincomeDB.deleteIncomeData(String.valueOf( incomeData.getIncomeId() ) );
    }
*/


}
