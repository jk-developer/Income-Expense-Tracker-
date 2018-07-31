package com.example.jitendrakumar.incometracker.activities;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.database.Cursor;
import android.support.v4.view.animation.FastOutLinearInInterpolator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.transition.Explode;
import android.transition.Slide;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.animation.AnticipateOvershootInterpolator;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.jitendrakumar.incometracker.R;
import com.example.jitendrakumar.incometracker.adapters.MyIncomeAdapter;
import com.example.jitendrakumar.incometracker.database.IncomeDatabaseHelper;
import com.example.jitendrakumar.incometracker.fragments.AddIncomeFragment;
import com.example.jitendrakumar.incometracker.fragments.LoginFragment;
import com.example.jitendrakumar.incometracker.models.IncomeData;

import java.util.ArrayList;

public class IncomeReportActivity extends AppCompatActivity {
    RecyclerView rvIncomeReport;
    IncomeDatabaseHelper MyincomeDB;
    ArrayList<IncomeData> arrayList = new ArrayList<>( );
    IncomeData incomeData;
    MyIncomeAdapter myIncomeAdapter;
    public static final String TAG = "date";
    private float totalIncome = (float) 0.00;
    private int HomeIncome, DatewiseIncomeReport, AddIncome;
    private int yrs, yrf, mths,mthf, dys, dyf;
    private Cursor res;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().requestFeature( Window.FEATURE_CONTENT_TRANSITIONS );
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_income_report );

        initPage();
        initAnimation();

        MyincomeDB = new IncomeDatabaseHelper( IncomeReportActivity.this );
        rvIncomeReport = (RecyclerView) findViewById( R.id.rvIncomeReport );

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

        try{
            res = MyincomeDB.getAllIncomeData();

            if(res.getCount() == 0)
            {
                Toast.makeText( IncomeReportActivity.this, "No Income record is found!!!", Toast.LENGTH_SHORT ).show();
                return null;
            }
            else
            {
                while (res.moveToNext()){
                    int incId = res.getInt( 0 );
                    String incType =  res.getString( 1 );
                    float incAmount =  res.getFloat( 2 );
                    int incYear = res.getInt( 3 );
                    int incMonth = res.getInt( 4 );
                    int incDay = res.getInt( 5 );
                    int incHour = res.getInt( 6 );
                    int incMinute = res.getInt( 7 );
                    String incDesc = res.getString( 8 );

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
                    Log.d( TAG, "getArrayList: time"+Time );
                    incomeData = new IncomeData(incId,incType, incAmount, Date, Time, incDesc);
                    arrayList.add( incomeData);
                    totalIncome = totalIncome +incAmount;
                }

            }
        }catch (Exception e){
            e.printStackTrace();
        }

        return arrayList;
    }

    public void initPage(){
        getSupportActionBar().setTitle( "Income List" );
        getSupportActionBar().setDisplayHomeAsUpEnabled( true );
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
