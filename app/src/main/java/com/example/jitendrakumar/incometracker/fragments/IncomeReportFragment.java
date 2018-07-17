package com.example.jitendrakumar.incometracker.fragments;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jitendrakumar.incometracker.R;
import com.example.jitendrakumar.incometracker.activities.BarChartActivity;
import com.example.jitendrakumar.incometracker.activities.IncomePiechartActivity;
import com.example.jitendrakumar.incometracker.activities.MainActivity;
import com.example.jitendrakumar.incometracker.database.ExpenseDatabaseHelper;
import com.example.jitendrakumar.incometracker.database.IncomeDatabaseHelper;
import com.example.jitendrakumar.incometracker.fragments.date_time_fragment.DatePickerFragment;
import com.example.jitendrakumar.incometracker.models.IncomeData;

import java.util.Calendar;

@SuppressLint("ValidFragment")
public class IncomeReportFragment extends Fragment {
    TextView tvIncomeReportDateFrom, tvIncomeReportDateTo;
    IncomeDatabaseHelper myIncomeDB;
    Button btnViewIncomeReport, btnIncomeReportDateFrom, btnIncomeReportDateTo, btnIncomeBarchart,btnIncomePiechart,btnIncomebwdate, btnIncomebw;
    private String id;
    public static final String TAG = "res";
    private int years, yearf, months, monthf, days,dayf;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate( R.layout.fragment_income_report, container, false );

        tvIncomeReportDateFrom = (TextView) view.findViewById( R.id.tvIncomeReportDateFrom);
        tvIncomeReportDateTo = (TextView) view.findViewById( R.id.tvIncomeReportDateTo);
        btnViewIncomeReport = (Button)view.findViewById( R.id.btnViewIncomeReport );
        btnIncomeReportDateFrom = (Button)view.findViewById( R.id.btnIncomeReportDateFrom );
        btnIncomeReportDateTo  = (Button) view.findViewById( R.id.btnIncomeReportDateTo );
        btnIncomeBarchart = (Button) view.findViewById( R.id.btnIncomeBarchart );
        btnIncomePiechart = (Button) view.findViewById( R.id.btnIncomePiechart );
        btnIncomebwdate = (Button) view.findViewById( R.id.btnIncomebwdate );
        btnIncomebw = (Button) view.findViewById( R.id.btnIncomebw );
        myIncomeDB = new IncomeDatabaseHelper( getContext());

        tvIncomeReportDateFrom.setHintTextColor(getResources().getColor(R.color.colorTexts));
        tvIncomeReportDateTo.setHintTextColor(getResources().getColor(R.color.colorTexts));
        tvIncomeReportDateFrom.setTextColor( Color.parseColor("#00ff00"));
        tvIncomeReportDateTo.setTextColor( Color.parseColor("#00ff00"));

