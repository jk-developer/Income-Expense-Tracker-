package com.example.jitendrakumar.incometracker.fragments;


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
import com.example.jitendrakumar.incometracker.activities.BorrowActivity;
import com.example.jitendrakumar.incometracker.database.BorrowDatabaseHelper;
import com.example.jitendrakumar.incometracker.fragments.date_time_fragment.DatePickerFragment;

import java.util.Calendar;

public class BorrowFragment extends Fragment {

    EditText etPersonName, etPayingAmount, etPayingReason;
    Button btnPayingSubmit, btnViewAllPayingData;
    TextView tvBorrowDate, tvHintBorrowDate;
    BorrowDatabaseHelper borrowDatabaseHelper;
    public static final String TAG = "name";
    private   int  yr, mth , dy;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate( R.layout.fragment_borrow, container, false );
        borrowDatabaseHelper = new BorrowDatabaseHelper( getContext());

        etPersonName = (EditText) view.findViewById( R.id.etPersonName );
        etPayingAmount = (EditText) view.findViewById( R.id.etPayingAmount );
        etPayingReason = (EditText) view.findViewById( R.id.etPayingReason );
        tvBorrowDate = (TextView) view.findViewById( R.id.tvBorrowDate );
        btnPayingSubmit = (Button) view.findViewById( R.id.btnPayingSubmit );
        tvHintBorrowDate = (TextView) view.findViewById( R.id.tvHintBorrowDate );

        etPersonName.setHintTextColor(getResources().getColor(R.color.colorTexts));
        etPayingReason.setHintTextColor(getResources().getColor(R.color.colorTexts));
        etPayingAmount.setHintTextColor(getResources().getColor(R.color.colorTexts));
        tvBorrowDate.setHintTextColor(getResources().getColor(R.color.colorTexts));

        etPersonName.setTextColor( Color.parseColor("#00ff00"));
        tvBorrowDate.setTextColor( Color.parseColor("#00ff00"));
        etPayingAmount.setTextColor( Color.parseColor("#00ff00"));
        etPayingReason.setTextColor( Color.parseColor("#00ff00"));

        Calendar cal = Calendar.getInstance();
        int year = cal.get( Calendar.YEAR );
        int month = cal.get( Calendar.MONTH);
        int day = cal.get( Calendar.DAY_OF_MONTH );

        String Date = "";
        if((month+1)<=9 && day<=9)
        {
            Date = "0"+day +"/0"+(month+1) +"/"+year ;
        }
        if((month+1)<=9 && day>9){
            Date = day +"/0"+(month+1) +"/"+year ;
        }
        if((month+1)>9 && day<=9){
            Date = "0"+day +"/"+(month+1)+"/"+year ;
        }
        else {
            Date = day +"/"+(month+1)+"/"+year ;
        }

        tvBorrowDate.setText(Date);

        tvHintBorrowDate.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment dialogFragment = new DatePickerFragment(tvBorrowDate);
                dialogFragment.show( getFragmentManager(), "date picker" );
            }
        } );



        btnPayingSubmit.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    String personName = etPersonName.getText().toString();
                    Log.d( TAG, "onClick: "+personName );
                    String payingAmount = etPayingAmount.getText().toString();
                    String payingDate =  tvBorrowDate.getText().toString();
                    String payingReason = etPayingReason.getText().toString();

                    String[]dateParts = payingDate.toString().split("/");
                    try {
                        yr = safeParseInt(dateParts[2]);
                        mth = safeParseInt(dateParts[1]);
                        dy = safeParseInt(dateParts[0]);
                    } catch (Exception e) {
                        Toast.makeText( getActivity(), "Error in parsing Date", Toast.LENGTH_SHORT ).show();
                    }

                    if (personName.length()==0){
                        etPersonName.setError( "Person Name field is required!!!" );
                    }
                    if(payingAmount.length()==0){
                        etPayingAmount.setError( "Paying Amount field is required!!!" );
                    }
                    if(payingDate.length()==0)
                    {
                        Toast.makeText( getActivity(), "Date field is required!!!", Toast.LENGTH_SHORT ).show();
                    }
                    if(payingReason.length()==0){
                        etPayingReason.setError( "Reason field is required!!!" );
                    }
                    else{

                        boolean isInserted = borrowDatabaseHelper.insertPayingData( personName, Float.parseFloat(payingAmount  ) ,payingReason,yr, mth,dy);
                        if (isInserted == true) {
                            Toast.makeText( getActivity(), "Data Saved to Paying DataBase.", Toast.LENGTH_SHORT ).show();
                            Intent i = new Intent( getActivity(), BorrowActivity.class );
                            startActivity( i );

                        } else {
                            Toast.makeText( getActivity(), "Data is not Saved to Paying DataBase.", Toast.LENGTH_SHORT ).show();
                        }
                    }
                }
                catch (NullPointerException e)
                {
                    e.printStackTrace();
                }

            }
        } );

        return  view;
    }

    public int safeParseInt(String number) throws Exception {
        if(number != null) {
            return Integer.parseInt(number.trim());
        } else {
            throw new NullPointerException("Date string is invalid");
        }
    }


}
