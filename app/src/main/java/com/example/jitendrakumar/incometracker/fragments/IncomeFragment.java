package com.example.jitendrakumar.incometracker.fragments;

import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.jitendrakumar.incometracker.R;
import com.example.jitendrakumar.incometracker.activities.UserSessionManagement;
import com.example.jitendrakumar.incometracker.database.DatabaseHelper;
import com.example.jitendrakumar.incometracker.database.IncomeDatabaseHelper;

public class IncomeFragment extends Fragment {

   // TextView tvIncomeDate;
  //  DatePickerDialog.OnDateSetListener myDateSetListener;
     EditText etIncomeType, etIncomeAmount, etIncomeDate, etIncomeTime;
     Button btnIncomeSubmit,btnIncomeViewAll;
     IncomeDatabaseHelper MyincomeDB;
     private String id;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate( R.layout.fragment_income, container, false );
        MyincomeDB = new IncomeDatabaseHelper( getContext());
        etIncomeType = (EditText) view.findViewById( R.id.etIncomeType );
        etIncomeAmount = (EditText) view.findViewById( R.id.etIncomeAmount);
        etIncomeDate = (EditText) view.findViewById( R.id.etIncomeDate );
        etIncomeTime = (EditText) view.findViewById( R.id.etIncomeTime );
        btnIncomeSubmit = (Button)view.findViewById( R.id.btnIncomeSubmit );
        btnIncomeViewAll = (Button)view.findViewById( R.id.btnIncomeViewAll );

        etIncomeType.setHintTextColor(getResources().getColor(R.color.colorTexts));
        etIncomeAmount.setHintTextColor(getResources().getColor(R.color.colorTexts));
        etIncomeDate.setHintTextColor(getResources().getColor(R.color.colorTexts));
        etIncomeTime.setHintTextColor(getResources().getColor(R.color.colorTexts));

        addDataInIncomeDB();
        viewAllIncomeData();

     /*   tvIncomeDate = (TextView) view.findViewById( R.id.etIncomeDate );
        tvIncomeDate.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get( Calendar.YEAR );
                int month = cal.get( Calendar.MONTH );
                int day = cal.get( Calendar.DAY_OF_MONTH );

                DatePickerDialog dialog = new DatePickerDialog( getContext(), android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        myDateSetListener,
                        year, month, day );
                dialog.getWindow().setBackgroundDrawable( new ColorDrawable( Color.TRANSPARENT ) );
                dialog.show();
            }
        } );

        myDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = month + 1;
                String date = dayOfMonth + "/" + month + "/" + year;
                tvIncomeDate.setText( date );
            }
        };
       */


        return view;
    }

    public void addDataInIncomeDB() {
        btnIncomeSubmit.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = getArguments();
                if(bundle != null)
                {
                   id = bundle.getString("id");
                }
                if(id != null )
                {
                    try {
                        String incomeType = etIncomeType.getText().toString();
                        String incomeAmount = etIncomeAmount.getText().toString();
                        String incomeDate =  etIncomeDate.getText().toString();
                        String incomeTime = etIncomeTime.getText().toString();
                        if(incomeType.length() == 0)
                        {
                            etIncomeType.setError( "Please enter some income type." );
                        }
                        else
                        {
                            boolean isInserted = MyincomeDB.insertIncomeData( incomeType, incomeAmount , incomeDate,incomeTime , id);
                            if (isInserted == true) {
                                Toast.makeText( getActivity(), "Data Saved to Income DataBase.", Toast.LENGTH_SHORT ).show();

                            } else {
                                Toast.makeText( getActivity(), "Data is not Saved to Income DataBase.", Toast.LENGTH_SHORT ).show();
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
                    Toast.makeText( getActivity(), "Please First Login to Add the Income Data.", Toast.LENGTH_SHORT ).show();
                }

            }
        } );


    }

    public void viewAllIncomeData(){
        btnIncomeViewAll.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Cursor res = MyincomeDB.getAllIncomeData();
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
