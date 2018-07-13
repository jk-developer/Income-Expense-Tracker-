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
import com.example.jitendrakumar.incometracker.database.ExpenseDatabaseHelper;
import com.example.jitendrakumar.incometracker.fragments.date_time_fragment.DatePickerFragment;
import com.example.jitendrakumar.incometracker.fragments.date_time_fragment.TimePickerFragment;
import com.example.jitendrakumar.incometracker.helper.SessionManagement;

import java.util.Calendar;

public class AddExpenseFragment extends Fragment {
    EditText  etExpenseAmount;
    TextView tvExpenseDate, tvExpenseTime, tvHintExpenseDate,tvExpenseType,tvExpenseInput, tvExpenseHintTime;
    ExpenseDatabaseHelper MyexpenseDB;
    Button btnExpenseSubmit, btnExpnenseViewAll, btnExpnenseBarchart;
    SessionManagement s;
    private CharSequence expense[] = {"Food", "Leisure","Transport","Clothes", "Travel","Health","Hobbies","Gifts","Household",
    "Groceries","Gadgets","Kids", "Loans", "Education","Holidays","Savings","Beauty","Sports","Mobile","Other"};

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate( R.layout.fragment_add_expense, container, false );
        MyexpenseDB = new ExpenseDatabaseHelper( getContext() );
        tvExpenseType = (TextView) view.findViewById( R.id.tvExpenseType );
        tvExpenseInput = (TextView) view.findViewById( R.id.tvExpenseInput );
        etExpenseAmount = (EditText) view.findViewById( R.id.etExpenseAmount );
        tvExpenseDate = (TextView) view.findViewById( R.id.tvExpenseDate );
        tvExpenseTime = (TextView) view.findViewById( R.id.tvExpenseTime );
        btnExpenseSubmit = (Button) view.findViewById( R.id.btnExpenseSubmit );
        btnExpnenseViewAll = (Button) view.findViewById( R.id.btnExpnenseViewAll );
        btnExpnenseBarchart = (Button)view.findViewById( R.id.btnExpnenseBarchart );
        tvHintExpenseDate = (TextView) view.findViewById( R.id.tvHintExpenseDate );
        tvExpenseHintTime = (TextView) view.findViewById( R.id.tvExpenseHintTime );
        s = new SessionManagement( getContext() );

        Calendar c = Calendar.getInstance();
        int year = c.get( Calendar.YEAR );
        int month = c.get( Calendar.MONTH);
        int day = c.get( Calendar.DAY_OF_MONTH );
        tvExpenseDate.setText( day+"/"+month+"/"+year );

        tvExpenseType.setHintTextColor( getResources().getColor( R.color.colorTexts ) );
        tvExpenseInput.setHintTextColor( getResources().getColor( R.color.colorTexts ) );
        etExpenseAmount.setHintTextColor( getResources().getColor( R.color.colorTexts ) );
        tvExpenseDate.setHintTextColor( getResources().getColor( R.color.colorTexts ) );
        tvExpenseTime.setHintTextColor( getResources().getColor( R.color.colorTexts ) );

        etExpenseAmount.setTextColor( Color.parseColor( "#00ff00" ) );
        tvExpenseDate.setTextColor( Color.parseColor( "#00ff00" ) );
        tvExpenseType.setTextColor( Color.parseColor( "#00ff00" ) );
        tvExpenseInput.setTextColor( Color.parseColor( "#00ff00" ) );
        tvExpenseTime.setTextColor( Color.parseColor( "#00ff00" ) );



        addDataInExpenseDB();
        viewAllExpenseData();

        btnExpnenseBarchart.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent( getActivity(), ExpenseBarchartActivity.class );
                startActivity( i );
            }
        } );

        tvExpenseType.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle( "Select Expense Category" );
                builder.setItems( expense, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
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
                            tvExpenseType.setError( "Select some expense category!!!" );
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
                        boolean isInserted = MyexpenseDB.insertExpenseData( expenseType, expenseAmount, expenseDate, expenseTime );
                        if (isInserted == true) {
                            Toast.makeText( getActivity(), "Data Saved to Expense DataBase.", Toast.LENGTH_SHORT ).show();

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

    public void viewAllExpenseData() {
        btnExpnenseViewAll.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent( getActivity(), ExpenseReportActivity.class );
                startActivity( i );

            }
        } );
    }


}
