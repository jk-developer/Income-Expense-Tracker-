package com.example.jitendrakumar.incometracker.activities;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.transition.Slide;
import android.util.Log;
import android.view.Gravity;
import android.view.Window;
import android.view.animation.AnticipateOvershootInterpolator;
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
        getWindow().requestFeature( Window.FEATURE_CONTENT_TRANSITIONS );
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_expense_report );


        getSupportActionBar().setTitle( "Expense List" );
        getSupportActionBar().setDisplayHomeAsUpEnabled( true );

        initAnimation();

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
            Toast.makeText( ExpenseReportActivity.this, "No Expense record is found!!!", Toast.LENGTH_SHORT ).show();
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
                String expDesc = res.getString( 8 );

                String Date = "";
                if(incMonth<=9 && incDay<=9)
                {
                    Date = "0"+incDay +"/0"+incMonth +"/"+incYear ;
                }
                if(incMonth<=9 && incDay>9){
                    Date = incDay +"/0"+incMonth +"/"+incYear ;
                }
                if(incMonth>9 && incDay<=9){
                    Date = "0"+incDay +"/"+incMonth +"/"+incYear ;
                }
                else {
                    Date = incDay +"/"+incMonth +"/"+incYear ;
                }

                String Time = Integer.toString( incHour )+":"+Integer.toString( incMinute );

                expenseData = new ExpenseData(expId, expType, incAmount, Date, Time, expDesc);
                arrayList.add( expenseData);
                totalExpense = totalExpense +incAmount;
            }

        }
        return arrayList;
            }

    @Override
    public boolean onSupportNavigateUp() {
        finishAfterTransition();
        return true;    }

    public void initAnimation(){
        Slide enterTransition = new Slide( );
        enterTransition.setSlideEdge( Gravity.BOTTOM);
        enterTransition.setInterpolator( new AnticipateOvershootInterpolator(  ));
        enterTransition.setDuration( 1000 );
        getWindow().setEnterTransition( enterTransition );

    }

}
