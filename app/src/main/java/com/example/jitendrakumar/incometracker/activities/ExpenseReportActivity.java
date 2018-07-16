package com.example.jitendrakumar.incometracker.activities;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.example.jitendrakumar.incometracker.R;
import com.example.jitendrakumar.incometracker.adapters.MyExpenseAdapter;
import com.example.jitendrakumar.incometracker.database.ExpenseDatabaseHelper;
import com.example.jitendrakumar.incometracker.models.ExpenseData;
import com.example.jitendrakumar.incometracker.models.IncomeData;

import java.util.ArrayList;

public class ExpenseReportActivity extends AppCompatActivity {

    RecyclerView rvExpenseReport;
    ExpenseDatabaseHelper MyexpenseDB;
    ArrayList<ExpenseData> arrayList = new ArrayList<>( );
    ExpenseData expenseData;
    MyExpenseAdapter myExpenseAdapter;
    private float totalExpense = (float) 0.00;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_expense_report );


        getSupportActionBar().setTitle( "Expense List" );
        getSupportActionBar().setDisplayHomeAsUpEnabled( true );

        MyexpenseDB  = new ExpenseDatabaseHelper( ExpenseReportActivity.this );
        rvExpenseReport = (RecyclerView) findViewById( R.id.rvExpenseReport );

        ArrayList<ExpenseData> myexpenselist = new ArrayList<>( );
        myexpenselist = getArrayList();

        myExpenseAdapter = new MyExpenseAdapter(myexpenselist, getApplicationContext());
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        rvExpenseReport.setLayoutManager(mLayoutManager);
        rvExpenseReport.setItemAnimator(new DefaultItemAnimator());
        rvExpenseReport.setHasFixedSize(true);
        rvExpenseReport.setAdapter(myExpenseAdapter);

    }

    public ArrayList<ExpenseData> getArrayList(){
        Cursor res = MyexpenseDB.getAllExpenseData();
        if(res.getCount() == 0)
        {
            Toast.makeText( ExpenseReportActivity.this, "Nothing Found in Databse!!!", Toast.LENGTH_SHORT ).show();
            return null;
        }
        else
        {
            while (res.moveToNext()){
                int expId = res.getInt( 0 );
                String expType =  res.getString( 1 );
                float incAmount =  res.getFloat( 2 );
                int incYear = res.getInt( 3 );
                int incMonth = res.getInt( 4 );
                int incDay = res.getInt( 5 );
                int incHour = res.getInt( 6 );
                int incMinute = res.getInt( 7 );
             //   Log.d( TAG, "getArrayList: details"+incId + incType+ incAmount+incYear+incMonth+incDay+incHour+incMinute);
                String Date = Integer.toString( incDay )+"/"+Integer.toString( incMonth )+"/"+Integer.toString( incYear );
              //  Log.d( TAG, "getArrayList: date "+Date );
                String Time = Integer.toString( incHour )+":"+Integer.toString( incMinute );
             //   Log.d( TAG, "getArrayList: time"+Time );
                expenseData = new ExpenseData(expId, expType, incAmount, Date, Time);
                arrayList.add( expenseData);
                totalExpense = totalExpense +incAmount;
            }

        }
        return arrayList;
            }

}
