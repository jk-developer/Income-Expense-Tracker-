package com.example.jitendrakumar.incometracker.activities;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
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
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.jitendrakumar.incometracker.R;
import com.example.jitendrakumar.incometracker.database.BorrowDatabaseHelper;
import com.example.jitendrakumar.incometracker.fragments.date_time_fragment.DatePickerFragment;

import java.util.Calendar;

public class AddBorrowActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener{

    EditText etPersonName, etPayingAmount, etPayingReason;
    Button btnPayingSubmit, btnViewAllPayingData;
    TextView tvBorrowDate, tvHintBorrowDate;
    BorrowDatabaseHelper borrowDatabaseHelper;
    public static final String TAG = "name";
    private   int  yr, mth , dy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_add_borrow );

        getSupportActionBar().setTitle( "Add Borrow" );
        getSupportActionBar().setDisplayHomeAsUpEnabled( true );

            borrowDatabaseHelper = new BorrowDatabaseHelper(AddBorrowActivity.this);

            etPersonName = (EditText)findViewById( R.id.etPersonName );
            etPayingAmount = (EditText)findViewById( R.id.etPayingAmount );
            etPayingReason = (EditText) findViewById( R.id.etPayingReason );
            tvBorrowDate = (TextView)findViewById( R.id.tvBorrowDate );
            btnPayingSubmit = (Button)findViewById( R.id.btnPayingSubmit );
            tvHintBorrowDate = (TextView) findViewById( R.id.tvHintBorrowDate );

            etPersonName.setHintTextColor(getResources().getColor(R.color.colorTexts));
            etPayingReason.setHintTextColor(getResources().getColor(R.color.colorTexts));
            etPayingAmount.setHintTextColor(getResources().getColor(R.color.colorTexts));
            tvBorrowDate.setHintTextColor(getResources().getColor(R.color.colorTexts));

            etPersonName.setTextColor( Color.parseColor("#00ff00"));
            tvBorrowDate.setTextColor( Color.parseColor("#00ff00"));
            etPayingAmount.setTextColor( Color.parseColor("#00ff00"));
            etPayingReason.setTextColor( Color.parseColor("#00ff00"));

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
                Date = "0"+day +"/"+(month+1)+"/"+year ;
            }
            else {
                Date = day +"/"+(month+1)+"/"+year ;
            }

            tvBorrowDate.setText(Date);

        final DatePickerDialog datePickerDialog = new DatePickerDialog(this, AddBorrowActivity.this, year, month, day);

        tvHintBorrowDate.setOnClickListener( new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                 datePickerDialog.show();
                }
            } );



            btnPayingSubmit.setOnClickListener( new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        String personName = etPersonName.getText().toString();
                        Log.d( TAG, "onClick: "+personName );
                        String payingAmount = etPayingAmount.getText().toString();
                        String payingDate =  tvBorrowDate.getText().toString();
                        String payingReason = etPayingReason.getText().toString();

                        String[]dateParts = payingDate.toString().split("/");
                        try {
                            yr = safeParseInt(dateParts[2]);
                            mth = safeParseInt(dateParts[1]);
                            dy = safeParseInt(dateParts[0]);
                        } catch (Exception e) {
                            Toast.makeText(AddBorrowActivity.this, "Error in parsing Date", Toast.LENGTH_SHORT ).show();
                        }

                        if (personName.length()==0){
                            etPersonName.setError( "Person Name field is required!!!" );
                        }
                        if(payingAmount.length()==0){
                            etPayingAmount.setError( "Paying Amount field is required!!!" );
                        }
                        if(payingDate.length()==0)
                        {
                            Toast.makeText(AddBorrowActivity.this, "Date field is required!!!", Toast.LENGTH_SHORT ).show();
                        }
                        if(payingReason.length()==0){
                            etPayingReason.setError( "Reason field is required!!!" );
                        }
                        else{

                            boolean isInserted = borrowDatabaseHelper.insertPayingData( personName, Float.parseFloat(payingAmount  ) ,payingReason,yr, mth,dy);
                            if (isInserted == true) {
                                Toast.makeText(AddBorrowActivity.this, "Data Saved to Paying DataBase.", Toast.LENGTH_SHORT ).show();
                                Intent i = new Intent(AddBorrowActivity.this, BorrowActivity.class );
                                startActivity( i );

                            } else {
                                Toast.makeText(AddBorrowActivity.this, "Data is not Saved to Paying DataBase.", Toast.LENGTH_SHORT ).show();
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
            tvBorrowDate.setText("0"+day+"/"+"0"+month+"/"+year);
        }
        else if(day<=9 && month>9)
        {
            tvBorrowDate.setText("0"+day+"/"+month+"/"+year);
        }
        else if(day>9 && month<=9){
            tvBorrowDate.setText(day+"/"+"0"+month+"/"+year);
        }
        else{
            tvBorrowDate.setText(day+"/"+month+"/"+year);
        }
    }
}


