package com.example.jitendrakumar.incometracker.fragments;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jitendrakumar.incometracker.R;

public class HomeFragment extends Fragment {
    TextView tvHello;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate( R.layout.fragment_home,container, false );
        tvHello = (TextView) view.findViewById( R.id.tvHello );
        tvHello.setVisibility(View.VISIBLE);
        Bundle bundle = getArguments();
       if(bundle != null)
       {
           String id = bundle.getString("id");
           String username = bundle.getString( "username" );
           //Toast.makeText( getContext(), id+username, Toast.LENGTH_SHORT ).show();
           tvHello.setText( "Hello, "+username +"Your Unique Id : "+id );
       }

        return view;
    }
}
