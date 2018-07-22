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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jitendrakumar.incometracker.R;
import com.example.jitendrakumar.incometracker.activities.ExpenseBarchartActivity;
import com.example.jitendrakumar.incometracker.activities.ExpenseReportActivity;
import com.example.jitendrakumar.incometracker.activities.IncomeReportActivity;
import com.example.jitendrakumar.incometracker.activities.MainActivity;
import com.example.jitendrakumar.incometracker.database.ExpenseDatabaseHelper;
import com.example.jitendrakumar.incometracker.fragments.date_time_fragment.DatePickerFragment;
import com.example.jitendrakumar.incometracker.fragments.date_time_fragment.TimePickerFragment;
import com.example.jitendrakumar.incometracker.helper.SessionManagement;

import java.util.Calendar;

public class AddExpenseFragment extends Fragment {
    EditText  etExpenseAmount, etExpenseDescription;
    TextView tvExpenseDate, tvExpenseTime, tvHintExpenseDate, tvExpenseType, tvExpenseHintType, tvExpenseHintTime;
    ExpenseDatabaseHelper MyexpenseDB;
    Button btnExpenseSubmit;
    private int eyear, emonth, eday, ehour, eminute;
    SessionManagement s;
    private CharSequence expense[] = {"Food", "Leisure","Transport", "Medicines", "House Rent", "Maintenace", "Clothes", "Travel","Health","Hobbies","Gifts","Household",
    "Groceries","Gadgets","Kids", "Loans", "Education","Holidays","Savings","Beauty","Sports","Mobile","Other"};

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate( R.layout.fragment_add_expense, container, false );
        MyexpenseDB = new ExpenseDatabaseHelper( getContext() );
        tvExpenseType = (TextView) view.findViewById( R.id.tvExpenseType );
        tvExpenseHintType  = (TextView) view.findViewById( R.id.tvExpenseHintType );
        etExpenseAmount = (EditText) view.findViewById( R.id.etExpenseAmount );
        etExpenseDescription = (EditText)view.findViewById( R.id.etExpenseDescription );
        tvExpenseDate = (TextView) view.findViewById( R.id.tvExpenseDate );
        tvExpenseTime = (TextView) view.findViewById( R.id.tvExpenseTime );
        btnExpenseSubmit = (Button) view.findViewById( R.id.btnExpenseSubmit );
        tvHintExpenseDate = (TextView) view.findViewById( R.id.tvHintExpenseDate );
        tvExpenseHintTime = (TextView) view.findViewById( R.id.tvExpenseHintTime );
        s = new SessionManagement( getContext() );

        Calendar c = Calendar.getInstance();
        int year = c.get( Calendar.YEAR );
        int month = c.get( Calendar.MONTH);
        int day = c.get( Calendar.DAY_OF_MONTH );
        int hour = c.get( Calendar.HOUR_OF_DAY );
        int minute = c.get( Calendar.MINUTE );
        String Date = "";
        if((month+1)<=9 && day<=9)
        {
            Date = "0"+day +"/0"+(month+1) +"/"+year ;
        }
        if((month+1)<=9 && day>9){
            Date = day +"/0"+(month+1)+"/"+year ;
        }
        if((month+1)>9 && day<=9){
            Date = "0"+day +"/"+(month+1) +"/"+year ;
        }
        else {
            Date = day +"/"+(month+1) +"/"+year ;
        }

        tvExpenseDate.setText(Date);
        tvExpenseTime.setText( hour+":"+minute );

        tvExpenseType.setHintTextColor( getResources().getColor( R.color.colorTexts ) );
        tvExpenseHintType.setHintTextColor( getResources().getColor( R.color.colorTexts ) );
        etExpenseAmount.setHintTextColor( getResources().getColor( R.color.colorTexts ) );
        tvExpenseDate.setHintTextColor( getResources().getColor( R.color.colorTexts ) );
        tvExpenseTime.setHintTextColor( getResources().getColor( R.color.colorTexts ) );

