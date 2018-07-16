package com.example.jitendrakumar.incometracker.adapters;

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
import com.example.jitendrakumar.incometracker.activities.BorrowItemsActivity;
import com.example.jitendrakumar.incometracker.fragments.date_time_fragment.TimePickerFragment;
import com.example.jitendrakumar.incometracker.models.BorrowData;

import java.util.ArrayList;

public class MyBorrowAdapter extends RecyclerView.Adapter<MyBorrowAdapter.BorrowViewHolder> {

    private ArrayList<BorrowData> listBeneficiary;
    private Context mContext;
    private ArrayList<BorrowData> mFilteredList;
    public static final String TAG = "res";
    BorrowData borrowData;


    public MyBorrowAdapter(ArrayList<BorrowData> listBeneficiary, Context mContext) {
        this.listBeneficiary = listBeneficiary;
        this.mContext = mContext;
        this.mFilteredList = listBeneficiary;
    }

    public class BorrowViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public AppCompatTextView tvBorrowDataId;
        public AppCompatTextView tvBorrowDataPersonName;
        public AppCompatTextView tvBorrowDataAmount;
        public AppCompatTextView tvBorrowDataDate;
        public AppCompatTextView tvBorrowDataDescription;

        public BorrowViewHolder(View view) {
            super(view);
            view.setOnClickListener(this);
            tvBorrowDataId = (AppCompatTextView)view.findViewById( R.id.tvBorrowDataId);
            tvBorrowDataAmount = (AppCompatTextView)view.findViewById( R.id.tvBorrowDataAmount);
            tvBorrowDataDate = (AppCompatTextView)view.findViewById( R.id.tvBorrowDataDate );
            tvBorrowDataDescription = (AppCompatTextView)view.findViewById( R.id.tvBorrowDataDescription );
            tvBorrowDataPersonName = (AppCompatTextView)view.findViewById( R.id.tvBorrowDataPersonName );

        }

        @Override
        public void onClick(View v) {
            borrowData = new BorrowData( listBeneficiary.get( getPosition()).getbId(), listBeneficiary.get( getPosition()).getbAmount(), listBeneficiary.get( getPosition()).getbPerson(), listBeneficiary.get( getPosition() ).getbDesc(), listBeneficiary.get(getPosition()).getbDate());
           // Toast.makeText( mContext, getPosition()+ borrowData.getInputType()+ incomeData.getInputAmount()+"is clicked", Toast.LENGTH_SHORT).show();
            Intent i = new Intent( mContext, BorrowItemsActivity.class );
            i.putExtra( "amount",borrowData.getbAmount());
            i.putExtra( "date", borrowData.getbDate() );
            i.putExtra( "desc", borrowData.getbDesc());
            i.putExtra( "person", borrowData.getbPerson());
            i.putExtra( "id", borrowData.getbId() );
        //    Log.d( TAG, "onClick: "+ incomeData.getInputAmount()+incomeData.getIncomeTime()+incomeData.getIncomeId() );
            mContext.startActivity( i );
        }
    }

    @Override
    public BorrowViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // inflating recycler item view
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.single_borrow_item, parent, false);

        return new BorrowViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull BorrowViewHolder holder, int position) {
        holder.tvBorrowDataId.setText(String.valueOf( listBeneficiary.get(position).getbId()));
        holder.tvBorrowDataAmount.setText(String.valueOf(listBeneficiary.get(position).getbAmount()));
        holder.tvBorrowDataPersonName.setText(listBeneficiary.get(position).getbPerson());
        holder.tvBorrowDataDescription.setText( listBeneficiary.get( position ).getbDesc() );
        holder.tvBorrowDataDate.setText( listBeneficiary.get( position ).getbDate() );
    }


    @Override
    public int getItemCount() {
        return mFilteredList.size();
    }


}
