package com.example.jitendrakumar.incometracker.activities;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.jitendrakumar.incometracker.R;
import com.example.jitendrakumar.incometracker.database.ExpenseDatabaseHelper;
import com.example.jitendrakumar.incometracker.fragments.date_time_fragment.DatePickerFragment;
import com.example.jitendrakumar.incometracker.fragments.date_time_fragment.TimePickerFragment;
import com.example.jitendrakumar.incometracker.helper.SessionManagement;

import java.util.Calendar;

public class AddExpenseActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener{
    EditText etExpenseAmount, etExpenseDescription;
    TextView tvExpenseDate, tvExpenseTime, tvHintExpenseDate, tvExpenseType, tvExpenseHintType, tvExpenseHintTime;
    ExpenseDatabaseHelper MyexpenseDB;
    Button btnExpenseSubmit;
    private int eyear, emonth, eday, ehour, eminute;
    SessionManagement s;
    private CharSequence expense[] = {"Food", "Leisure","Transport", "Medicines", "House Rent", "Maintenace", "Clothes", "Travel","Health","Hobbies","Gifts","Household",
            "Groceries","Gadgets","Kids", "Loans", "Education","Holidays","Savings","Beauty","Sports","Mobile","Other"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_add_expense );

        getSupportActionBar().setTitle( "Add Expense" );
        getSupportActionBar().setDisplayHomeAsUpEnabled( true );

            MyexpenseDB = new ExpenseDatabaseHelper(AddExpenseActivity.this);
            tvExpenseType = (TextView) findViewById( R.id.tvExpenseType );
            tvExpenseHintType  = (TextView) findViewById( R.id.tvExpenseHintType );
            etExpenseAmount = (EditText) findViewById( R.id.etExpenseAmount );
            etExpenseDescription = (EditText)findViewById( R.id.etExpenseDescription );
            tvExpenseDate = (TextView)findViewById( R.id.tvExpenseDate );
            tvExpenseTime = (TextView)findViewById( R.id.tvExpenseTime );
            btnExpenseSubmit = (Button)findViewById( R.id.btnExpenseSubmit );
            tvHintExpenseDate = (TextView)findViewById( R.id.tvHintExpenseDate );
            tvExpenseHintTime = (TextView)findViewById( R.id.tvExpenseHintTime );
            s = new SessionManagement(AddExpenseActivity.this);

            Calendar c = Calendar.getInstance();
            int year = c.get( Calendar.YEAR );
            int month = c.get( Calendar.MONTH);
            int day = c.get( Calendar.DAY_OF_MONTH );
            int hour = c.get( Calendar.HOUR_OF_DAY );
            int minute = c.get( Calendar.MINUTE );
            String Date = "";
            if((month+1)<=9 && day<=9)
            {
                Date = "0"+day +"/0"+(month+1) +"/"+year ;
            }
            if((month+1)<=9 && day>9){
                Date = day +"/0"+(month+1)+"/"+year ;
            }
            if((month+1)>9 && day<=9){
                Date = "0"+day +"/"+(month+1) +"/"+year ;
            }
            else {
                Date = day +"/"+(month+1) +"/"+year ;
            }

            tvExpenseDate.setText(Date);
            tvExpenseTime.setText( hour+":"+minute );

            final DatePickerDialog datePickerDialog = new DatePickerDialog(this, AddExpenseActivity.this, year, month, day);
            final TimePickerDialog timePickerDialog = new TimePickerDialog( this,AddExpenseActivity.this, hour, minute, android.text.format.DateFormat.is24HourFormat(this) );

            tvExpenseType.setHintTextColor( getResources().getColor( R.color.colorTexts ) );
            tvExpenseHintType.setHintTextColor( getResources().getColor( R.color.colorTexts ) );
            etExpenseAmount.setHintTextColor( getResources().getColor( R.color.colorTexts ) );
            tvExpenseDate.setHintTextColor( getResources().getColor( R.color.colorTexts ) );
            tvExpenseTime.setHintTextColor( getResources().getColor( R.color.colorTexts ) );

            etExpenseAmount.setTextColor( Color.parseColor( "#00ff00" ) );
            tvExpenseDate.setTextColor( Color.parseColor( "#00ff00" ) );
            tvExpenseType.setTextColor( Color.parseColor( "#00ff00" ) );
            tvExpenseHintType.setTextColor( Color.parseColor( "#00ff00" ) );
            tvExpenseTime.setTextColor( Color.parseColor( "#00ff00" ) );

