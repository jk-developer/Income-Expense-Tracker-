package com.example.jitendrakumar.incometracker.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.jitendrakumar.incometracker.R;
import com.example.jitendrakumar.incometracker.activities.BorrowActivity;
import com.example.jitendrakumar.incometracker.activities.LendActivity;

public class BorrowReportFragment extends Fragment {
    EditText etLendReportMonthTo, etLendReportMonthFrom;
    Button btnViewLendReport;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate( R.layout.fragment_borrow_report, container, false );
        etLendReportMonthFrom = (EditText) view.findViewById( R.id.etLendReportMonthFrom );
        etLendReportMonthTo = (EditText) view.findViewById( R.id.etLendReportMonthTo );
        btnViewLendReport = (Button) view.findViewById( R.id.btnViewLendReport );

        btnViewLendReport.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText( getActivity(), "Borrow Report is clicked", Toast.LENGTH_SHORT ).show();
                Intent borrowActivity = new Intent( getActivity(), BorrowActivity.class );
                startActivity( borrowActivity );
            }
        } );
        return view;
    }

}