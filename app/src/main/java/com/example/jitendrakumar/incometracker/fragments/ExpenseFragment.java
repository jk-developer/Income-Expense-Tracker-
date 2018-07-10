package com.example.jitendrakumar.incometracker.fragments;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.jitendrakumar.incometracker.R;
import com.example.jitendrakumar.incometracker.activities.ExpenseReportActivity;
import com.example.jitendrakumar.incometracker.activities.IncomeReportActivity;
import com.example.jitendrakumar.incometracker.database.ExpenseDatabaseHelper;
import com.example.jitendrakumar.incometracker.database.IncomeDatabaseHelper;
import com.example.jitendrakumar.incometracker.helper.SessionManagement;

public class ExpenseFragment extends Fragment {
    EditText etExpenseType, etExpenseAmount, etExpenseDate, etExpenseTime;
    ExpenseDatabaseHelper MyexpenseDB;
    Button btnExpenseSubmit, btnExpnenseViewAll;
    SessionManagement s;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate( R.layout.fragment_expense, container, false );
        MyexpenseDB = new ExpenseDatabaseHelper( getContext() );
        etExpenseType = (EditText) view.findViewById( R.id.etExpenseType );
        etExpenseAmount = (EditText) view.findViewById( R.id.etExpenseAmount );
        etExpenseDate = (EditText) view.findViewById( R.id.etExpenseDate );
        etExpenseTime = (EditText) view.findViewById( R.id.etExpenseTime );
        btnExpenseSubmit = (Button) view.findViewById( R.id.btnExpenseSubmit );
        btnExpnenseViewAll = (Button) view.findViewById( R.id.btnExpnenseViewAll );
        s = new SessionManagement( getContext() );

        etExpenseType.setHintTextColor( getResources().getColor( R.color.colorTexts ) );
        etExpenseAmount.setHintTextColor( getResources().getColor( R.color.colorTexts ) );
        etExpenseDate.setHintTextColor( getResources().getColor( R.color.colorTexts ) );
        etExpenseTime.setHintTextColor( getResources().getColor( R.color.colorTexts ) );

        etExpenseAmount.setTextColor( Color.parseColor( "#00ff00" ) );
        etExpenseDate.setTextColor( Color.parseColor( "#00ff00" ) );
        etExpenseType.setTextColor( Color.parseColor( "#00ff00" ) );
        etExpenseTime.setTextColor( Color.parseColor( "#00ff00" ) );


        addDataInExpenseDB();
        viewAllExpenseData();

        return view;
    }

    public void addDataInExpenseDB() {
        btnExpenseSubmit.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try {

                    String expenseType = etExpenseType.getText().toString();
                    String expenseAmount = etExpenseAmount.getText().toString();
                    String expenseDate = etExpenseDate.getText().toString();
                    String expenseTime = etExpenseTime.getText().toString();

                    if (expenseType.length() == 0) {
                        etExpenseType.setError( "Income Type is required!!!" );
                    }
                    if (expenseAmount.length() == 0) {
                        etExpenseAmount.setError( "Income Amount is required!!!" );
                    }
                    if (expenseDate.length() == 0) {
                        etExpenseDate.setError( "Date field is required!!! " );
                    }
                    if (expenseTime.length() == 0) {
                        etExpenseTime.setError( "Time field is required!!!" );
                    } else {
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
