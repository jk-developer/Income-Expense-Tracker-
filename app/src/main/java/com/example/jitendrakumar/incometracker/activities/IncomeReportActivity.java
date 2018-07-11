package com.example.jitendrakumar.incometracker.activities;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
                String incId = res.getString( 0 );
                String incType =  res.getString( 1 );
                String incAmount =  res.getString( 2 );
                String incDate = res.getString( 3 );
                String incTime =  res.getString( 4 );
                incomeData = new IncomeData( Integer.valueOf( incId ), incType, Double.valueOf( incAmount ),incDate, incTime);
                arrayList.add( incomeData);
            }

        }
        return arrayList;
    }

    public void updateList(IncomeData incData) {
                 MyincomeDB.updateIncomeData( String.valueOf( incData.getIncomeId()), incData.getInputType(), String.valueOf( incData.getInputAmount()), incData.getDate(), incData.getTime() );
    }

    public void deleteList(IncomeData incomeData){
        MyincomeDB.deleteIncomeData(String.valueOf( incomeData.getIncomeId() ) );
    }


}
