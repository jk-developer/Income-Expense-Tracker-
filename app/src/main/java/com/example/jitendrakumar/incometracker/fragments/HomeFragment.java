package com.example.jitendrakumar.incometracker.fragments;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jitendrakumar.incometracker.R;

public class HomeFragment extends Fragment {
    TextView tvHello;
    public String id;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate( R.layout.fragment_home,container, false );
        tvHello = (TextView) view.findViewById( R.id.tvHello );
        tvHello.setVisibility(View.VISIBLE);

        Bundle bundle = getArguments();
        if(bundle != null)
        {
           id = bundle.getString("id");
           String username = bundle.getString( "username" );
           //Toast.makeText( getContext(), id+username, Toast.LENGTH_SHORT ).show();
           tvHello.setText( "Hello, "+username +"Your Unique Id : "+id );

           Bundle income = new Bundle();
           income.putString("id", id.toString());
           FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
           FragmentTransaction fragmentTransaction  = getFragmentManager().beginTransaction();
           IncomeFragment incomeFragment = new IncomeFragment();
           incomeFragment.setArguments( income );
           fragmentTransaction.replace( R.id.fragment_container,incomeFragment);
           fragmentTransaction.commit();

           Bundle expense = new Bundle();
           expense.putString( "userid", id.toString() );
           FragmentManager expenseFragmentManager = getActivity().getSupportFragmentManager();
           FragmentTransaction expenseFragmentTransaction  = getFragmentManager().beginTransaction();
           ExpenseFragment expenseFragment = new ExpenseFragment();
           expenseFragment.setArguments( expense );
           expenseFragmentTransaction.replace( R.id.fragment_container,expenseFragment);
           expenseFragmentTransaction.commit();

            Bundle expense_report = new Bundle();
            expense_report.putString( "userid", id.toString() );
            FragmentManager expenseReportFragmentManager = getActivity().getSupportFragmentManager();
            FragmentTransaction expenseReportFragmentTransaction  = getFragmentManager().beginTransaction();
            ExpenseReportFragment expenseReportFragment = new ExpenseReportFragment();
            expenseReportFragment.setArguments( expense_report );
            expenseReportFragmentTransaction.replace( R.id.fragment_container,expenseReportFragment);
            expenseReportFragmentTransaction.commit();

            Bundle income_report = new Bundle();
            income_report.putString( "userid", id.toString() );
            FragmentManager incomeFragmentManager = getActivity().getSupportFragmentManager();
            FragmentTransaction incomeFragmentTransaction  = getFragmentManager().beginTransaction();
            ExpenseFragment incomeReportFragment = new ExpenseFragment();
            incomeReportFragment.setArguments( income_report );
            incomeFragmentTransaction.replace( R.id.fragment_container,incomeReportFragment);
            incomeFragmentTransaction.commit();


       }

        return view;
    }
}
