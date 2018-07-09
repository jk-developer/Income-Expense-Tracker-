package com.example.jitendrakumar.incometracker.fragments;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jitendrakumar.incometracker.R;
import com.example.jitendrakumar.incometracker.activities.MainActivity;
import com.example.jitendrakumar.incometracker.activities.MyAdapter;
import com.example.jitendrakumar.incometracker.helper.HomeData;

import java.util.ArrayList;

public class HomeFragment extends Fragment {
    TextView tvHello;
    public String id;
    LinearLayout homeLayout,incomeLayout,expenseLayout,incomeReportLayout,expenseReportLayout,aboutLayout,settingLayout,loginLayout;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate( R.layout.fragment_home,container, false );
        tvHello = (TextView) view.findViewById( R.id.tvHello );
        homeLayout = (LinearLayout) view.findViewById( R.id.homeLayout );
        incomeLayout = (LinearLayout) view.findViewById( R.id.incomeLayout );
        expenseLayout = (LinearLayout) view.findViewById( R.id.expenseLayout );
        incomeReportLayout = (LinearLayout) view.findViewById( R.id.incomeReportLayout );
        expenseReportLayout = (LinearLayout) view.findViewById( R.id.expenseReportLayout );
        loginLayout = (LinearLayout) view.findViewById( R.id.loginLayout );
        aboutLayout = (LinearLayout) view.findViewById( R.id.aboutLayout );
        settingLayout = (LinearLayout) view.findViewById( R.id.settingLayout );
        tvHello.setVisibility(View.VISIBLE);

        homeLayout.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText( getContext(), "home Layout clicked ", Toast.LENGTH_SHORT ).show();
                FragmentTransaction fragmentTransaction  = getFragmentManager().beginTransaction();
                fragmentTransaction.replace( R.id.fragment_container, new HomeFragment());
                fragmentTransaction.commit();
            }
        } );

        loginLayout.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText( getContext(), "login Layout clicked ", Toast.LENGTH_SHORT ).show();
                FragmentTransaction fragmentTransaction  = getFragmentManager().beginTransaction();
                fragmentTransaction.replace( R.id.fragment_container, new LoginFragment());
                fragmentTransaction.commit();
            }
        } );

        incomeLayout.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText( getContext(), "login Layout clicked ", Toast.LENGTH_SHORT ).show();
                FragmentTransaction fragmentTransaction  = getFragmentManager().beginTransaction();
                fragmentTransaction.replace( R.id.fragment_container, new IncomeFragment());
                fragmentTransaction.commit();
            }
        } );

        expenseLayout.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText( getContext(), "login Layout clicked ", Toast.LENGTH_SHORT ).show();
                FragmentTransaction fragmentTransaction  = getFragmentManager().beginTransaction();
                fragmentTransaction.replace( R.id.fragment_container, new ExpenseFragment());
                fragmentTransaction.commit();
            }
        } );

        incomeReportLayout.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText( getContext(), "login Layout clicked ", Toast.LENGTH_SHORT ).show();
                FragmentTransaction fragmentTransaction  = getFragmentManager().beginTransaction();
                fragmentTransaction.replace( R.id.fragment_container, new IncomeReportFragment());
                fragmentTransaction.commit();
            }
        } );

        expenseReportLayout.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText( getContext(), "login Layout clicked ", Toast.LENGTH_SHORT ).show();
                FragmentTransaction fragmentTransaction  = getFragmentManager().beginTransaction();
                fragmentTransaction.replace( R.id.fragment_container, new ExpenseReportFragment());
                fragmentTransaction.commit();
            }
        } );

        aboutLayout.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText( getContext(), "login Layout clicked ", Toast.LENGTH_SHORT ).show();
                FragmentTransaction fragmentTransaction  = getFragmentManager().beginTransaction();
                fragmentTransaction.replace( R.id.fragment_container, new AboutFragment());
                fragmentTransaction.commit();
            }
        } );

        settingLayout.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText( getContext(), "login Layout clicked ", Toast.LENGTH_SHORT ).show();
                FragmentTransaction fragmentTransaction  = getFragmentManager().beginTransaction();
                fragmentTransaction.replace( R.id.fragment_container, new SettingFragment());
                fragmentTransaction.commit();
            }
        } );

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
            Toast.makeText( getActivity(), "after income fragment "+id, Toast.LENGTH_SHORT ).show();

            Bundle income_report = new Bundle();
            income_report.putString( "userid", id.toString() );
            FragmentManager incomeFragmentManager = getActivity().getSupportFragmentManager();
            FragmentTransaction incomeFragmentTransaction  = getFragmentManager().beginTransaction();
            ExpenseFragment incomeReportFragment = new ExpenseFragment();
            incomeReportFragment.setArguments( income_report );
            incomeFragmentTransaction.replace( R.id.fragment_container,incomeReportFragment);
            incomeFragmentTransaction.commit();

            Toast.makeText( getActivity(), "after increport fragment "+id, Toast.LENGTH_SHORT ).show();

           Bundle expense = new Bundle();
           expense.putString( "userid", id.toString() );
           FragmentManager expenseFragmentManager = getActivity().getSupportFragmentManager();
           FragmentTransaction expenseFragmentTransaction  = getFragmentManager().beginTransaction();
           ExpenseFragment expenseFragment = new ExpenseFragment();
           expenseFragment.setArguments( expense );
           expenseFragmentTransaction.replace( R.id.fragment_container,expenseFragment);
           expenseFragmentTransaction.commit();
            Toast.makeText( getActivity(), "after expense fragment "+id, Toast.LENGTH_SHORT ).show();

            Bundle expense_report = new Bundle();
            expense_report.putString( "userid", id.toString() );
            FragmentManager expenseReportFragmentManager = getActivity().getSupportFragmentManager();
            FragmentTransaction expenseReportFragmentTransaction  = getFragmentManager().beginTransaction();
            ExpenseReportFragment expenseReportFragment = new ExpenseReportFragment();
            expenseReportFragment.setArguments( expense_report );
            expenseReportFragmentTransaction.replace( R.id.fragment_container,expenseReportFragment);
            expenseReportFragmentTransaction.commit();

             Toast.makeText( getActivity(), "after exreport fragment "+id, Toast.LENGTH_SHORT ).show();

                  }

        return view;
    }
}
