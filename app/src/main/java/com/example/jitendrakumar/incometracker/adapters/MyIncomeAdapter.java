package com.example.jitendrakumar.incometracker.adapters;

import android.content.Context;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.jitendrakumar.incometracker.R;
import com.example.jitendrakumar.incometracker.models.IncomeData;

import java.util.ArrayList;

public class MyIncomeAdapter extends RecyclerView.Adapter<MyIncomeAdapter.BeneficiaryViewHolder>  {

    private ArrayList<IncomeData> listBeneficiary;
    private Context mContext;
    private ArrayList<IncomeData> mFilteredList;
    public static final String TAG = "res";


    public MyIncomeAdapter(ArrayList<IncomeData> listBeneficiary, Context mContext) {
        this.listBeneficiary = listBeneficiary;
        this.mContext = mContext;
        this.mFilteredList = listBeneficiary;


    }

    public class BeneficiaryViewHolder extends RecyclerView.ViewHolder {

        public AppCompatTextView tvIncomeReportId;
        public AppCompatTextView tvIncomeReportType;
        public AppCompatTextView tvIncomeReportAmount;
        public AppCompatTextView tvIncomeReportDate;
        public AppCompatTextView tvIncomeReportTime;

        public BeneficiaryViewHolder(View view) {
            super(view);
            tvIncomeReportId = (AppCompatTextView)view.findViewById( R.id.tvIncomeReportId );
            tvIncomeReportType = (AppCompatTextView)view.findViewById( R.id.tvIncomeReportType );
            tvIncomeReportAmount = (AppCompatTextView)view.findViewById( R.id.tvIncomeReportAmount );
            tvIncomeReportDate = (AppCompatTextView)view.findViewById( R.id.tvIncomeReportDate );
            tvIncomeReportTime = (AppCompatTextView)view.findViewById( R.id.tvIncomeReportTime );

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
        holder.tvIncomeReportId.setText(String.valueOf( listBeneficiary.get(position).getIncomeId() ));
        holder.tvIncomeReportType.setText(listBeneficiary.get(position).getInputType());
        holder.tvIncomeReportAmount.setText(String.valueOf( listBeneficiary.get(position).getInputAmount() ));
        int year = Integer.parseInt( listBeneficiary.get( position ).getIncomeDate().trim().substring( 6 ) );
        Log.d( TAG, "onBindViewHolder: year"+year );
        Log.d( TAG, "onBindViewHolder: length"+listBeneficiary.get( position ).getIncomeDate().length() );
        int month = Integer.parseInt( listBeneficiary.get( position ).getIncomeDate().trim().substring( 3,5 ) );
        int day = Integer.parseInt( listBeneficiary.get( position ).getIncomeDate().trim().substring( 0,2 ) );
        int hour = Integer.parseInt( listBeneficiary.get( position ).getIncomeTime().trim().substring( 0,2 ) );
        int minute = Integer.parseInt( listBeneficiary.get( position ).getIncomeTime().trim().substring( 3 ) );
        String Date = Integer.toString( day )+"/"+Integer.toString( month )+"/"+Integer.toString( year );
        String Time = Integer.toString( hour )+":"+Integer.toString( minute );
        holder.tvIncomeReportDate.setText(Date);
        holder.tvIncomeReportTime.setText(Time);

    }

    @Override
    public int getItemCount() {
        return mFilteredList.size();
    }
}


