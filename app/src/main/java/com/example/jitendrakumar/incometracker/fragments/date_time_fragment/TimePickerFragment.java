package com.example.jitendrakumar.incometracker.fragments.date_time_fragment;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.Calendar;

@SuppressLint("ValidFragment")
public class TimePickerFragment extends DialogFragment implements TimePickerDialog.OnTimeSetListener{
    private TextView tvTime;
    private Calendar c;

    @SuppressLint("ValidFragment")
    public TimePickerFragment(TextView etIncomeTime) {
        this.tvTime = etIncomeTime;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
         c = Calendar.getInstance();
        int hour = c.get( Calendar.HOUR_OF_DAY );
        int minute = c.get( Calendar.MINUTE );
        return new TimePickerDialog( getActivity(), (TimePickerDialog.OnTimeSetListener) this, hour, minute, android.text.format.DateFormat.is24HourFormat(getContext()) );
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
    public String checkAmPm(){
        String am_pm = " ";
        if (c.get(Calendar.AM_PM) == Calendar.AM)
            am_pm = "AM";
        else if (c.get(Calendar.AM_PM) == Calendar.PM)
            am_pm = "PM";

        return am_pm;
    }
}
