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

public class IncomeReportFragment extends Fragment {
    EditText etIncomeFrom, etIncomeTo;
    IncomeDatabaseHelper myIncomeDB;
    Button btnViewIncomeReport;
    private String id;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate( R.layout.fragment_income_report, container, false );
        etIncomeFrom = (EditText)view.findViewById( R.id.etIncomeFrom);
        etIncomeTo = (EditText) view.findViewById( R.id.etIncomeTo);
        btnViewIncomeReport = (Button)view.findViewById( R.id.btnViewIncomeReport );

        etIncomeFrom.setHintTextColor(getResources().getColor(R.color.colorTexts));
        etIncomeTo.setHintTextColor(getResources().getColor(R.color.colorTexts));
        return view;
    }

    public void showAllIncomeData(){
        btnViewIncomeReport.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = getArguments();
                if(bundle != null)
                {
                    id = bundle.getString("id");
                }
                String dateFrom = etIncomeFrom.getText().toString();
                String dateTo = etIncomeTo.getText().toString();
                Cursor res = myIncomeDB.getAllIncomeReport(id,dateFrom,dateTo);
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
