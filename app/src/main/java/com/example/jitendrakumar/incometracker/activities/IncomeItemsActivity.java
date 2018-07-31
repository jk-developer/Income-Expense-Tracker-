package com.example.jitendrakumar.incometracker.activities;

import android.annotation.TargetApi;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.transition.Explode;
import android.transition.Slide;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.animation.AnticipateOvershootInterpolator;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.jitendrakumar.incometracker.R;
import com.example.jitendrakumar.incometracker.database.IncomeDatabaseHelper;
import com.example.jitendrakumar.incometracker.fragments.LoginFragment;
import com.example.jitendrakumar.incometracker.fragments.date_time_fragment.DatePickerFragment;

import java.util.Calendar;

public class IncomeItemsActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {

    TextInputLayout input_layout_value;
    EditText etValue, etIncomeDesc;
    TextView tvDate, tvHintDate, tvCategory, tvHintCategory, tvDelete, tvSave, tvTime, tvHintTime;
    private  String date, time, type, desc;
    private  int id, year, month, day, hour, minute;
    private float amt;
    IncomeDatabaseHelper incomeDb;
    private CharSequence incomeItems[] = {"Regular Salary", "Buissness Profits","Rental Income","Savings", "Gifts","Pocket Money"," Investments ","Governmental grants","Retirement Income",
            "Bonus","Other"};

