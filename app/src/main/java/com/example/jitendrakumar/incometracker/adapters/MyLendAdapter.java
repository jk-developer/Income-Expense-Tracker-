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

import com.example.jitendrakumar.incometracker.R;
import com.example.jitendrakumar.incometracker.activities.LendItemsActivity;
import com.example.jitendrakumar.incometracker.models.LendData;

import java.util.ArrayList;

public class MyLendAdapter extends RecyclerView.Adapter<MyLendAdapter.LendViewHolder> {

    private ArrayList<LendData> listBeneficiary;
    private Context mContext;
    private ArrayList<LendData> mFilteredList;
    public static final String TAG = "res";
    LendData lendData;
    private int y,m,d;



    public MyLendAdapter(ArrayList<LendData> listBeneficiary, Context mContext) {
        this.listBeneficiary = listBeneficiary;
        this.mContext = mContext;
        this.mFilteredList = listBeneficiary;
    }

    public class LendViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public AppCompatTextView tvLendDataId;
        public AppCompatTextView tvLendDataPersonName;
        public AppCompatTextView tvLendDataAmount;
        public AppCompatTextView tvLendDataDate;
        public AppCompatTextView tvLendDataDescription;

        public LendViewHolder(View view) {
            super( view );
            view.setOnClickListener( this );
            tvLendDataId = (AppCompatTextView) view.findViewById( R.id.tvLendDataId );
            tvLendDataAmount = (AppCompatTextView) view.findViewById( R.id.tvLendDataAmount );
            tvLendDataDate = (AppCompatTextView) view.findViewById( R.id.tvLendDataDate );
            tvLendDataDescription = (AppCompatTextView) view.findViewById( R.id.tvLendDataDescription );
            tvLendDataPersonName = (AppCompatTextView) view.findViewById( R.id.tvLendDataPersonName );

        }

        @Override
        public void onClick(View v) {
            lendData = new LendData( listBeneficiary.get( getPosition() ).getLid(), listBeneficiary.get( getPosition() ).getLamount(), listBeneficiary.get( getPosition() ).getLperson(), listBeneficiary.get( getPosition() ).getLdesc(), listBeneficiary.get( getPosition() ).getLdate() );
            // Toast.makeText( mContext, getPosition()+ borrowData.getInputType()+ incomeData.getInputAmount()+"is clicked", Toast.LENGTH_SHORT).show();
            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation( (Activity) mContext );
            Intent i = new Intent( mContext, LendItemsActivity.class );
            i.putExtra( "amount", lendData.getLamount() );
            i.putExtra( "date", lendData.getLdate() );
            i.putExtra( "desc", lendData.getLdesc() );
            i.putExtra( "person", lendData.getLperson() );
            i.putExtra( "id", lendData.getLid() );
            //    Log.d( TAG, "onClick: "+ incomeData.getInputAmount()+incomeData.getIncomeTime()+incomeData.getIncomeId() );
            mContext.startActivity( i, options.toBundle() );
        }
    }

    @Override
    public LendViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // inflating recycler item view
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.single_lend_item, parent, false);

        return new LendViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull LendViewHolder holder, int position) {
        holder.tvLendDataId.setText(String.valueOf( listBeneficiary.get(position).getLid()));
        holder.tvLendDataAmount.setText(String.valueOf(listBeneficiary.get(position).getLamount()));
        holder.tvLendDataPersonName.setText(listBeneficiary.get(position).getLperson());
        holder.tvLendDataDescription.setText( listBeneficiary.get( position ).getLdesc() );

        String dateStr = listBeneficiary.get( position ).getLdate().toString();

        String[]dateParts = dateStr.split("/");
        try {
            y = safeParseInt(dateParts[2]);
            m = safeParseInt(dateParts[1]);
            d = safeParseInt(dateParts[0]);
        } catch (Exception e) {
            Log.d( TAG, "onBindViewHolder: Error in Date Parsing  " );
        }

        String Date = "";
        if(m<=9 && d<=9)
        {
            Date = "0"+d +"/0"+m +"/"+y ;
        }
        if(m<=9 && d>9){
            Date = d +"/0"+m +"/"+y ;
        }
        if(m>9 && d<=9){
            Date = "0"+d +"/"+m +"/"+y ;
        }
        else {
            Date = d +"/"+m +"/"+y ;
        }

        holder.tvLendDataDate.setText( Date);
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
