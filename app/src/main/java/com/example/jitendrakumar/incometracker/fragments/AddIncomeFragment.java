package com.example.jitendrakumar.incometracker.fragments;

import android.content.DialogInterface;
import android.content.Intent;
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
import com.example.jitendrakumar.incometracker.activities.IncomeReportActivity;
import com.example.jitendrakumar.incometracker.database.IncomeDatabaseHelper;
import com.example.jitendrakumar.incometracker.fragments.date_time_fragment.DatePickerFragment;
import com.example.jitendrakumar.incometracker.fragments.date_time_fragment.TimePickerFragment;
import com.example.jitendrakumar.incometracker.models.IncomeData;
import com.example.jitendrakumar.incometracker.helper.SessionManagement;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AddIncomeFragment extends Fragment {

    Pattern pattern;
    Matcher matcher;
    final String DATE_PATTERN = "(0?[1-9]|1[012]) [/.-] (0?[1-9]|[12][0-9]|3[01]) [/.-] ((19|20)\\d\\d)";
    SessionManagement sessonid;
    ArrayList<IncomeData> arrayList = new ArrayList<>();
    IncomeData incomeData;
    public static final String TAG = "res";

    // TextView tvIncomeDate;
    EditText etIncomeAmount, etIncomeDescription;
    TextView tvIncomeDate, tvIncomeTime, tvIncomeHintDate, tvIncomeHintTime, tvIncomeType, tvIncomeInput;
    Button btnIncomeSubmit;
    IncomeDatabaseHelper MyincomeDB;
    private int year, month, day, hour, minute;
    private CharSequence income[] = {"Regular Salary", "Buissness Profits", "Rental Income", "Savings", "Gifts", "Pocket Money", " Investments ", "Governmental grants", "Retirement Income",
            "Bonus", "Other"};


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate( R.layout.fragment_add_income, container, false );
        MyincomeDB = new IncomeDatabaseHelper( getContext() );
        sessonid = new SessionManagement( getContext() );
        tvIncomeType = (TextView) view.findViewById( R.id.tvIncomeType );
        tvIncomeInput = (TextView) view.findViewById( R.id.tvIncomeInput );
        etIncomeAmount = (EditText) view.findViewById( R.id.etIncomeAmount );
        etIncomeDescription = (EditText) view.findViewById( R.id.etIncomeDescription );
        tvIncomeDate = (TextView) view.findViewById( R.id.tvIncomeDate );
        tvIncomeTime = (TextView) view.findViewById( R.id.tvIncomeTime );
        btnIncomeSubmit = (Button) view.findViewById( R.id.btnIncomeSubmit );
        tvIncomeHintDate = (TextView) view.findViewById( R.id.tvHintIncomeDate );
        tvIncomeHintTime = (TextView) view.findViewById( R.id.tvExpenseHintTime );
        tvIncomeHintDate = (TextView) view.findViewById( R.id.tvHintIncomeDate );
        tvIncomeHintTime = (TextView) view.findViewById( R.id.tvIncomeHintTime );

        Calendar cal = Calendar.getInstance();
        int year = cal.get( Calendar.YEAR );
        int month = cal.get( Calendar.MONTH );
        int day = cal.get( Calendar.DAY_OF_MONTH );
        int hour = cal.get( Calendar.HOUR_OF_DAY );
        int minute = cal.get( Calendar.MINUTE );
        String Date = "";
        if ((month + 1) <= 9 && day <= 9) {
            Date = "0" + day + "/0" + (month + 1) + "/" + year;
        }
        if ((month + 1) <= 9 && day > 9) {
            Date = day + "/0" + (month + 1) + "/" + year;
        }
        if ((month + 1) > 9 && day <= 9) {
            Date = "0" + day + "/" + (month + 1) + "/" + year;
        } else {
            Date = day + "/" + (month + 1) + "/" + year;
        }

        tvIncomeDate.setText( Date );
        tvIncomeTime.setText( hour + ":" + minute );

        tvIncomeType.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder builder = new AlertDialog.Builder( getActivity() );
                builder.setTitle( "Select Expense Category" );
                builder.setItems( income, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        tvIncomeInput.setText( income[which].toString() );
                    }
                } );

                builder.setNegativeButton( "CANCEL", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        builder.setCancelable( true );
                    }
                } );

                builder.setPositiveButton( "OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (which == -1) {

                        } else {
                            tvIncomeInput.setText( income[which].toString() );

                        }


                    }
                } );
                AlertDialog alertDialog = builder.create();
                alertDialog.show();

            }
        } );

        tvIncomeType.setHintTextColor( getResources().getColor( R.color.colorTexts ) );
        tvIncomeInput.setHintTextColor( getResources().getColor( R.color.colorTexts ) );
        etIncomeAmount.setHintTextColor( getResources().getColor( R.color.colorTexts ) );
        etIncomeDescription.setHintTextColor( getResources().getColor( R.color.colorTexts ) );
        tvIncomeDate.setHintTextColor( getResources().getColor( R.color.colorTexts ) );
        tvIncomeTime.setHintTextColor( getResources().getColor( R.color.colorTexts ) );

        tvIncomeDate.setTextColor( Color.parseColor( "#00ff00" ) );
        etIncomeAmount.setTextColor( Color.parseColor( "#00ff00" ) );
        tvIncomeType.setTextColor( Color.parseColor( "#00ff00" ) );
        tvIncomeTime.setTextColor( Color.parseColor( "#00ff00" ) );
        tvIncomeInput.setTextColor( Color.parseColor( "#00ff00" ) );

        addDataInIncomeDB();

        tvIncomeHintTime.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment newFragment = new TimePickerFragment( tvIncomeTime );
                newFragment.show( getFragmentManager(), "TimePicker" );


            }
        } );

        tvIncomeHintDate.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment newFragment = new DatePickerFragment( tvIncomeDate );
                newFragment.show( getFragmentManager(), "DatePicker" );

            }
        } );

        return view;
    }

    public void addDataInIncomeDB() {
        btnIncomeSubmit.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    String incomeType = tvIncomeInput.getText().toString();
                    String incomeAmount = etIncomeAmount.getText().toString();
                    String incomeDate = tvIncomeDate.getText().toString();
                    String incomeTime = tvIncomeTime.getText().toString();
                    String incomeDesc = etIncomeDescription.getText().toString();
                    // Extracting year month and day integer value from the Date String DD/MM/YYYY
                    String[] dateParts = incomeDate.toString().split( "/" );
                    try {
                        year = safeParseInt( dateParts[2] );
                        month = safeParseInt( dateParts[1] );
                        day = safeParseInt( dateParts[0] );
                    } catch (Exception e) {
                        Toast.makeText( getActivity(), "Error in parsing Date", Toast.LENGTH_SHORT ).show();
                    }
                    String timeStr = incomeTime.toString();
                    String[] timeParts = timeStr.split( ":" );
                    try {
                        hour = safeParseInt( timeParts[0] );
                        minute = safeParseInt( timeParts[1] );
                    } catch (Exception e) {
                        Toast.makeText( getActivity(), "Error in parsing Time", Toast.LENGTH_SHORT ).show();
                    }

                    if (incomeAmount.length() == 0) {
                        etIncomeAmount.setError( "Income Amount is required!!!" );
                    }
                    if (incomeDate.length() == 0) {
                        tvIncomeDate.setError( "Date field is required!!! " );
                    }
                    if (incomeTime.length() == 0) {
                        tvIncomeTime.setError( "Time field is required!!!" );
                    } else {
                        boolean isInserted = MyincomeDB.insertIncomeData( incomeType, Float.parseFloat( incomeAmount ), year, month, day, hour, minute, incomeDesc );
                        if (isInserted == true) {
                            Toast.makeText( getActivity(), "Data Saved to Income DataBase.", Toast.LENGTH_SHORT ).show();
                            Intent in = new Intent( getContext(), IncomeReportActivity.class );
                            startActivity( in );

                        } else {
                            Toast.makeText( getActivity(), "Data is not Saved to Income DataBase.", Toast.LENGTH_SHORT ).show();
                        }
                    }

                } catch (NullPointerException e) {
                    e.printStackTrace();
                }

            }
        } );

    }

    public int safeParseInt(String number) throws Exception {
        if (number != null) {
            return Integer.parseInt( number.trim() );
        } else {
            throw new NullPointerException( "Date string is invalid" );
        }
    }
}
