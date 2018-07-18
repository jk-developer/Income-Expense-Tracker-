package com.example.jitendrakumar.incometracker.fragments;

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
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jitendrakumar.incometracker.R;
import com.example.jitendrakumar.incometracker.activities.BarChartActivity;
import com.example.jitendrakumar.incometracker.activities.ExpenseBarchartActivity;
import com.example.jitendrakumar.incometracker.database.ExpenseDatabaseHelper;
import com.example.jitendrakumar.incometracker.database.IncomeDatabaseHelper;
import com.example.jitendrakumar.incometracker.fragments.date_time_fragment.DatePickerFragment;

import java.util.Calendar;

public class ExpenseReportFragment extends Fragment {
    TextView tvExpenseReportDateFrom, tvExpenseReportDateTo, tvHintExpenseReportDateFrom, tvHintExpenseReportDateTo;
    ExpenseDatabaseHelper expenseDatabaseHelper;
    Button btnExpenseReportbwTwoDates, btnExpenseBarchart, btnExpenseReportbwMonthsAndDay, btnViewExpenseReportbwMonths;
    EditText etFromMonth, etToMonth, etToDay, etFromDay,etExpenseReportFromMonth,etExpenseReportToMonth;
    private String id;
    public static final String TAG = "res";
    private int years, yearf, months, monthf, days,dayf;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate( R.layout.fragment_expense_report, container, false );

        tvExpenseReportDateFrom = (TextView) view.findViewById( R.id.tvExpenseReportDateFrom);
        tvExpenseReportDateTo = (TextView) view.findViewById( R.id.tvExpenseReportDateTo);
        btnExpenseReportbwTwoDates = (Button)view.findViewById( R.id.btnExpenseReportbwTwoDates );
        tvHintExpenseReportDateFrom = (TextView) view.findViewById( R.id.tvHintExpenseReportDateFrom );
        tvHintExpenseReportDateTo  = (TextView) view.findViewById( R.id.tvHintExpenseReportDateTo );
        btnExpenseBarchart = (Button) view.findViewById( R.id.btnExpenseBarchart );
        btnExpenseReportbwMonthsAndDay = (Button) view.findViewById( R.id.btnExpenseReportbwMonthsAndDay);
        btnViewExpenseReportbwMonths = (Button) view.findViewById( R.id.btnViewExpenseReportbwMonths );
        etFromMonth = (EditText) view.findViewById( R.id.etFromMonth );
        etToMonth = (EditText)view.findViewById( R.id.etToMonth );
        etExpenseReportToMonth = (EditText)view.findViewById( R.id.etExpenseReportToMonth );
        etToDay = (EditText)view.findViewById( R.id.etToDay );
        etFromDay = (EditText)view.findViewById( R.id.etFromDay );
        etExpenseReportFromMonth = (EditText)view.findViewById( R.id.etExpenseReportFromMonth );

        expenseDatabaseHelper= new ExpenseDatabaseHelper( getContext());

        tvExpenseReportDateFrom.setHintTextColor(getResources().getColor(R.color.colorTexts));
        tvExpenseReportDateTo.setHintTextColor(getResources().getColor(R.color.colorTexts));
        tvExpenseReportDateFrom.setTextColor( Color.parseColor("#00ff00"));
        tvExpenseReportDateTo.setTextColor( Color.parseColor("#00ff00"));
        etFromMonth.setTextColor( Color.parseColor("#00ff00"));
        etToMonth.setTextColor( Color.parseColor("#00ff00"));
        etFromDay.setTextColor( Color.parseColor("#00ff00"));
        etToDay.setTextColor( Color.parseColor("#00ff00"));
        etExpenseReportFromMonth.setTextColor( Color.parseColor("#00ff00"));
        etExpenseReportToMonth.setTextColor( Color.parseColor("#00ff00"));

        showAllExpenseDatabwDates();
        showRecordbwMonthsandDays();
        showRecordsbwMonths();
        Calendar cal = Calendar.getInstance();
        int year = cal.get( Calendar.YEAR );
        int month = cal.get( Calendar.MONTH);
        int day = cal.get( Calendar.DAY_OF_MONTH );

        tvExpenseReportDateTo.setText( day+"/"+(month+1)+"/"+year );
        tvExpenseReportDateFrom.setText( day+"/"+(month+1)+"/"+year );

        tvHintExpenseReportDateFrom.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment newFragment = new DatePickerFragment( tvExpenseReportDateFrom );
                newFragment.show( getFragmentManager(), "DatePicker" );
            }
        } );

        tvHintExpenseReportDateTo.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment newFragment = new DatePickerFragment( tvExpenseReportDateTo );
                newFragment.show( getFragmentManager(), "DatePicker" );
            }
        } );

        btnExpenseBarchart.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent( getActivity(), ExpenseBarchartActivity.class );
                startActivity( i );
            }
        } );


        return view;
    }


    public void showMessage(String title, String Message){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setCancelable( true );
        builder.setTitle( title );
        builder.setMessage( Message );
        builder.show();
    }

    public void showAllExpenseDatabwDates(){
        btnExpenseReportbwTwoDates.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String dateFrom = tvExpenseReportDateFrom.getText().toString();
                String dateTo = tvExpenseReportDateTo.getText().toString();
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
                    Cursor res = expenseDatabaseHelper.getRecordbwyears(years, yearf, months, monthf, days, dayf);
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
                            String date = res.getInt( 5 )+"/"+res.getInt( 4 )+"/"+res.getInt( 3 );
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


    public void showRecordbwMonthsandDays(){

        btnExpenseReportbwMonthsAndDay.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try {
                    //here etFromMonth and etToDate represents years and others 2 months
                    int y1 = Integer.parseInt( etFromMonth.getText().toString());
                    int y2 = Integer.parseInt( etToMonth.getText().toString());
                    int m1 = Integer.parseInt( etFromDay.getText().toString());
                    int m2 = Integer.parseInt( etToDay.getText().toString());

                    Cursor r = expenseDatabaseHelper.getRecordbwDays( y1, y2, m1, m2 );
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
                            String date = r.getInt( 5 )+"/"+r.getInt( 4 )+"/"+r.getInt( 3 );
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

    public void showRecordsbwMonths(){

        btnViewExpenseReportbwMonths.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d( TAG, "onClick: bwMonths" );
                try {
                    int month1 = Integer.parseInt( etExpenseReportFromMonth.getText().toString());
                    int month2 = Integer.parseInt( etExpenseReportToMonth.getText().toString() );
                    Cursor r = expenseDatabaseHelper.getRecordbwMonths( month1,month2 );
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
                            String date = r.getInt( 5 )+"/"+r.getInt( 4 )+"/"+r.getInt( 3 );
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


    public int safeParseInt(String number) throws Exception {
        if(number != null) {
            return Integer.parseInt(number.trim());
        } else {
            throw new NullPointerException("Date string is invalid");
        }
    }

}



