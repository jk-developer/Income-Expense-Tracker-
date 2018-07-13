package com.example.jitendrakumar.incometracker.fragments;


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
import com.example.jitendrakumar.incometracker.database.BorrowDatabaseHelper;
import com.example.jitendrakumar.incometracker.fragments.date_time_fragment.DatePickerFragment;

public class BorrowFragment extends Fragment {

    EditText etPersonName, etPayingAmount, etPayingReason;
    Button btnPayingSubmit, btnViewAllPayingData, btnPayingDate;
    TextView tvPayingDate;
    BorrowDatabaseHelper borrowDatabaseHelper;
    public static final String TAG = "name";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate( R.layout.fragment_borrow, container, false );
        borrowDatabaseHelper = new BorrowDatabaseHelper( getContext());

        etPersonName = (EditText) view.findViewById( R.id.etPersonName );
        etPayingAmount = (EditText) view.findViewById( R.id.etPayingAmount );
        etPayingReason = (EditText) view.findViewById( R.id.etPayingReason );
        tvPayingDate = (TextView) view.findViewById( R.id.tvPayingDate );
        btnPayingSubmit = (Button) view.findViewById( R.id.btnPayingSubmit );
        btnPayingDate = (Button) view.findViewById( R.id.btnPayingDate );
        btnViewAllPayingData = (Button) view.findViewById( R.id.btnViewAllPayingData );

        etPersonName.setHintTextColor(getResources().getColor(R.color.colorTexts));
        etPayingReason.setHintTextColor(getResources().getColor(R.color.colorTexts));
        etPayingAmount.setHintTextColor(getResources().getColor(R.color.colorTexts));
       tvPayingDate.setHintTextColor(getResources().getColor(R.color.colorTexts));

        etPersonName.setTextColor( Color.parseColor("#00ff00"));
       tvPayingDate.setTextColor( Color.parseColor("#00ff00"));
        etPayingAmount.setTextColor( Color.parseColor("#00ff00"));
        etPayingReason.setTextColor( Color.parseColor("#00ff00"));

        btnPayingDate.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment dialogFragment = new DatePickerFragment(tvPayingDate);
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
                    String payingDate =  tvPayingDate.getText().toString();
                    String payingReason = etPayingReason.getText().toString();

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

                        boolean isInserted = borrowDatabaseHelper.insertPayingData( personName, payingAmount ,payingReason,payingDate);
                        if (isInserted == true) {
                            Toast.makeText( getActivity(), "Data Saved to Paying DataBase.", Toast.LENGTH_SHORT ).show();

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

        showAllPayingData();
        return  view;
    }

    public void showAllPayingData(){
        btnViewAllPayingData.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Cursor res = borrowDatabaseHelper.getAllPayingData();
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
                        buffer.append( "Paying Id : "+ res.getString( 0 )+"\n" );
                        buffer.append( "Person Name : "+ res.getString( 1 )+"\n" );
                        buffer.append( "Paying Amount : "+ res.getString( 2 )+"\n" );
                        buffer.append( "Paying Reason : "+ res.getString( 3 )+"\n" );
                        buffer.append( "Paying Date : "+ res.getString( 4 )+"\n\n" );

                    }
                    // Show all data
                    showMessage( "Data", buffer.toString() );
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


}
