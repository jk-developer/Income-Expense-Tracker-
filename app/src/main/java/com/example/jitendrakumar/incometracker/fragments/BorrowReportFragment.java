package com.example.jitendrakumar.incometracker.fragments;


import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.jitendrakumar.incometracker.R;
import com.example.jitendrakumar.incometracker.activities.BorrowActivity;
import com.example.jitendrakumar.incometracker.activities.LendActivity;
import com.example.jitendrakumar.incometracker.database.BorrowDatabaseHelper;

public class BorrowReportFragment extends Fragment {
    EditText etLendReportMonthTo, etLendReportMonthFrom;
    Button btnViewLendReport;
    BorrowDatabaseHelper bHelper;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate( R.layout.fragment_borrow_report, container, false );
        etLendReportMonthFrom = (EditText) view.findViewById( R.id.etLendReportMonthFrom );
        etLendReportMonthTo = (EditText) view.findViewById( R.id.etLendReportMonthTo );
        btnViewLendReport = (Button) view.findViewById( R.id.btnViewLendReport );
        bHelper = new BorrowDatabaseHelper( getContext() );

        btnViewLendReport.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {

                    Cursor res = bHelper.getDataNamewise("harsh");
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
                            buffer.append( "Borrow Id : "+ res.getInt( 0 )+"\n" );
                            buffer.append( "Person Name : "+ res.getString( 1 )+"\n" );
                            buffer.append( "Amount : "+ res.getFloat( 2 )+"\n" );
                            buffer.append( "Description : "+  res.getString( 3 )+"\n" );
                            buffer.append( "Date : "+ res.getString( 4 )+"\n\n" );

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

}
