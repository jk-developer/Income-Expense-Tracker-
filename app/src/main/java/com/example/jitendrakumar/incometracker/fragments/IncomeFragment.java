package com.example.jitendrakumar.incometracker.fragments;

import android.database.Cursor;
import android.graphics.Color;
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
import com.example.jitendrakumar.incometracker.helper.SessionManagement;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class IncomeFragment extends Fragment {

    Pattern pattern;
    Matcher matcher;
    final String DATE_PATTERN = "(0?[1-9]|1[012]) [/.-] (0?[1-9]|[12][0-9]|3[01]) [/.-] ((19|20)\\d\\d)";
    SessionManagement sessonid;


    // TextView tvIncomeDate;
  //  DatePickerDialog.OnDateSetListener myDateSetListener;
     EditText etIncomeType, etIncomeAmount, etIncomeDate, etIncomeTime;
     Button btnIncomeSubmit,btnIncomeViewAll, btnIncomeUpdate, btnIncomeDelete;
     IncomeDatabaseHelper MyincomeDB;
     private String id;
     IncomeUpdateFragment incomeUpdateFragment;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate( R.layout.fragment_income, container, false );
        MyincomeDB = new IncomeDatabaseHelper( getContext());
        sessonid = new SessionManagement( getContext() );
        incomeUpdateFragment = new IncomeUpdateFragment();
        etIncomeType = (EditText) view.findViewById( R.id.etIncomeType );
        etIncomeAmount = (EditText) view.findViewById( R.id.etIncomeAmount);
        etIncomeDate = (EditText) view.findViewById( R.id.etIncomeDate );
        etIncomeTime = (EditText) view.findViewById( R.id.etIncomeTime );
        btnIncomeSubmit = (Button)view.findViewById( R.id.btnIncomeSubmit );
        btnIncomeViewAll = (Button)view.findViewById( R.id.btnIncomeViewAll );
        btnIncomeUpdate = (Button)view.findViewById( R.id.btnIncomeUpdate);
        btnIncomeDelete = (Button)view.findViewById( R.id.btnIncomeDelete );

        etIncomeType.setHintTextColor(getResources().getColor(R.color.colorTexts));
        etIncomeAmount.setHintTextColor(getResources().getColor(R.color.colorTexts));
        etIncomeDate.setHintTextColor(getResources().getColor(R.color.colorTexts));
        etIncomeTime.setHintTextColor(getResources().getColor(R.color.colorTexts));
        etIncomeDate.setTextColor( Color.parseColor("#00ff00"));
        etIncomeAmount.setTextColor( Color.parseColor("#00ff00"));
        etIncomeType.setTextColor( Color.parseColor("#00ff00"));
        etIncomeTime.setTextColor( Color.parseColor("#00ff00"));

        addDataInIncomeDB();
        viewAllIncomeData();
        updateIncome();

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

                    try {
                        String incomeType = etIncomeType.getText().toString();
                        String incomeAmount = etIncomeAmount.getText().toString();
                        String incomeDate =  etIncomeDate.getText().toString();
                        String incomeTime = etIncomeTime.getText().toString();
                        if(incomeType.length() == 0)
                        {
                            etIncomeType.setError( "Income Type is required!!!" );
                        }
                        if(incomeAmount.length() == 0)
                        {
                            etIncomeAmount.setError( "Income Amount is required!!!" );
                        }
                        if(incomeDate.length() == 0){
                            etIncomeDate.setError( "Date field is required!!! " );
                        }
                        if(incomeTime.length()==0)
                        {
                            etIncomeTime.setError( "Time field is required!!!" );
                        }
                        else {
                            boolean isInserted = MyincomeDB.insertIncomeData( incomeType, incomeAmount , incomeDate,incomeTime);
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

    public boolean validateDate(EditText date) {

        matcher = pattern.matcher(date.getText().toString());

        if(matcher.matches()){
            matcher.reset();

            if(matcher.find()){
                String day = matcher.group(1);
                String month = matcher.group(2);
                int year = Integer.parseInt(matcher.group(3));

                if (day.equals("31") &&
                        (month.equals("4") || month .equals("6") || month.equals("9") ||
                                month.equals("11") || month.equals("04") || month .equals("06") ||
                                month.equals("09"))) {
                    return false; // only 1,3,5,7,8,10,12 has 31 days
                }

                else if (month.equals("2") || month.equals("02")) {
                    //leap year
                    if(year % 4==0){
                        if(day.equals("30") || day.equals("31")){
                            return false;
                        }
                        else{
                            return true;
                        }
                    }
                    else{
                        if(day.equals("29")||day.equals("30")||day.equals("31")){
                            return false;
                        }
                        else{
                            return true;
                        }
                    }
                }

                else{
                    return true;
                }
            }

            else{
                return false;
            }
        }
        else{
            return false;
        }

    }

    public void updateIncome()
    {
        btnIncomeUpdate.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction fragmentTransaction  = getFragmentManager().beginTransaction();
                fragmentTransaction.replace( R.id.fragment_container, new IncomeUpdateFragment());
                fragmentTransaction.addToBackStack( null );
                fragmentTransaction.commit();

               Toast.makeText( getActivity(), "Now fill the Fields to be updated!!!", Toast.LENGTH_SHORT ).show();
              boolean isUpdated =  MyincomeDB.updateIncomeData(incomeUpdateFragment.getIncomeId() ,etIncomeType.getText().toString(), etIncomeAmount.getText().toString(),etIncomeDate.getText().toString(), etIncomeTime.getText().toString());
              if (isUpdated==true)
              {
                  Toast.makeText( getActivity(), "Income Data of "+incomeUpdateFragment.getIncomeId()+" is updated successfully!!!", Toast.LENGTH_SHORT ).show();
              }
              else
              {
                  Toast.makeText( getActivity(), "Income Data of "+incomeUpdateFragment.getIncomeId()+" is not updated successfully!!!", Toast.LENGTH_SHORT ).show();
              }
            }
        } );
    }


}
