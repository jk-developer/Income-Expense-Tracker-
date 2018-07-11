package com.example.jitendrakumar.incometracker.fragments;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jitendrakumar.incometracker.R;
import com.example.jitendrakumar.incometracker.activities.ExpenseReportActivity;
import com.example.jitendrakumar.incometracker.database.ExpenseDatabaseHelper;
import com.example.jitendrakumar.incometracker.fragments.date_time_fragment.DatePickerFragment;
import com.example.jitendrakumar.incometracker.fragments.date_time_fragment.TimePickerFragment;
import com.example.jitendrakumar.incometracker.helper.SessionManagement;

public class ExpenseFragment extends Fragment {
    EditText etExpenseType, etExpenseAmount;
    TextView tvExpenseDate, tvExpenseTime;
    ExpenseDatabaseHelper MyexpenseDB;
    Button btnExpenseSubmit, btnExpnenseViewAll, btnExpenseDate, btnExpenseTime;
    SessionManagement s;
    Spinner spinnerExpense;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate( R.layout.fragment_expense, container, false );
        MyexpenseDB = new ExpenseDatabaseHelper( getContext() );
        etExpenseType = (EditText) view.findViewById( R.id.etExpenseType );
        etExpenseAmount = (EditText) view.findViewById( R.id.etExpenseAmount );
        tvExpenseDate = (TextView) view.findViewById( R.id.tvExpenseDate );
        tvExpenseTime = (TextView) view.findViewById( R.id.tvExpenseTime );
        btnExpenseSubmit = (Button) view.findViewById( R.id.btnExpenseSubmit );
        btnExpnenseViewAll = (Button) view.findViewById( R.id.btnExpnenseViewAll );
        btnExpenseDate = (Button) view.findViewById( R.id.btnExpenseDate );
        btnExpenseTime = (Button) view.findViewById( R.id.btnExpenseTime );
        spinnerExpense = (Spinner) view.findViewById( R.id.spinnerExpense );
        s = new SessionManagement( getContext() );

        etExpenseType.setHintTextColor( getResources().getColor( R.color.colorTexts ) );
        etExpenseAmount.setHintTextColor( getResources().getColor( R.color.colorTexts ) );
        tvExpenseDate.setHintTextColor( getResources().getColor( R.color.colorTexts ) );
        tvExpenseTime.setHintTextColor( getResources().getColor( R.color.colorTexts ) );

        etExpenseAmount.setTextColor( Color.parseColor( "#00ff00" ) );
        tvExpenseDate.setTextColor( Color.parseColor( "#00ff00" ) );
        etExpenseType.setTextColor( Color.parseColor( "#00ff00" ) );
        tvExpenseTime.setTextColor( Color.parseColor( "#00ff00" ) );


        addDataInExpenseDB();
        viewAllExpenseData();
        spinnerDays();

        btnExpenseDate.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment newFragment = new DatePickerFragment( tvExpenseDate );
                newFragment.show( getFragmentManager(), "TimePicker" );
            }
        } );

        btnExpenseTime.setOnClickListener( new View.OnClickListener() {
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

                    String expenseType = etExpenseType.getText().toString();
                    String expenseAmount = etExpenseAmount.getText().toString();
                    String expenseDate = tvExpenseDate.getText().toString();
                    String expenseTime = tvExpenseTime.getText().toString();

                    if (expenseType.length() == 0) {
                        etExpenseType.setError( "Income Type is required!!!" );
                    }
                    if (expenseAmount.length() == 0) {
                        etExpenseAmount.setError( "Income Amount is required!!!" );
                    }
                    if (expenseDate.length() == 0) {
                        tvExpenseDate.setError( "Date field is required!!! " );
                    }
                    if (expenseTime.length() == 0) {
                        tvExpenseTime.setError( "Time field is required!!!" );
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

    public void spinnerDays() {
        //https://developer.android.com/guide/topics/ui/controls/spinner

        ArrayAdapter adapter = ArrayAdapter.createFromResource( getContext(),
                R.array.days_array, R.layout.single_day_item );

        adapter.setDropDownViewResource( R.layout.spinner_dropdown_item );
        spinnerExpense.setAdapter( adapter );

    }
}