    @TargetApi(Build.VERSION_CODES.N)
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().requestFeature( Window.FEATURE_CONTENT_TRANSITIONS );
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_income_items );
        incomeDb = new IncomeDatabaseHelper( this );

        initAnimation();

        input_layout_value = (TextInputLayout) findViewById( R.id.input_layout_value );
        tvDate = (TextView) findViewById( R.id.tvDate );
        tvCategory= (TextView) findViewById( R.id.tvCategory );
        tvHintDate = (TextView) findViewById( R.id.tvHintDate );
        tvHintCategory = (TextView) findViewById( R.id.tvHintCategory );
        tvDelete= (TextView) findViewById( R.id.tvDelete );
        tvSave = (TextView) findViewById( R.id.tvSave );
        tvTime = (TextView) findViewById( R.id.tvTime );
        tvHintTime = (TextView) findViewById( R.id.tvHintTime );
        etValue = (EditText) findViewById( R.id.etValue );
        etIncomeDesc = (EditText)findViewById( R.id.etIncomeDesc );

        Calendar c = Calendar.getInstance();
        int incyear = c.get( Calendar.YEAR );
        int incmonth = c.get( Calendar.MONTH);
        int incday = c.get( Calendar.DAY_OF_MONTH );

        final DatePickerDialog datePickerDialog = new DatePickerDialog(this, IncomeItemsActivity.this, incyear, incmonth, incday);

        Calendar cTime = Calendar.getInstance();
        int inchour = cTime.get( Calendar.HOUR_OF_DAY );
        int incminute = cTime.get( Calendar.MINUTE );
        final TimePickerDialog timePickerDialog = new TimePickerDialog( this,IncomeItemsActivity.this, inchour, incminute, android.text.format.DateFormat.is24HourFormat(this) );

        getSupportActionBar().setTitle( "Edit Income" );
        getSupportActionBar().setDisplayHomeAsUpEnabled( true );

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            //The key argument here must match that used in the other activity
              amt = extras.getFloat( "amount");
              date = extras.getString( "Date" );
              type = extras.getString( "Type" );
              time = extras.getString( "Time" );
              id = extras.getInt( "incomeId" );
              desc = extras.getString( "incomeDesc" );
        }

        tvDate.setText(date.toString());
        etValue.setText(String.valueOf(amt));
        tvTime.setText( time );
        tvCategory.setText( type );
        etIncomeDesc.setText( desc );
       // etValue.setSelection(String.valueOf( amt ).toString().length());

        tvDate.setHintTextColor( getResources().getColor(R.color.colorPrimaryDark) );
        tvCategory.setHintTextColor( getResources().getColor(R.color.colorPrimaryDark) );

        tvDelete.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final AlertDialog.Builder item_builder = new AlertDialog.Builder(IncomeItemsActivity.this);
                item_builder.setTitle( "Delete this Record?" );
                item_builder.setIcon( R.drawable.ic_reset);
                item_builder.setPositiveButton( "DELETE RECORD", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        incomeDb.deleteIncomeData( String.valueOf( id ) );
                        Intent i = new Intent( IncomeItemsActivity.this, IncomeReportActivity.class );
                        startActivity( i );
                      //  Toast.makeText( IncomeItemsActivity.this, "this income is deleted"+id , Toast.LENGTH_SHORT).show();
                        }
                } );
                item_builder.setNegativeButton( "CANCEL", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        item_builder.setCancelable( true );
                    }
                } );
                AlertDialog alertDialog = item_builder.create();
                alertDialog.show();

            }
        } );


        tvSave.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final AlertDialog.Builder save_builder = new AlertDialog.Builder(IncomeItemsActivity.this);
                save_builder.setTitle( "Finally save the changes ?" );

                save_builder.setPositiveButton( "Save", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                       final String newDate = tvDate.getText().toString();
                        String[]dateParts = newDate.split("/");
                        try {
                            year = safeParseInt(dateParts[2]);
                            month = safeParseInt(dateParts[1]);
                            day = safeParseInt(dateParts[0]);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        // parsing the hour and minute int values from Time String HH:MM
                       String newTime = tvTime.getText().toString();
                        String[] timeParts = newTime.split( ":" );
                        try {
                             hour = safeParseInt( timeParts[0] );
                             minute = safeParseInt( timeParts[1] );
                        }catch (Exception e)
                        {
                            e.printStackTrace();
                        }


                        if(incomeDb.updateIncomeData( String.valueOf( id ), tvCategory.getText().toString() , Float.parseFloat(etValue.getText().toString()  ) ,year, month, day,hour, minute, etIncomeDesc.getText().toString())) {
                            Intent i = new Intent( IncomeItemsActivity.this, IncomeReportActivity.class );
                            startActivity( i );
                            Toast.makeText( IncomeItemsActivity.this, "this income is updated" + id, Toast.LENGTH_SHORT ).show();
                        }
                        else {
                            Toast.makeText( IncomeItemsActivity.this, "Some error in updating ", Toast.LENGTH_SHORT ).show();
                        }
                    }
                } );
                save_builder.setNegativeButton( "CANCEL", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        save_builder.setCancelable( true );
                    }
                } );
                AlertDialog alertDialog = save_builder.create();
                alertDialog.show();

            }
        } );

        tvHintDate.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePickerDialog.show();
        }
        });

        tvHintTime.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              timePickerDialog.show();
            }
        } );

        tvHintCategory.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder income_builder = new AlertDialog.Builder(IncomeItemsActivity.this);
                income_builder.setTitle( "Select Income Category" );
               income_builder.setItems( incomeItems, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText( IncomeItemsActivity.this, ""+incomeItems[which],Toast.LENGTH_SHORT ).show();
                    }
                } );

               income_builder.setNegativeButton( "CANCEL", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        income_builder.setCancelable( true );
                    }
                } );

                income_builder.setPositiveButton( "OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(which==-1)
                        {
                          //  Toast.makeText( getContext(), "Select some  expense category", Toast.LENGTH_SHORT ).show();
                            tvCategory.setError( "Select some expense category!!!" );
                        }else {
                            tvCategory.setText( incomeItems[which].toString());

                        }


                    }
                } );
                AlertDialog alertDialog = income_builder.create();
                alertDialog.show();
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
            tvDate.setText("0"+day+"/"+"0"+month+"/"+year);
        }
        else if(day<=9 && month>9)
        {
            tvDate.setText("0"+day+"/"+month+"/"+year);
        }
        else if(day>9 && month<=9){
            tvDate.setText(day+"/"+"0"+month+"/"+year);
        }
        else{
            tvDate.setText(day+"/"+month+"/"+year);
        }
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        populateSetTime(hourOfDay, minute);
    }

    public void populateSetTime(int hour, int minute) {
        if(minute<=9 ||minute==0)
            tvTime.setText(hour+":0"+minute);
        else
            tvTime.setText( hour+":"+minute );

    }

    @Override
    public boolean onSupportNavigateUp() {
        finishAfterTransition();
        return true;    }

    public void initAnimation(){
        Slide enterTransition = new Slide( );
        enterTransition.setSlideEdge( Gravity.RIGHT);
        enterTransition.setInterpolator( new AnticipateOvershootInterpolator(  ));
        enterTransition.setDuration( 1000 );
        getWindow().setEnterTransition( enterTransition );


    }
}
