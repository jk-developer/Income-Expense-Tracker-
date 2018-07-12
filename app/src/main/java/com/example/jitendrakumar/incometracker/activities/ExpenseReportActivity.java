package com.example.jitendrakumar.incometracker.activities;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.example.jitendrakumar.incometracker.R;
import com.example.jitendrakumar.incometracker.adapters.MyExpenseAdapter;
import com.example.jitendrakumar.incometracker.database.ExpenseDatabaseHelper;
import com.example.jitendrakumar.incometracker.models.ExpenseData;
import java.util.ArrayList;

public class ExpenseReportActivity extends AppCompatActivity {

    RecyclerView rvIncomeReport;
    ExpenseDatabaseHelper MyexpenseDB;
    ArrayList<ExpenseData> arrayList = new ArrayList<>( );
    ExpenseData expenseData;
    MyExpenseAdapter myExpenseAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_expense_report );

        MyexpenseDB  = new ExpenseDatabaseHelper( ExpenseReportActivity.this );
        rvIncomeReport = (RecyclerView) findViewById( R.id.rvIncomeReport );

        ArrayList<ExpenseData> myexpenselist = new ArrayList<>( );
        myexpenselist = getArrayList();

        myExpenseAdapter = new MyExpenseAdapter(myexpenselist, getApplicationContext());
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        rvIncomeReport.setLayoutManager(mLayoutManager);
        rvIncomeReport.setItemAnimator(new DefaultItemAnimator());
        rvIncomeReport.setHasFixedSize(true);
        rvIncomeReport.setAdapter(myExpenseAdapter);

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
                double expAmount =  res.getDouble( 2 );
                String expDate = res.getString( 3 );
                String expTime =  res.getString( 4 );
                expenseData = new ExpenseData( expId, expType, expAmount ,expDate, expTime );
                arrayList.add( expenseData);
            }

        }
        return arrayList;
    }
}
