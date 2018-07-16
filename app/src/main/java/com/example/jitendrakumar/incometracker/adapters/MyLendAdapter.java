package com.example.jitendrakumar.incometracker.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
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
            Intent i = new Intent( mContext, LendItemsActivity.class );
            i.putExtra( "amount", lendData.getLamount() );
            i.putExtra( "date", lendData.getLdate() );
            i.putExtra( "desc", lendData.getLdesc() );
            i.putExtra( "person", lendData.getLperson() );
            i.putExtra( "id", lendData.getLid() );
            //    Log.d( TAG, "onClick: "+ incomeData.getInputAmount()+incomeData.getIncomeTime()+incomeData.getIncomeId() );
            mContext.startActivity( i );
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
        holder.tvLendDataDate.setText( listBeneficiary.get( position ).getLdate() );
    }


    @Override
    public int getItemCount() {
        return mFilteredList.size();
    }


}
