package com.example.jitendrakumar.incometracker.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.jitendrakumar.incometracker.R;


public class IncomeUpdateFragment extends Fragment {

    EditText etIncomeId;
    Button btnUpdate;
    public String updateid;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate( R.layout.fragment_income_update2, container, false );
        etIncomeId = (EditText)view.findViewById( R.id.etIncomeId );
        btnUpdate = (Button)view.findViewById( R.id.btnUpdate );

        btnUpdate.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateid = etIncomeId.getText().toString();
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace( R.id.fragment_container, new IncomeFragment() );
                fragmentTransaction.addToBackStack( null );
                fragmentTransaction.commit();
            }


        } );
        return view;

    }
    public String getIncomeId(){
        return updateid;
    }



}
