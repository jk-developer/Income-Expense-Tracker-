package com.example.jitendrakumar.incometracker.fragments;


import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
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
import com.example.jitendrakumar.incometracker.activities.BorrowActivity;
import com.example.jitendrakumar.incometracker.activities.BorrowBarchartActivity;
import com.example.jitendrakumar.incometracker.activities.LendActivity;
import com.example.jitendrakumar.incometracker.database.BorrowDatabaseHelper;
import com.example.jitendrakumar.incometracker.fragments.date_time_fragment.DatePickerFragment;

import java.util.Calendar;

public class BorrowReportFragment extends Fragment {
    TextView tvBorrowReportDateFrom, tvBorrowReportDateTo, tvHintBorrowReportDateFrom, tvHintBorrowReportDateTo;
    Button btnBorrowReportbwTwoDates, btnBorrowBarchart, btnBorrowReportLender, btnBorrowReportbwMonthsAndDay;
    EditText etFromMonth, etToMonth, etToDay, etFromDay, etBorrowLender;
    BorrowDatabaseHelper bHelper;
    private int years, yearf, months, monthf, days,dayf;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate( R.layout.fragment_borrow_report, container, false );

        tvBorrowReportDateFrom = (TextView) view.findViewById( R.id.tvBorrowReportDateFrom);
        tvBorrowReportDateTo = (TextView) view.findViewById( R.id.tvBorrowReportDateTo);
        btnBorrowReportbwTwoDates = (Button)view.findViewById( R.id.btnBorrowReportbwTwoDates );
        tvHintBorrowReportDateFrom = (TextView) view.findViewById( R.id.tvHintBorrowReportDateFrom );
        tvHintBorrowReportDateTo  = (TextView) view.findViewById( R.id.tvHintBorrowReportDateTo );
        btnBorrowBarchart = (Button) view.findViewById( R.id.btnBorrowBarchart );
        btnBorrowReportbwMonthsAndDay = (Button) view.findViewById( R.id.btnBorrowReportbwMonthsAndDay);
        btnBorrowReportLender = (Button) view.findViewById( R.id.btnBorrowReportLender );
        etBorrowLender = (EditText) view.findViewById( R.id.etBorrowLender );
        etFromMonth = (EditText) view.findViewById( R.id.etFromMonth );
        etToMonth = (EditText)view.findViewById( R.id.etToMonth );
        etToDay = (EditText)view.findViewById( R.id.etToDay );
        etFromDay = (EditText)view.findViewById( R.id.etFromDay );

        bHelper = new BorrowDatabaseHelper( getContext());

        tvBorrowReportDateFrom.setHintTextColor(getResources().getColor(R.color.colorTexts));
        tvBorrowReportDateTo.setHintTextColor(getResources().getColor(R.color.colorTexts));
        tvBorrowReportDateFrom.setTextColor( Color.parseColor("#00ff00"));
        tvBorrowReportDateTo.setTextColor( Color.parseColor("#00ff00"));
        etFromMonth.setTextColor( Color.parseColor("#00ff00"));
        etToMonth.setTextColor( Color.parseColor("#00ff00"));
        etFromDay.setTextColor( Color.parseColor("#00ff00"));
        etToDay.setTextColor( Color.parseColor("#00ff00"));

        showAllIncomeDatabwDates();
        showRecordbwMonthsandDays();

        Calendar cal = Calendar.getInstance();
        int year = cal.get( Calendar.YEAR );
        int month = cal.get( Calendar.MONTH);
        int day = cal.get( Calendar.DAY_OF_MONTH );

        tvBorrowReportDateTo.setText( day+"/"+(month+1)+"/"+year );
        tvBorrowReportDateFrom.setText( day+"/"+(month+1)+"/"+year );

        tvHintBorrowReportDateFrom.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment newFragment = new DatePickerFragment( tvBorrowReportDateFrom );
                newFragment.show( getFragmentManager(), "DatePicker" );
            }
        } );

        tvHintBorrowReportDateTo.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment newFragment = new DatePickerFragment( tvBorrowReportDateTo );
                newFragment.show( getFragmentManager(), "DatePicker" );
            }
        } );

        btnBorrowBarchart.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent( getActivity(), BorrowBarchartActivity.class );
                startActivity( i );
            }
        } );


        btnBorrowReportLender.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String lender = etBorrowLender.getText().toString();
                try{
                    Cursor r = bHelper.getDataNamewise(lender);
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
                            buffer.append( "Borrow Id : "+ r.getInt( 0 )+"\n" );
                            buffer.append( "Lender name : "+ r.getString( 1 )+"\n" );
                            buffer.append( "Amount : "+ r.getFloat( 2 )+"\n" );
                            String date = r.getInt( 5 )+"/"+r.getInt( 4 )+"/"+r.getInt( 3 );
                            buffer.append( "Date : "+date+"\n" );


                        }
                        // Show all data
                        showMessage( "Data", buffer.toString() );
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
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

    public void showAllIncomeDatabwDates(){
        btnBorrowReportbwTwoDates.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String dateFrom = tvBorrowReportDateFrom.getText().toString();
                String dateTo = tvBorrowReportDateTo.getText().toString();
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

                    Cursor r = bHelper.getRecordbwyears(years, yearf, months, monthf, days, dayf);

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
                            buffer.append( "Borrow Id : "+ r.getInt( 0 )+"\n" );
                            buffer.append( "Lender name : "+ r.getString( 1 )+"\n" );
                            buffer.append( "Amount : "+ r.getFloat( 2 )+"\n" );
                            String date = r.getInt( 5 )+"/"+r.getInt( 4 )+"/"+r.getInt( 3 );
                            buffer.append( "Date : "+date+"\n" );

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

        btnBorrowReportbwMonthsAndDay.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try {
                    int y1 = Integer.parseInt( etFromMonth.getText().toString());
                    int y2 = Integer.parseInt( etToMonth.getText().toString());
                    int m1 = Integer.parseInt( etFromDay.getText().toString());
                    int m2 = Integer.parseInt( etToDay.getText().toString());

                    Cursor r = bHelper.getRecordbwDays( y1, y2, m1, m2 );
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
                            buffer.append( "Borrow Id : "+ r.getInt( 0 )+"\n" );
                            buffer.append( "Lender name : "+ r.getString( 1 )+"\n" );
                            buffer.append( "Amount : "+ r.getFloat( 2 )+"\n" );
                            String date = r.getInt( 5 )+"/"+r.getInt( 4 )+"/"+r.getInt( 3 );
                            buffer.append( "Date : "+date+"\n" );


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
