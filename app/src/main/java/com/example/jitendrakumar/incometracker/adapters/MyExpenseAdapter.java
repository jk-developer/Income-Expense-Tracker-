package com.example.jitendrakumar.incometracker.adapters;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.jitendrakumar.incometracker.R;
import com.example.jitendrakumar.incometracker.activities.ExpenseItemsActivity;
import com.example.jitendrakumar.incometracker.models.ExpenseData;
import com.example.jitendrakumar.incometracker.models.IncomeData;

import java.util.ArrayList;

public class MyExpenseAdapter extends RecyclerView.Adapter<MyExpenseAdapter.MyViewHolder>  {
       private ArrayList<ExpenseData> listBeneficiary;
        private Context mContext;
        private ArrayList<ExpenseData> mFilteredList;
        public static final String TAG = "error";
        private int expyear , expmonth, expday, exphour, expminute;
        ExpenseData expenseData;

    public MyExpenseAdapter(ArrayList<ExpenseData> listBeneficiary, Context mContext) {
            this.listBeneficiary = listBeneficiary;
            this.mContext = mContext;
            this.mFilteredList = listBeneficiary;
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            // inflating recycler item view
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.single_expense_item, parent, false);

            return new MyViewHolder(itemView);
        }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        holder.tvExpenseReportId.setText(String.valueOf(listBeneficiary.get(position).getExpenseId() ));
        holder.tvExpenseReportType.setText(listBeneficiary.get(position).getExpenseType());
        holder.tvExpenseReportAmount.setText(String.valueOf( listBeneficiary.get(position).getExpenseAmount() ));
        String dateStr = listBeneficiary.get( position ).getExpenseDate().toString();

        String[]dateParts = dateStr.split("/");
        try {
            expyear = safeParseInt(dateParts[2]);
            expmonth = safeParseInt(dateParts[1]);
            expday = safeParseInt(dateParts[0]);
        } catch (Exception e) {
            Log.d( TAG, "onBindViewHolder: Error in Date Parsing  " );
        }
        // parsing the hour and minute int values from Time String HH:MM
        String timeStr = listBeneficiary.get( position).getExpenseTime().toString();
        String[] timeParts = timeStr.split( ":" );
        try {
            exphour = safeParseInt( timeParts[0] );
            expminute = safeParseInt( timeParts[1] );
        }catch (Exception e)
        {
            e.printStackTrace();
        }

        String Date = "";
        if(expmonth<=9 && expday<=9)
        {
            Date = "0"+expday +"/0"+expmonth +"/"+expyear ;
        }
        if(expmonth<=9 && expday>9){
            Date = expday +"/0"+expmonth +"/"+expyear ;
        }
        if(expmonth>9 && expday<=9){
            Date = "0"+expday +"/"+expmonth +"/"+expyear ;
        }
        else {
            Date = expday +"/"+expmonth +"/"+expyear ;
        }
     /*   String Time ="";
        String format = timePickerFragment.checkAmPm();
        if(format.equals( "AM" ) && hour<=9){
             Time =  "0"+hour+":"+minute +format;
        }
        if(format.equals( "AM" ) && hour>9 && hour<=12){
             Time =  hour+":"+minute +format;
        }
        if(format.equals( "PM" ) && hour>12){
             Time =  "0"+hour+":"+minute +format;
        }
        if(format.equals( "AM" ) && hour==0){
             Time =  "0"+hour+":"+minute +format;
        }
*/       String Time = exphour+":"+expminute;
         holder.tvExpenseReportDate.setText(Date);
         holder.tvExpenseReportTime.setText(Time);
         holder.tvExpenseReportDesc.setText( listBeneficiary.get( position ).getExpenseDesc());

    }

        @Override
        public int getItemCount() {
            if(mFilteredList==null)
                return 0;
            else
               return mFilteredList.size();

        }


    public class MyViewHolder extends RecyclerView.ViewHolder implements  View.OnClickListener {
        public AppCompatTextView tvExpenseReportId;
        public AppCompatTextView tvExpenseReportType;
        public AppCompatTextView tvExpenseReportAmount;
        public AppCompatTextView tvExpenseReportDate;
        public AppCompatTextView tvExpenseReportTime;
        public AppCompatTextView tvExpenseReportDesc;

        public MyViewHolder(View view) {
            super(view);
            view.setOnClickListener(this);
            tvExpenseReportId = (AppCompatTextView)view.findViewById( R.id.tvExpenseReportId );
            tvExpenseReportType = (AppCompatTextView)view.findViewById( R.id.tvExpenseReportType );
            tvExpenseReportAmount = (AppCompatTextView)view.findViewById( R.id.tvExpenseReportAmount );
            tvExpenseReportDate = (AppCompatTextView)view.findViewById( R.id.tvExpenseReportDate );
            tvExpenseReportTime = (AppCompatTextView)view.findViewById( R.id.tvExpenseReportTime );
            tvExpenseReportDesc = (AppCompatTextView)view.findViewById( R.id.tvExpenseReportDesc);

        }

        @Override
        public void onClick(View v) {
            expenseData= new ExpenseData( listBeneficiary.get( getPosition()).getExpenseId(), listBeneficiary.get( getPosition()).getExpenseType(), listBeneficiary.get( getPosition()).getExpenseAmount(), listBeneficiary.get( getPosition() ).getExpenseDate(), listBeneficiary.get(getPosition()).getExpenseTime(), listBeneficiary.get( getPosition()).getExpenseDesc());
            ActivityOptions optionsExpense = ActivityOptions.makeSceneTransitionAnimation( (Activity) mContext);
            Intent i = new Intent( mContext, ExpenseItemsActivity.class );
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            i.putExtra( "amount",expenseData.getExpenseAmount());
            i.putExtra( "Date", expenseData.getExpenseDate() );
            i.putExtra( "Time", expenseData.getExpenseTime());
            i.putExtra( "Type", expenseData.getExpenseType());
            i.putExtra( "incomeId", expenseData.getExpenseId() );
            i.putExtra( "expenseDesc", expenseData.getExpenseDesc() );
      //      Log.d( TAG, "onClick: "+ expenseData.getInputAmount()+incomeData.getIncomeTime()+incomeData.getIncomeId() );
            mContext.startActivity( i, optionsExpense.toBundle() );
        }
    }

    public int safeParseInt(String number) throws Exception {
        if(number != null) {
            return Integer.parseInt(number.trim());
        } else {
            throw new NullPointerException("Date string is invalid");
        }
    }
}




