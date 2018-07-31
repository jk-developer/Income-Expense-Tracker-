package com.example.jitendrakumar.incometracker.adapters;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jitendrakumar.incometracker.R;
import com.example.jitendrakumar.incometracker.activities.IncomeItemsActivity;
import com.example.jitendrakumar.incometracker.activities.MainActivity;
import com.example.jitendrakumar.incometracker.fragments.date_time_fragment.TimePickerFragment;
import com.example.jitendrakumar.incometracker.models.IncomeData;

import java.util.ArrayList;
import java.util.Calendar;

public class MyIncomeAdapter extends RecyclerView.Adapter<MyIncomeAdapter.BeneficiaryViewHolder>  {
    private ArrayList<IncomeData> listBeneficiary;
    private Context mContext;
    private ArrayList<IncomeData> mFilteredList;
    public static final String TAG = "res";
    private int year , month, day, hour, minute;
    TimePickerFragment timePickerFragment;
    IncomeData incomeData;


    public MyIncomeAdapter(ArrayList<IncomeData> listBeneficiary, Context mContext) {
        this.listBeneficiary = listBeneficiary;
        this.mContext = mContext;
        this.mFilteredList = listBeneficiary;
        }

    public class BeneficiaryViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public AppCompatTextView tvIncomeReportType;
        public AppCompatTextView tvIncomeReportAmount;
        public AppCompatTextView tvIncomeReportDate;
        public AppCompatTextView tvIncomeReportTime;
        public AppCompatTextView tvIncomeReportDesc;

        public BeneficiaryViewHolder(View view) {
            super(view);
            view.setOnClickListener(this);
            tvIncomeReportType = (AppCompatTextView)view.findViewById( R.id.tvIncomeReportType );
            tvIncomeReportAmount = (AppCompatTextView)view.findViewById( R.id.tvIncomeReportAmount );
            tvIncomeReportDate = (AppCompatTextView)view.findViewById( R.id.tvIncomeReportDate );
            tvIncomeReportTime = (AppCompatTextView)view.findViewById( R.id.tvIncomeReportTime );
            tvIncomeReportDesc = (AppCompatTextView)view.findViewById( R.id.tvIncomeReportDesc );

        }

        @Override
        public void onClick(View v) {
            incomeData = new IncomeData( listBeneficiary.get( getPosition()).getIncomeId(), listBeneficiary.get( getPosition()).getInputType(), listBeneficiary.get( getPosition()).getInputAmount(), listBeneficiary.get( getPosition() ).getIncomeDate(), listBeneficiary.get(getPosition()).getIncomeTime(), listBeneficiary.get( getPosition()).getIncomeDesc());
           // Toast.makeText( mContext, getPosition()+ incomeData.getInputType()+ incomeData.getInputAmount()+"is clicked", Toast.LENGTH_SHORT).show();
            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation( (Activity) mContext );
            Intent i = new Intent( mContext, IncomeItemsActivity.class );
            i.putExtra( "amount",incomeData.getInputAmount());
            i.putExtra( "Date", incomeData.getIncomeDate() );
            i.putExtra( "Time", incomeData.getIncomeTime() );
            i.putExtra( "Type", incomeData.getInputType());
            i.putExtra( "incomeId", incomeData.getIncomeId() );
            i.putExtra( "incomeDesc", incomeData.getIncomeDesc() );
            mContext.startActivity( i, options.toBundle() );
        }
    }

    @Override
    public BeneficiaryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // inflating recycler item view
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.single_income_item, parent, false);

        return new BeneficiaryViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final BeneficiaryViewHolder holder, int position) {
      //  holder.tvIncomeReportId.setText(String.valueOf( listBeneficiary.get(position).getIncomeId() ));
        holder.tvIncomeReportType.setText(listBeneficiary.get(position).getInputType());
        holder.tvIncomeReportAmount.setText(String.valueOf( listBeneficiary.get(position).getInputAmount() ));

        String dateStr = listBeneficiary.get( position ).getIncomeDate().toString();

        String[]dateParts = dateStr.split("/");
        try {
            year = safeParseInt(dateParts[2]);
            month = safeParseInt(dateParts[1]);
            day = safeParseInt(dateParts[0]);
        } catch (Exception e) {
            Log.d( TAG, "onBindViewHolder: Error in Date Parsing  " );
        }
        // parsing the hour and minute int values from Time String HH:MM
        String timeStr = listBeneficiary.get( position).getIncomeTime().toString();
        String[] timeParts = timeStr.split( ":" );
        try {
            hour = safeParseInt( timeParts[0] );
            minute = safeParseInt( timeParts[1] );
        }catch (Exception e)
        {
            e.printStackTrace();
        }

        String Date = "";
        if(month<=9 && day<=9)
        {
             Date = "0"+day +"/0"+month +"/"+year ;
        }
        if(month<=9 && day>9){
             Date = day +"/0"+month +"/"+year ;
        }
        if(month>9 && day<=9){
            Date = "0"+day +"/"+month +"/"+year ;
        }
        else {
             Date = day +"/"+month +"/"+year ;
        }
        Log.d( TAG, "onBindViewHolder: "+Date );
         String Time = hour+":"+minute;
         holder.tvIncomeReportDate.setText(Date);
         holder.tvIncomeReportTime.setText(Time);
         holder.tvIncomeReportDesc.setText( listBeneficiary.get( position).getIncomeDesc());

    }

    @Override
    public int getItemCount() {
        if(mFilteredList==null)
            return 0;
        else
          return mFilteredList.size();
    }

    public int safeParseInt(String number) throws Exception {
        if(number != null) {
            return Integer.parseInt(number.trim());
        } else {
            throw new NullPointerException("Date string is invalid");
        }
    }
}