            addDataInExpenseDB();

            tvExpenseHintType.setOnClickListener( new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final AlertDialog.Builder builder = new AlertDialog.Builder(AddExpenseActivity.this);
                    builder.setTitle( "Select Expense Category" );
                    builder.setItems( expense, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            tvExpenseType.setText( expense[which].toString().trim());
                            Toast.makeText(AddExpenseActivity.this, ""+expense[which],Toast.LENGTH_SHORT ).show();
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
                                Toast.makeText( AddExpenseActivity.this, "Select some  expense category", Toast.LENGTH_SHORT ).show();
                                //  tvExpenseType.setError( "Select some expense category!!!" );
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
                    datePickerDialog.show();
                }
            } );

            tvExpenseHintTime.setOnClickListener( new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    timePickerDialog.show();
                }
            } );


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
                        String expenseDesc = etExpenseDescription.getText().toString();

                        // Extracting year month and day integer value from the Date String DD/MM/YYYY
                        String[]dateParts = expenseDate.toString().split("/");
                        try {
                            eyear = safeParseInt(dateParts[2]);
                            emonth = safeParseInt(dateParts[1]);
                            eday = safeParseInt(dateParts[0]);
                        } catch (Exception e) {
                            Toast.makeText(AddExpenseActivity.this, "Error in parsing Date", Toast.LENGTH_SHORT ).show();
                        }
                        String timeStr = expenseTime.toString();
                        String[] timeParts = timeStr.split( ":" );
                        try {
                            ehour = safeParseInt( timeParts[0] );
                            eminute = safeParseInt( timeParts[1] );
                        }catch (Exception e)
                        {
                            Toast.makeText(AddExpenseActivity.this, "Error in parsing Time", Toast.LENGTH_SHORT ).show();
                        }


                        if (expenseType.length() == 0) {
                            tvExpenseType.setError( "Expense Type is required!!!" );
                        }
                        else if (expenseAmount.length() == 0) {
                            etExpenseAmount.setError( "Expense Amount is required!!!" );
                        }
                        else if (expenseDate.length() == 0) {
                            Toast.makeText(AddExpenseActivity.this,"Date field is required!!! ", Toast.LENGTH_SHORT ).show();
                        }
                        else if (expenseTime.length() == 0) {
                            Toast.makeText(AddExpenseActivity.this,"Time field is required!!! ", Toast.LENGTH_SHORT ).show();
                        }
                        else {
                            boolean isInserted = MyexpenseDB.insertExpenseData( expenseType, Float.parseFloat( expenseAmount ),eyear, emonth, eday, ehour,eminute, expenseDesc );
                            if (isInserted == true) {
                                Toast.makeText( AddExpenseActivity.this, "Data Saved to Expense DataBase.", Toast.LENGTH_SHORT ).show();
                                Intent i = new Intent(AddExpenseActivity.this, ExpenseReportActivity.class );
                                startActivity( i );

                            } else {
                                Toast.makeText(AddExpenseActivity.this, "Data is not Saved to Expense DataBase.", Toast.LENGTH_SHORT ).show();
                            }
                        }
                    } catch (NullPointerException e) {
                        e.printStackTrace();
                    }
                }

            } );


        }

        private int safeParseInt(String number) throws Exception {
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

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        populateSetTime(hourOfDay, minute);
    }

    public void populateSetTime(int hour, int minute) {
        if(minute<=9 ||minute==0)
            tvExpenseTime.setText(hour+":0"+minute);
        else
            tvExpenseTime.setText( hour+":"+minute );

    }

    public void populateSetDate(int year, int month, int day) {
        if(day<=9 && month <=9)
        {
            tvExpenseDate.setText("0"+day+"/"+"0"+month+"/"+year);
        }
        else if(day<=9 && month>9)
        {
            tvExpenseDate.setText("0"+day+"/"+month+"/"+year);
        }
        else if(day>9 && month<=9){
            tvExpenseDate.setText(day+"/"+"0"+month+"/"+year);
        }
        else{
            tvExpenseDate.setText(day+"/"+month+"/"+year);
        }
    }
}

