package com.example.jitendrakumar.incometracker.fragments;

import android.database.Cursor;
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

import com.example.jitendrakumar.incometracker.R;
import com.example.jitendrakumar.incometracker.database.ExpenseDatabaseHelper;
import com.example.jitendrakumar.incometracker.database.IncomeDatabaseHelper;

public class ExpenseReportFragment extends Fragment {
    EditText etExpenseFrom, etExpenseTo;
    ExpenseDatabaseHelper myExpenseDB;
    Button btnViewExpenseReport;
    private String id;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate( R.layout.fragment_expense_report, container, false );
        etExpenseFrom = (EditText)view.findViewById( R.id.etExpenseFrom);
        etExpenseTo = (EditText) view.findViewById( R.id.etExpenseTo);
        btnViewExpenseReport = (Button) view.findViewById( R.id.btnViewExpenseReport);
        myExpenseDB = new ExpenseDatabaseHelper( getContext());

        etExpenseFrom.setHintTextColor(getResources().getColor(R.color.colorTexts));
        etExpenseTo.setHintTextColor(getResources().getColor(R.color.colorTexts));
        showAllExpenseData();
        return view;
    }

    public void showAllExpenseData(){
        btnViewExpenseReport.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = getArguments();
                if(bundle != null)
                {
                    id = bundle.getString("id");
                }
                String expenseDateFrom = etExpenseFrom.getText().toString();
                String expenseDateTo = etExpenseTo.getText().toString();
                Cursor res = myExpenseDB.getAllExpenseReport(id,expenseDateFrom,expenseDateTo);
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
                        buffer.append( "Income Id : "+ res.getString( 0 )+"\n" );
                        buffer.append( "Income Type : "+ res.getString( 1 )+"\n" );
                        buffer.append( "Income Amount : "+ res.getString( 2 )+"\n" );
                        buffer.append( "Date : "+ res.getString( 3 )+"\n" );
                        buffer.append( "Time : "+ res.getString( 4 )+"\n\n" );
                        buffer.append( "User Id : "+ res.getString( 5 )+"\n\n" );
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
