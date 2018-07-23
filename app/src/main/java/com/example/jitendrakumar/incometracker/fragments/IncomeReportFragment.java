package com.example.jitendrakumar.incometracker.fragments;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jitendrakumar.incometracker.R;
import com.example.jitendrakumar.incometracker.activities.BarChartActivity;
import com.example.jitendrakumar.incometracker.database.IncomeDatabaseHelper;
import com.example.jitendrakumar.incometracker.fragments.date_time_fragment.DatePickerFragment;

import java.util.Calendar;

@SuppressLint("ValidFragment")
public class IncomeReportFragment extends Fragment {
    TextView tvIncomeReportDateFrom, tvIncomeReportDateTo, tvHintIncomeReportDateFrom, tvHintIncomeReportDateTo, tvIncomeReportType, tvIncomeReportInput;
    IncomeDatabaseHelper myIncomeDB;
    Button btnIncomeReportbwTwoDates, btnIncomeBarchart, btnIncomeReportbwMonthsAndDay, btnViewIncomeReportbwMonths, btnIncomeReportCategorywise;
    EditText etFromMonth, etToMonth, etToDay, etFromDay, etIncomeReportFromMonth, etIncomeReportToMonth;
    private String id;
    public static final String TAG = "res";
    private int years, yearf, months, monthf, days, dayf;
    private CharSequence income[] = {"Regular Salary", "Buissness Profits", "Rental Income", "Savings", "Gifts", "Pocket Money", " Investments ", "Governmental grants", "Retirement Income",
            "Bonus", "Other"};


        @Nullable
        @Override
        public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            View view = inflater.inflate( R.layout.fragment_income_report, container, false );

            tvIncomeReportDateFrom = (TextView) view.findViewById( R.id.tvIncomeReportDateFrom );
            tvIncomeReportDateTo = (TextView) view.findViewById( R.id.tvIncomeReportDateTo );
            btnIncomeReportbwTwoDates = (Button) view.findViewById( R.id.btnIncomeReportbwTwoDates );
            tvHintIncomeReportDateFrom = (TextView) view.findViewById( R.id.tvHintIncomeReportDateFrom );
            tvHintIncomeReportDateTo = (TextView) view.findViewById( R.id.tvHintIncomeReportDateTo );

            btnIncomeBarchart = (Button) view.findViewById( R.id.btnIncomeBarchart );
            btnIncomeReportbwMonthsAndDay = (Button) view.findViewById( R.id.btnIncomeReportbwMonthsAndDay );
            btnViewIncomeReportbwMonths = (Button) view.findViewById( R.id.btnViewIncomeReportbwMonths );
            etFromMonth = (EditText) view.findViewById( R.id.etFromMonth );
            etToMonth = (EditText) view.findViewById( R.id.etToMonth );
            etIncomeReportToMonth = (EditText) view.findViewById( R.id.etIncomeReportToMonth );
            etToDay = (EditText) view.findViewById( R.id.etToDay );
            etFromDay = (EditText) view.findViewById( R.id.etFromDay );
            etIncomeReportFromMonth = (EditText) view.findViewById( R.id.etIncomeReportFromMonth );
            tvIncomeReportInput = (TextView) view.findViewById( R.id.tvIncomeReportInput );
            btnIncomeReportCategorywise = (Button) view.findViewById( R.id.btnIncomeReportCategorywise );
            tvIncomeReportType = (TextView) view.findViewById( R.id.tvIncomeReportType );

            myIncomeDB = new IncomeDatabaseHelper( getContext() );

            tvIncomeReportDateFrom.setHintTextColor( getResources().getColor( R.color.colorTexts ) );
            tvIncomeReportDateTo.setHintTextColor( getResources().getColor( R.color.colorTexts ) );
            tvIncomeReportType.setHintTextColor( getResources().getColor( R.color.colorTexts ) );
            tvIncomeReportDateFrom.setTextColor( Color.parseColor( "#00ff00" ) );
            tvIncomeReportDateTo.setTextColor( Color.parseColor( "#00ff00" ) );
            etFromMonth.setTextColor( Color.parseColor( "#00ff00" ) );
            etToMonth.setTextColor( Color.parseColor( "#00ff00" ) );
            etFromDay.setTextColor( Color.parseColor( "#00ff00" ) );
            etToDay.setTextColor( Color.parseColor( "#00ff00" ) );
            etIncomeReportFromMonth.setTextColor( Color.parseColor( "#00ff00" ) );
            etIncomeReportToMonth.setTextColor( Color.parseColor( "#00ff00" ) );
            tvIncomeReportInput.setTextColor( Color.parseColor( "#00ff00" ) );

