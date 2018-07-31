package com.example.jitendrakumar.incometracker.activities;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jitendrakumar.incometracker.R;
import com.example.jitendrakumar.incometracker.database.LendDatabaseHelper;
import com.example.jitendrakumar.incometracker.fragments.date_time_fragment.DatePickerFragment;

import java.util.Calendar;

public class AddLendActivity extends AppCompatActivity  implements DatePickerDialog.OnDateSetListener {


    EditText etPersonName, etTakenAmount, etTakenReason;
    Button btnTakenSubmit, btnViewAllTakenData;
    TextView tvLendDate, tvHintLendDate;
    LendDatabaseHelper lendDatabaseHelper;
    public static final String TAG = "name";
    private   int  lendyr, lendmth , lenddy;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_add_lend );

        getSupportActionBar().setTitle( "Add Lend" );
        getSupportActionBar().setDisplayHomeAsUpEnabled( true );

            lendDatabaseHelper = new LendDatabaseHelper( AddLendActivity.this);

            etPersonName = (EditText)findViewById( R.id.etPersonName );
            etTakenAmount = (EditText)findViewById( R.id.etTakenAmount );
            etTakenReason = (EditText)findViewById( R.id.etTakenReason);
            tvLendDate = (TextView)findViewById( R.id.tvLendDate );
            tvHintLendDate = (TextView)findViewById( R.id.tvHintLendDate );
            btnTakenSubmit = (Button)findViewById( R.id.btnTakenSubmit );

            etPersonName.setHintTextColor(getResources().getColor(R.color.colorTexts));
            etTakenReason.setHintTextColor(getResources().getColor(R.color.colorTexts));
            tvLendDate.setHintTextColor(getResources().getColor(R.color.colorTexts));
            etTakenAmount.setHintTextColor(getResources().getColor(R.color.colorTexts));

            etTakenAmount.setTextColor( Color.parseColor("#00ff00"));
            tvLendDate.setTextColor( Color.parseColor("#00ff00"));
            etTakenReason.setTextColor( Color.parseColor("#00ff00"));
            etPersonName.setTextColor( Color.parseColor("#00ff00"));

            Calendar cal = Calendar.getInstance();
            int year = cal.get( Calendar.YEAR );
            int month = cal.get( Calendar.MONTH);
            int day = cal.get( Calendar.DAY_OF_MONTH );

            String Date = "";
            if((month+1)<=9 && day<=9)
            {
                Date = "0"+day +"/0"+(month+1) +"/"+year ;
            }
            if((month+1)<=9 && day>9){
                Date = day +"/0"+(month+1) +"/"+year ;
            }
            if((month+1)>9 && day<=9){
                Date = "0"+day +"/"+(month+1) +"/"+year ;
            }
            else {
                Date = day +"/"+(month+1) +"/"+year ;
            }

            tvLendDate.setText(Date);
          final DatePickerDialog datePickerDialog = new DatePickerDialog(this, AddLendActivity.this, year, month, day);


        tvHintLendDate.setOnClickListener( new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    datePickerDialog.show();
                }
            } );



            btnTakenSubmit.setOnClickListener( new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        String personName = etPersonName.getText().toString();
                        Log.d( TAG, "onClick: "+personName );
                        String takenAmount = etTakenAmount.getText().toString();
                        String takenDate =  tvLendDate.getText().toString();
                        String takenReason = etTakenReason.getText().toString();

                        String[]dateParts = takenDate.toString().split("/");
                        try {
                            lendyr = safeParseInt(dateParts[2]);
                            lendmth = safeParseInt(dateParts[1]);
                            lenddy = safeParseInt(dateParts[0]);
                        } catch (Exception e) {
                            Toast.makeText(AddLendActivity.this, "Error in parsing Date", Toast.LENGTH_SHORT ).show();
                    }

                        if (personName.length()==0){
                            etPersonName.setError( "Person Name field is required!!!" );
                        }
                        if(takenAmount.length()==0){
                            etTakenAmount.setError( "Paying Amount field is required!!!" );
                        }
                        if(takenDate.length()==0)
                        {
                            Toast.makeText(AddLendActivity.this, "Date field is required!!!", Toast.LENGTH_SHORT ).show();
                        }
                        if(takenReason.length()==0){
                            etTakenReason.setError( "Reason field is required!!!" );
                        }
                        else if(personName.length()!=0 && takenAmount.length()!=0 && takenReason.length()!=0 && takenAmount.length()!=0 && takenDate.length()!=0){
                            boolean isInserted = lendDatabaseHelper.insertTakenData( personName, Float.parseFloat( takenAmount ) ,takenReason, lendyr, lendmth, lenddy);
                            if (isInserted == true) {
                                Toast.makeText(AddLendActivity.this, "Data Saved to Taken DataBase.", Toast.LENGTH_SHORT ).show();
                                Intent i = new Intent(AddLendActivity.this, LendActivity.class);
                                startActivity( i );

                            } else {
                                Toast.makeText( AddLendActivity.this, "Data is not Saved to Taken DataBase.", Toast.LENGTH_SHORT ).show();
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

        public int safeParseInt(String number) throws Exception {
            if(number != null) {
                return Integer.parseInt(number.trim());
            } else {
                throw new NullPointerException("Date string is invalid");
            }
        }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        populateSetDate(year, month+1, dayOfMonth );
    }
    public void populateSetDate(int year, int month, int day) {
        if(day<=9 && month <=9)
        {
            tvLendDate.setText("0"+day+"/"+"0"+month+"/"+year);
        }
        else if(day<=9 && month>9)
        {
            tvLendDate.setText("0"+day+"/"+month+"/"+year);
        }
        else if(day>9 && month<=9){
            tvLendDate.setText(day+"/"+"0"+month+"/"+year);
        }
        else{
            tvLendDate.setText(day+"/"+month+"/"+year);
        }
    }

    }

