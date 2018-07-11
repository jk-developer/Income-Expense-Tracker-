package com.example.jitendrakumar.incometracker.fragments.date_time_fragment;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.widget.DatePicker;
import android.widget.TextView;

import java.util.Calendar;

@SuppressLint("ValidFragment")
public class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {
    private TextView tvdate;

    @SuppressLint("ValidFragment")
    public DatePickerFragment(TextView etIncomeDate) {
        this.tvdate = etIncomeDate;
    }


    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        Calendar c = Calendar.getInstance();
        int year = c.get( Calendar.YEAR );
        int month = c.get( Calendar.MONTH);
        int day = c.get( Calendar.DAY_OF_MONTH );
        return new DatePickerDialog(getActivity(), (DatePickerDialog.OnDateSetListener) this, year, month, day);
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
       populateSetDate(year, month+1, dayOfMonth );
    }
    public void populateSetDate(int year, int month, int day) {
        if(day<=9 && month <=9)
        {
            tvdate.setText("0"+day+"/"+"0"+month+"/"+year);
        }
        else if(day<=9 && month>9)
        {
            tvdate.setText("0"+day+"/"+month+"/"+year);
        }
        else if(day>9 && month<=9){
            tvdate.setText(day+"/"+"0"+month+"/"+year);
        }
        else{
            tvdate.setText(day+"/"+month+"/"+year);
        }
    }
}
