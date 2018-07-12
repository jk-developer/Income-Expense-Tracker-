package com.example.jitendrakumar.incometracker.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.jitendrakumar.incometracker.R;
import com.example.jitendrakumar.incometracker.models.ExpenseData;
import com.example.jitendrakumar.incometracker.models.IncomeData;

import java.util.ArrayList;

public class MyExpenseAdapter extends RecyclerView.Adapter<MyExpenseAdapter.MyViewHolder>  {
       private ArrayList<ExpenseData> listBeneficiary;
        private Context mContext;
        private ArrayList<ExpenseData> mFilteredList;
        public static final String TAG = "error";


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
        holder.tvExpenseReportDate.setText(listBeneficiary.get(position).getExpenseDate());
        holder.tvExpenseReportTime.setText(listBeneficiary.get(position).getExpenseTime());

    }

        @Override
        public int getItemCount() {
            return mFilteredList.size();
        }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public AppCompatTextView tvExpenseReportId;
        public AppCompatTextView tvExpenseReportType;
        public AppCompatTextView tvExpenseReportAmount;
        public AppCompatTextView tvExpenseReportDate;
        public AppCompatTextView tvExpenseReportTime;

        public MyViewHolder(View view) {
            super(view);
            tvExpenseReportId = (AppCompatTextView)view.findViewById( R.id.tvExpenseReportId );
            tvExpenseReportType = (AppCompatTextView)view.findViewById( R.id.tvExpenseReportType );
            tvExpenseReportAmount = (AppCompatTextView)view.findViewById( R.id.tvExpenseReportAmount );
            tvExpenseReportDate = (AppCompatTextView)view.findViewById( R.id.tvExpenseReportDate );
            tvExpenseReportTime = (AppCompatTextView)view.findViewById( R.id.tvExpenseReportTime );

        }
    }
}




