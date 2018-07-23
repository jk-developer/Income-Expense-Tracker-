package com.example.jitendrakumar.incometracker.activities;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.transition.Slide;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.animation.AnticipateOvershootInterpolator;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jitendrakumar.incometracker.R;
import com.example.jitendrakumar.incometracker.database.LendDatabaseHelper;

import java.util.Calendar;

public class LendItemsActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener{

    TextInputLayout input_layout_value;
    EditText etValue;
    TextView tvDate, tvHintDate, tvDesc, tvHintDesc, tvDelete, tvSave, tvPerson, tvHintPerson ;
    private  String Date, Desc, Person;
    private  int Id,lyear, lmonth, lday;;
    private float amt;
    LendDatabaseHelper lHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().requestFeature( Window.FEATURE_CONTENT_TRANSITIONS );
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_lend_items );

            lHelper = new LendDatabaseHelper( this );

            input_layout_value = (TextInputLayout) findViewById( R.id.input_layout_value );
            tvDate = (TextView) findViewById( R.id.tvDate );
            tvDesc= (TextView) findViewById( R.id.tvDesc);
            tvHintDate = (TextView) findViewById( R.id.tvHintDate );
            tvHintDesc = (TextView) findViewById( R.id.tvHintDesc);
            tvDelete= (TextView) findViewById( R.id.tvDelete );
            tvSave = (TextView) findViewById( R.id.tvSave );
            tvPerson = (TextView) findViewById( R.id.tvPerson );
            tvHintPerson = (TextView) findViewById( R.id.tvHintPerson );
            etValue = (EditText) findViewById( R.id.etValue );

            Calendar c = Calendar.getInstance();
            int incyear = c.get( Calendar.YEAR );
            int incmonth = c.get( Calendar.MONTH);
            int incday = c.get( Calendar.DAY_OF_MONTH );

            final DatePickerDialog datePickerDialog = new DatePickerDialog(this, LendItemsActivity.this, incyear, incmonth, incday);

            getSupportActionBar().setTitle( "Edit Lend" );
            getSupportActionBar().setDisplayHomeAsUpEnabled( true );

            initAnimation();

            Bundle extras = getIntent().getExtras();
            if (extras != null) {
                //The key argument here must match that used in the other activity
                amt = extras.getFloat( "amount");
                Date = extras.getString( "date" );
                Desc = extras.getString( "desc" );
                Person = extras.getString( "person" );
                Id = extras.getInt( "id" );
            }

            tvDate.setText(Date.toString());
            etValue.setText(String.valueOf(amt));
            tvPerson.setText( Person.toString() );
            tvDesc.setText(Desc.toString());

            tvDate.setHintTextColor( getResources().getColor(R.color.colorPrimaryDark) );
            tvDesc.setHintTextColor( getResources().getColor(R.color.colorPrimaryDark) );

            tvDelete.setOnClickListener( new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    final AlertDialog.Builder item_builder = new AlertDialog.Builder(LendItemsActivity.this);
                    item_builder.setTitle( "Delete this Record?" );
                    item_builder.setIcon( R.drawable.ic_reset);
                    item_builder.setPositiveButton( "DELETE RECORD", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            lHelper.deleteLendData( String.valueOf( Id ) );
                            Intent i = new Intent( LendItemsActivity.this, LendActivity.class );
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

                    final AlertDialog.Builder save_builder = new AlertDialog.Builder(LendItemsActivity.this);
                    save_builder.setTitle( "Finally save the changes ?" );

                    save_builder.setPositiveButton( "Save", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            final String newDate = tvDate.getText().toString();

                            String[]dateParts = newDate.toString().split("/");
                            try {
                                lyear = safeParseInt(dateParts[2]);
                                lmonth = safeParseInt(dateParts[1]);
                                lday = safeParseInt(dateParts[0]);
                            } catch (Exception e) {
                                Toast.makeText( LendItemsActivity.this, "Error in parsing Date", Toast.LENGTH_SHORT ).show();
                            }

                            if(lHelper.updateLendData( String.valueOf( Id ),tvPerson.getText().toString(), Float.parseFloat(etValue.getText().toString()) , tvDesc.getText().toString(),lyear, lmonth, lday)) {
                                Intent i = new Intent( LendItemsActivity.this, LendActivity.class );
                                startActivity( i );
                                Toast.makeText( LendItemsActivity.this, "this income is updated" + Id, Toast.LENGTH_SHORT ).show();
                            }
                            else {
                                Toast.makeText( LendItemsActivity.this, "Some error in updating ", Toast.LENGTH_SHORT ).show();
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

    public int safeParseInt(String number) throws Exception {
        if(number != null) {
            return Integer.parseInt(number.trim());
        } else {
            throw new NullPointerException("Date string is invalid");
        }
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

