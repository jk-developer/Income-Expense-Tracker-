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
import com.example.jitendrakumar.incometracker.database.LendDatabaseHelper;
import com.example.jitendrakumar.incometracker.fragments.date_time_fragment.DatePickerFragment;

import java.util.Calendar;


public class LendFragment extends Fragment {

    EditText etPersonName, etTakenAmount, etTakenReason;
    Button btnTakenSubmit, btnViewAllTakenData;
    TextView tvLendDate, tvHintLendDate;
    LendDatabaseHelper lendDatabaseHelper;
    public static final String TAG = "name";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate( R.layout.fragment_lend, container, false );
        lendDatabaseHelper = new LendDatabaseHelper( getContext());

        etPersonName = (EditText) view.findViewById( R.id.etPersonName );
        etTakenAmount = (EditText) view.findViewById( R.id.etTakenAmount );
        etTakenReason = (EditText) view.findViewById( R.id.etTakenReason);
        tvLendDate = (TextView) view.findViewById( R.id.tvLendDate );
        tvHintLendDate = (TextView)view.findViewById( R.id.tvHintLendDate );
        btnTakenSubmit = (Button) view.findViewById( R.id.btnTakenSubmit );
        btnViewAllTakenData = (Button) view.findViewById( R.id.btnViewAllTakenData );

        etPersonName.setHintTextColor(getResources().getColor(R.color.colorTexts));
        etTakenReason.setHintTextColor(getResources().getColor(R.color.colorTexts));
        tvLendDate.setHintTextColor(getResources().getColor(R.color.colorTexts));
        etTakenAmount.setHintTextColor(getResources().getColor(R.color.colorTexts));

        etTakenAmount.setTextColor( Color.parseColor("#00ff00"));
        tvLendDate.setTextColor( Color.parseColor("#00ff00"));
        etTakenReason.setTextColor( Color.parseColor("#00ff00"));
        etPersonName.setTextColor( Color.parseColor("#00ff00"));

        Calendar cal = Calendar.getInstance();
        int year = cal.get( Calendar.YEAR );
        int month = cal.get( Calendar.MONTH);
        int day = cal.get( Calendar.DAY_OF_MONTH );
        int hour = cal.get( Calendar.HOUR_OF_DAY );
        int minute = cal.get( Calendar.MINUTE );
        tvLendDate.setText( day+"/"+month+"/"+year );

        tvHintLendDate.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment newFragment = new DatePickerFragment(tvLendDate);
                newFragment.show(getFragmentManager(), "TimePicker");
            }
        } );



        btnTakenSubmit.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    String personName = etPersonName.getText().toString();
                    Log.d( TAG, "onClick: "+personName );
                    String takenAmount = etTakenAmount.getText().toString();
                    String takenDate =  tvLendDate.getText().toString();
                    String takenReason = etTakenReason.getText().toString();
                    if (personName.length()==0){
                        etPersonName.setError( "Person Name field is required!!!" );
                    }
                    if(takenAmount.length()==0){
                        etTakenAmount.setError( "Paying Amount field is required!!!" );
                    }
                    if(takenDate.length()==0)
                    {
                        Toast.makeText( getActivity(), "Date field is required!!!", Toast.LENGTH_SHORT ).show();
                    }
                    if(takenReason.length()==0){
                        etTakenReason.setError( "Reason field is required!!!" );
                    }
                    else if(personName.length()!=0 && takenAmount.length()!=0 && takenReason.length()!=0 && takenAmount.length()!=0 && takenDate.length()!=0){
                        boolean isInserted = lendDatabaseHelper.insertTakenData( personName, takenAmount ,takenReason,takenDate);
                        if (isInserted == true) {
                            Toast.makeText( getActivity(), "Data Saved to Taken DataBase.", Toast.LENGTH_SHORT ).show();

                        } else {
                            Toast.makeText( getActivity(), "Data is not Saved to Taken DataBase.", Toast.LENGTH_SHORT ).show();
                        }
                    }
                }
                catch (NullPointerException e)
                {
                    e.printStackTrace();
                }

            }
        } );

        showAllTakenData();
        return  view;
    }

    public void showAllTakenData(){
        btnViewAllTakenData.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Cursor res = lendDatabaseHelper.getAllTakenData();
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
                        buffer.append( "Taken Id : "+ res.getString( 0 )+"\n" );
                        buffer.append( "Person Name : "+ res.getString( 1 )+"\n" );
                        buffer.append( "Taken Amount : "+ res.getString( 2 )+"\n" );
                        buffer.append( "Taken Reason : "+ res.getString( 3 )+"\n" );
                        buffer.append( "Taken Date : "+ res.getString( 4 )+"\n\n" );

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