        showAllIncomeData();
        showRecordbwDays();
        showRecords();
        btnIncomeReportDateFrom.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment newFragment = new DatePickerFragment( tvIncomeReportDateFrom );
                newFragment.show( getFragmentManager(), "DatePicker" );
            }
        } );

        btnIncomeReportDateTo.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment newFragment = new DatePickerFragment( tvIncomeReportDateTo );
                newFragment.show( getFragmentManager(), "DatePicker" );
            }
        } );

        btnIncomeBarchart.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Intent i = new Intent( getActivity(), BarChartActivity.class );
               startActivity( i );
            }
        } );

        btnIncomePiechart.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent( getActivity(), IncomePiechartActivity.class );
                startActivity( i );
            }
        } );
      /*
        btnGet.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             Cursor res   myIncomeDB.getRecordCategorywise( "Regular Salary" );
            }
        } );


*/

        return view;
    }

    public void showAllIncomeData(){
        btnViewIncomeReport.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String dateFrom = tvIncomeReportDateFrom.getText().toString();
                String dateTo = tvIncomeReportDateTo.getText().toString();
                String[]dateParts = dateFrom.toString().split("/");
                try {
                    years = safeParseInt(dateParts[2]);
                    months = safeParseInt(dateParts[1]);
                    days = safeParseInt(dateParts[0]);
                } catch (Exception e) {
                    Toast.makeText( getActivity(), "Error in parsing Date", Toast.LENGTH_SHORT ).show();
                }

                String[]date_2Parts = dateTo.toString().split( "/" );
                try {
                    yearf = safeParseInt(date_2Parts[2]);
                    monthf = safeParseInt(date_2Parts[1]);
                    dayf = safeParseInt(date_2Parts[0]);
                } catch (Exception e) {
                    Toast.makeText( getActivity(), "Error in parsing Date", Toast.LENGTH_SHORT ).show();
                }

                try {
                    Log.d( TAG, "onClick: Clicked ");
                    Cursor res = myIncomeDB.getRecordbwyears(years, yearf, months, monthf, days, dayf);
                    Log.d( TAG, "onClick: result"+res.getCount() );
                    if(res.getCount() == 0)
                    {
                        // Show message
                        showMessage( "Error", "Nothing Found" );

                        return;
                    }
                    else
                    {
                        StringBuffer buffer = new StringBuffer(  );
                        while (res.moveToNext()){
                            buffer.append( "Income Id : "+ res.getInt( 0 )+"\n" );
                            buffer.append( "Income Type : "+ res.getString( 1 )+"\n" );
                            buffer.append( "Income Amount : "+ res.getFloat( 2 )+"\n" );
                            String date = res.getInt( 4 )+"/"+res.getInt( 5 )+"/"+res.getInt( 3 );
                            String time = res.getInt( 6 )+":"+res.getInt( 7 );
                            buffer.append( "Date : "+date+"\n" );
                            buffer.append( "Time : "+ time+"\n\n" );

                        }
                        // Show all data
                        showMessage( "Data", buffer.toString() );
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }


            }

        } );
    }

    public void showRecordbwDays(){

        btnIncomebwdate.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try {
                    Log.d( TAG, "onClick: Clicked ");
                    Cursor r = myIncomeDB.getRecordbwDays( 6, 12, 1, 17 );
                    Log.d( TAG, "onClick: result"+r.getCount() );
                    if(r.getCount() == 0)
                    {
                        // Show message
                        showMessage( "Error", "Nothing Found" );

                        return;
                    }
                    else
                    {
                        StringBuffer buffer = new StringBuffer(  );
                        while (r.moveToNext()){
                            buffer.append( "Income Id : "+ r.getInt( 0 )+"\n" );
                            buffer.append( "Income Type : "+ r.getString( 1 )+"\n" );
                            buffer.append( "Income Amount : "+ r.getFloat( 2 )+"\n" );
                            String date = r.getInt( 4 )+"/"+r.getInt( 5 )+"/"+r.getInt( 3 );
                            String time = r.getInt( 6 )+":"+r.getInt( 7 );
                            buffer.append( "Date : "+date+"\n" );
                            buffer.append( "Time : "+ time+"\n\n" );

                        }
                        // Show all data
                        showMessage( "Data", buffer.toString() );
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }


            }

        } );

    }

    public void showRecords(){

        btnIncomebw.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try {
                    Log.d( TAG, "onClick: Clicked ");
                    Cursor r = myIncomeDB.getRecordbwMonths( 8,11 );
                    Log.d( TAG, "onClick: result"+r.getCount() );
                    if(r.getCount() == 0)
                    {
                        // Show message
                        showMessage( "Error", "Nothing Found" );

                        return;
                    }
                    else
                    {
                        StringBuffer buffer = new StringBuffer(  );
                        while (r.moveToNext()){
                            buffer.append( "Income Id : "+ r.getInt( 0 )+"\n" );
                            buffer.append( "Income Type : "+ r.getString( 1 )+"\n" );
                            buffer.append( "Income Amount : "+ r.getFloat( 2 )+"\n" );
                            String date = r.getInt( 4 )+"/"+r.getInt( 5 )+"/"+r.getInt( 3 );
                            String time = r.getInt( 6 )+":"+r.getInt( 7 );
                            buffer.append( "Date : "+date+"\n" );
                            buffer.append( "Time : "+ time+"\n\n" );

                        }
                        // Show all data
                        showMessage( "Data", buffer.toString() );
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }


            }

        } );

    }


    public void showMessage(String title, String Message){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setCancelable( true );
        builder.setTitle( title );
        builder.setMessage( Message );
        builder.show();
    }

    public int safeParseInt(String number) throws Exception {
        if(number != null) {
            return Integer.parseInt(number.trim());
        } else {
            throw new NullPointerException("Date string is invalid");
        }
    }


}
