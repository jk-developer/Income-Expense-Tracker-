package com.example.jitendrakumar.incometracker.activities;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.jitendrakumar.incometracker.R;
import com.example.jitendrakumar.incometracker.helper.HomeData;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder>{

    public ImageView ivIcon;
    public TextView tvTitle;
    public final String TAG = "RES";
    private ArrayList<HomeData> home_list = new ArrayList<>( ) ;

    public MyAdapter() {

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater li = LayoutInflater.from( parent.getContext());
        View itemView = li.inflate( R.layout.single_list_item, parent,false );

        return new ViewHolder( itemView );
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.iconImage.setImageResource( HomeData.title_image[position] );
        holder.tvTitle.setText( HomeData.title[position] );
    }

    private HomeData getItem(int position) {
        return null;
    }

    @Override
    public int getItemCount() {
        return HomeData.title.length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView tvTitle;
        ImageView iconImage;

        public ViewHolder(View itemView) {
            super( itemView );
            tvTitle = (TextView) itemView.findViewById( R.id.tvTitle );
            iconImage  = (ImageView) itemView.findViewById( R.id.iconImage );
        }

        @Override
        public void onClick(View v) {

        }
    }


}

