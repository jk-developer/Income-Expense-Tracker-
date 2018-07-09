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
import android.widget.Toast;

import com.example.jitendrakumar.incometracker.R;
import com.example.jitendrakumar.incometracker.database.ExpenseDatabaseHelper;
import com.example.jitendrakumar.incometracker.database.IncomeDatabaseHelper;

public class ExpenseFragment extends Fragment {
    EditText etExpenseType, etExpenseAmount, etExpenseDate, etExpenseTime;
    ExpenseDatabaseHelper MyexpenseDB;
    Button btnExpenseSubmit, btnExpnenseViewAll;
    public String user_id;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate( R.layout.fragment_expense, container, false );
        MyexpenseDB = new ExpenseDatabaseHelper( getContext());
        etExpenseType = (EditText) view.findViewById( R.id.etExpenseType );
        etExpenseAmount = (EditText) view.findViewById( R.id.etExpenseAmount);
        etExpenseDate = (EditText) view.findViewById( R.id.etExpenseDate );
        etExpenseTime = (EditText) view.findViewById( R.id.etExpenseTime );
        btnExpenseSubmit = (Button) view.findViewById( R.id.btnExpenseSubmit);
        btnExpnenseViewAll = (Button) view.findViewById( R.id.btnExpnenseViewAll);

        etExpenseType.setHintTextColor(getResources().getColor(R.color.colorTexts));
        etExpenseAmount.setHintTextColor(getResources().getColor(R.color.colorTexts));
        etExpenseDate.setHintTextColor(getResources().getColor(R.color.colorTexts));
        etExpenseTime.setHintTextColor(getResources().getColor(R.color.colorTexts));

        addDataInExpenseDB();
        viewAllExpenseData();

        return view;
    }

    public void addDataInExpenseDB() {
        btnExpenseSubmit.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = getArguments();
                if(bundle != null)
                {
                    user_id = bundle.getString("id");
                }
                if(user_id != null)
                {

                    try {

                        String expenseType = etExpenseType.getText().toString();
                        String expenseAmount = etExpenseAmount.getText().toString();
                        String expenseDate =  etExpenseDate.getText().toString();
                        String expenseTime = etExpenseTime.getText().toString();
                        if(expenseType.length() == 0)
                        {
                            etExpenseType.setError( "Please enter some income type." );
                        }
                        else
                        {
                            boolean isInserted = MyexpenseDB.insertExpenseData( expenseType, expenseAmount, expenseDate, expenseTime , user_id);
                            if (isInserted == true) {
                                Toast.makeText( getActivity(), "Data Saved to Expense DataBase.", Toast.LENGTH_SHORT ).show();

                            } else {
                                Toast.makeText( getActivity(), "Data is not Saved to Expense DataBase.", Toast.LENGTH_SHORT ).show();
                            }
                        }
                    }
                    catch (NullPointerException e)
                    {
                        e.printStackTrace();
                    }
                }
                else
                {
                    Toast.makeText( getActivity(), "Please First Login to Add the Expense Data.", Toast.LENGTH_SHORT ).show();
                }



            }
        } );


    }

    public void viewAllExpenseData(){
        btnExpnenseViewAll.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Cursor res = MyexpenseDB.getAllExpenseData();
                if(res.getCount() == 0)
                {
                    // Show message
                    showMessage( "Error", "Nothing Found in Expense DB" );

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