            showAllIncomeDatabwDates();
            showRecordbwMonthsandDays();
            showRecordsbwMonths();
            Calendar cal = Calendar.getInstance();
            int year = cal.get( Calendar.YEAR );
            int month = cal.get( Calendar.MONTH );
            int day = cal.get( Calendar.DAY_OF_MONTH );

            tvIncomeReportDateTo.setText( day + "/" + (month + 1) + "/" + year );
            tvIncomeReportDateFrom.setText( day + "/" + (month + 1) + "/" + year );

            tvHintIncomeReportDateFrom.setOnClickListener( new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DialogFragment newFragment = new DatePickerFragment( tvIncomeReportDateFrom );
                    newFragment.show( getFragmentManager(), "DatePicker" );
                }
            } );

            tvHintIncomeReportDateTo.setOnClickListener( new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DialogFragment newFragment = new DatePickerFragment( tvIncomeReportDateTo );
                    newFragment.show( getFragmentManager(), "DatePicker" );
                }
            } );

            btnIncomeBarchart.setOnClickListener( new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent( getActivity(), BarChartActivity.class );
                    startActivity( i );
                }
            } );

            tvIncomeReportType.setOnClickListener( new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final AlertDialog.Builder builder = new AlertDialog.Builder( getActivity() );
                    builder.setTitle( "Select Expense Category" );
                    builder.setItems( income, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            tvIncomeReportInput.setText( income[which].toString() );
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
                            if (which == -1) {

                            } else {
                                tvIncomeReportInput.setText( income[which].toString() );

                            }


                        }
                    } );
                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();

                }
            } );

            btnIncomeReportCategorywise.setOnClickListener( new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String category = tvIncomeReportInput.getText().toString();
                    try {
                        Cursor r = myIncomeDB.getRecordsCategorywise( category );
                        if (r.getCount() == 0) {
                            // Show message
                            showMessage( "Error", "Nothing Found" );

                            return;
                        } else {
                            StringBuffer buffer = new StringBuffer();
                            while (r.moveToNext()) {
                                buffer.append( "Income Id : " + r.getInt( 0 ) + "\n" );
                                buffer.append( "Income Type : " + r.getString( 1 ) + "\n" );
                                buffer.append( "Income Amount : " + r.getFloat( 2 ) + "\n" );
                                String date = r.getInt( 5 ) + "/" + r.getInt( 4 ) + "/" + r.getInt( 3 );
                                String time = r.getInt( 6 ) + ":" + r.getInt( 7 );
                                buffer.append( "Date : " + date + "\n" );
                                buffer.append( "Time : " + time + "\n" );
                                buffer.append( "Description :" + r.getString( 8 ) + "\n\n" );

                            }
                            // Show all data
                            showMessage( "Data", buffer.toString() );
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            } );

            return view;
        }


        public void showMessage(String title, String Message) {
            AlertDialog.Builder builder = new AlertDialog.Builder( getContext() );
            builder.setCancelable( true );
            builder.setTitle( title );
            builder.setMessage( Message );
            builder.show();
        }

        public void showAllIncomeDatabwDates() {
            btnIncomeReportbwTwoDates.setOnClickListener( new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    String dateFrom = tvIncomeReportDateFrom.getText().toString();
                    String dateTo = tvIncomeReportDateTo.getText().toString();
                    String[] dateParts = dateFrom.toString().split( "/" );
                    try {
                        years = safeParseInt( dateParts[2] );
                        months = safeParseInt( dateParts[1] );
                        days = safeParseInt( dateParts[0] );
                    } catch (Exception e) {
                        Toast.makeText( getActivity(), "Error in parsing Date", Toast.LENGTH_SHORT ).show();
                    }

                    String[] date_2Parts = dateTo.toString().split( "/" );
                    try {
                        yearf = safeParseInt( date_2Parts[2] );
                        monthf = safeParseInt( date_2Parts[1] );
                        dayf = safeParseInt( date_2Parts[0] );
                    } catch (Exception e) {
                        Toast.makeText( getActivity(), "Error in parsing Date", Toast.LENGTH_SHORT ).show();
                    }

                    try {
                        Log.d( TAG, "onClick: Clicked " );
                        Cursor res = myIncomeDB.getRecordbwyears( years, yearf, months, monthf, days, dayf );
                        Log.d( TAG, "onClick: result" + res.getCount() );
                        if (res.getCount() == 0) {
                            // Show message
                            showMessage( "Error", "Nothing Found" );

                            return;
                        } else {
                            StringBuffer buffer = new StringBuffer();
                            while (res.moveToNext()) {
                                buffer.append( "Income Id : " + res.getInt( 0 ) + "\n" );
                                buffer.append( "Income Type : " + res.getString( 1 ) + "\n" );
                                buffer.append( "Income Amount : " + res.getFloat( 2 ) + "\n" );
                                String date = res.getInt( 5 ) + "/" + res.getInt( 4 ) + "/" + res.getInt( 3 );
                                String time = res.getInt( 6 ) + ":" + res.getInt( 7 );
                                buffer.append( "Date : " + date + "\n" );
                                buffer.append( "Time : " + time + "\n" );
                                buffer.append( "Description :" + res.getString( 8 ) + "\n\n" );

                            }
                            // Show all data
                            showMessage( "Data", buffer.toString() );
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }


            } );
        }


        public void showRecordbwMonthsandDays() {

            btnIncomeReportbwMonthsAndDay.setOnClickListener( new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    try {
                        int y1 = Integer.parseInt( etFromMonth.getText().toString() );
                        int y2 = Integer.parseInt( etToMonth.getText().toString() );
                        int m1 = Integer.parseInt( etFromDay.getText().toString() );
                        int m2 = Integer.parseInt( etToDay.getText().toString() );

                        Cursor r = myIncomeDB.getRecordbwDays( y1, y2, m1, m2 );
                        Log.d( TAG, "onClick: result" + r.getCount() );
                        if (r.getCount() == 0) {
                            // Show message
                            showMessage( "Error", "Nothing Found" );

                            return;
                        } else {
                            StringBuffer buffer = new StringBuffer();
                            while (r.moveToNext()) {
                                buffer.append( "Income Id : " + r.getInt( 0 ) + "\n" );
                                buffer.append( "Income Type : " + r.getString( 1 ) + "\n" );
                                buffer.append( "Income Amount : " + r.getFloat( 2 ) + "\n" );
                                String date = r.getInt( 5 ) + "/" + r.getInt( 4 ) + "/" + r.getInt( 3 );
                                String time = r.getInt( 6 ) + ":" + r.getInt( 7 );
                                buffer.append( "Date : " + date + "\n" );
                                buffer.append( "Time : " + time + "\n" );
                                buffer.append( "Description :" + r.getString( 8 ) + "\n\n" );

                            }
                            // Show all data
                            showMessage( "Data", buffer.toString() );
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }


                }

            } );

        }

        public void showRecordsbwMonths() {

            btnViewIncomeReportbwMonths.setOnClickListener( new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.d( TAG, "onClick: bwMonths" );
                    try {
                        int month1 = Integer.parseInt( etIncomeReportFromMonth.getText().toString() );
                        int month2 = Integer.parseInt( etIncomeReportToMonth.getText().toString() );
                        Cursor r = myIncomeDB.getRecordbwMonths( month1, month2 );
                        Log.d( TAG, "onClick: result" + r.getCount() );
                        if (r.getCount() == 0) {
                            // Show message
                            showMessage( "Error", "Nothing Found" );

                            return;
                        } else {
                            StringBuffer buffer = new StringBuffer();
                            while (r.moveToNext()) {
                                buffer.append( "Income Id : " + r.getInt( 0 ) + "\n" );
                                buffer.append( "Income Type : " + r.getString( 1 ) + "\n" );
                                buffer.append( "Income Amount : " + r.getFloat( 2 ) + "\n" );
                                String date = r.getInt( 5 ) + "/" + r.getInt( 4 ) + "/" + r.getInt( 3 );
                                String time = r.getInt( 6 ) + ":" + r.getInt( 7 );
                                buffer.append( "Date : " + date + "\n" );
                                buffer.append( "Time : " + time + "\n" );
                                buffer.append( "Description :" + r.getString( 8 ) + "\n\n" );

                            }
                            // Show all data
                            showMessage( "Data", buffer.toString() );
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }


                }

            } );

        }


        public int safeParseInt(String number) throws Exception {
            if (number != null) {
                return Integer.parseInt( number.trim() );
            } else {
                throw new NullPointerException( "Date string is invalid" );
            }
        }

}