        etExpenseAmount.setTextColor( Color.parseColor( "#00ff00" ) );
        tvExpenseDate.setTextColor( Color.parseColor( "#00ff00" ) );
        tvExpenseType.setTextColor( Color.parseColor( "#00ff00" ) );
        tvExpenseHintType.setTextColor( Color.parseColor( "#00ff00" ) );
        tvExpenseTime.setTextColor( Color.parseColor( "#00ff00" ) );

        addDataInExpenseDB();

        tvExpenseHintType.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle( "Select Expense Category" );
                builder.setItems( expense, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        tvExpenseType.setText( expense[which].toString().trim());
                       Toast.makeText( getContext(), ""+expense[which],Toast.LENGTH_SHORT ).show();
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
                        if(which==-1)
                        {
                            Toast.makeText( getContext(), "Select some  expense category", Toast.LENGTH_SHORT ).show();
                          //  tvExpenseType.setError( "Select some expense category!!!" );
                        }else {
                            tvExpenseType.setText( expense[which].toString());

                        }


                    }
                } );
                AlertDialog alertDialog = builder.create();
                alertDialog.show();

            }
        } );

        tvHintExpenseDate.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment newFragment = new DatePickerFragment( tvExpenseDate );
                newFragment.show( getFragmentManager(), "DatePicker" );
            }
        } );

        tvExpenseHintTime.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment newFragment = new TimePickerFragment( tvExpenseTime );
                newFragment.show( getFragmentManager(), "TimePicker" );
            }
        } );


        return view;
    }

    public void addDataInExpenseDB() {
        btnExpenseSubmit.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try {

                    String expenseType = tvExpenseType.getText().toString();
                    String expenseAmount = etExpenseAmount.getText().toString();
                    String expenseDate = tvExpenseDate.getText().toString();
                    String expenseTime = tvExpenseTime.getText().toString();
                    String expenseDesc = etExpenseDescription.getText().toString();

                    // Extracting year month and day integer value from the Date String DD/MM/YYYY
                    String[]dateParts = expenseDate.toString().split("/");
                    try {
                        eyear = safeParseInt(dateParts[2]);
                        emonth = safeParseInt(dateParts[1]);
                        eday = safeParseInt(dateParts[0]);
                    } catch (Exception e) {
                        Toast.makeText( getActivity(), "Error in parsing Date", Toast.LENGTH_SHORT ).show();
                    }
                    String timeStr = expenseTime.toString();
                    String[] timeParts = timeStr.split( ":" );
                    try {
                        ehour = safeParseInt( timeParts[0] );
                        eminute = safeParseInt( timeParts[1] );
                    }catch (Exception e)
                    {
                        Toast.makeText( getActivity(), "Error in parsing Time", Toast.LENGTH_SHORT ).show();
                    }


                    if (expenseType.length() == 0) {
                        tvExpenseType.setError( "Expense Type is required!!!" );
                    }
                    else if (expenseAmount.length() == 0) {
                        etExpenseAmount.setError( "Expense Amount is required!!!" );
                    }
                    else if (expenseDate.length() == 0) {
                        Toast.makeText( getActivity(),"Date field is required!!! ", Toast.LENGTH_SHORT ).show();
                    }
                    else if (expenseTime.length() == 0) {
                        Toast.makeText( getActivity(),"Time field is required!!! ", Toast.LENGTH_SHORT ).show();
                    }
                    else {
                        boolean isInserted = MyexpenseDB.insertExpenseData( expenseType, Float.parseFloat( expenseAmount ),eyear, emonth, eday, ehour,eminute, expenseDesc );
                        if (isInserted == true) {
                            Toast.makeText( getActivity(), "Data Saved to Expense DataBase.", Toast.LENGTH_SHORT ).show();
                            Intent i = new Intent( getActivity(), ExpenseReportActivity.class );
                            startActivity( i );

                        } else {
                            Toast.makeText( getActivity(), "Data is not Saved to Expense DataBase.", Toast.LENGTH_SHORT ).show();
                        }
                    }
                } catch (NullPointerException e) {
                    e.printStackTrace();
                }
            }

        } );


    }

    private int safeParseInt(String number) throws Exception {
        if(number != null) {
            return Integer.parseInt(number.trim());
        } else {
            throw new NullPointerException("Date string is invalid");
        }
    }


}
