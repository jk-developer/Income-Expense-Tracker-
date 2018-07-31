package com.example.jitendrakumar.incometracker.fragments;

import android.app.ActivityOptions;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jitendrakumar.incometracker.R;
import com.example.jitendrakumar.incometracker.activities.AboutActivity;
import com.example.jitendrakumar.incometracker.activities.AddBorrowActivity;
import com.example.jitendrakumar.incometracker.activities.AddExpenseActivity;
import com.example.jitendrakumar.incometracker.activities.AddIncomeActivity;
import com.example.jitendrakumar.incometracker.activities.AddLendActivity;
import com.example.jitendrakumar.incometracker.activities.BorrowActivity;
import com.example.jitendrakumar.incometracker.activities.ExpenseReportActivity;
import com.example.jitendrakumar.incometracker.activities.IncomeReportActivity;
import com.example.jitendrakumar.incometracker.activities.LendActivity;
import com.example.jitendrakumar.incometracker.activities.TodoActivity;
import com.example.jitendrakumar.incometracker.database.ExpenseDatabaseHelper;
import com.example.jitendrakumar.incometracker.database.IncomeDatabaseHelper;
import com.example.jitendrakumar.incometracker.database.BorrowDatabaseHelper;
import com.example.jitendrakumar.incometracker.database.LendDatabaseHelper;
import com.example.jitendrakumar.incometracker.helper.SessionManagement;

import java.util.Calendar;

public class HomeFragment extends Fragment {
    TextView tvHello;
    public String id;
    LinearLayout todoLayout,incomeLayout,expenseLayout,incomeReportLayout,expenseReportLayout, borrowReportLayout, lendReportLayout, loginLayout,borrowLayout, lendLayout,aboutLayout;
    SessionManagement ses;
    TextView incomeTotal,expenseTotal,savingTotal,tvHomeTitle, homeTime, homeIncome;
    IncomeDatabaseHelper incomeDatabaseHelper;
    ExpenseDatabaseHelper expenseDatabaseHelper;
    BorrowDatabaseHelper borrowDatabaseHelper;
    LendDatabaseHelper lendDatabaseHelper;
    float todaysAmount, thisMonthAmount;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate( R.layout.fragment_home,container, false );
        tvHello = (TextView) view.findViewById( R.id.tvHello );
        incomeLayout = (LinearLayout) view.findViewById( R.id.incomeLayout );
        expenseLayout = (LinearLayout) view.findViewById( R.id.expenseLayout );
        incomeTotal = (TextView) view.findViewById( R.id.incomeTotal );
        expenseTotal = (TextView) view.findViewById( R.id.expenseTotal );
        savingTotal = (TextView)view.findViewById( R.id.savingTotal );
        tvHomeTitle = (TextView)view.findViewById( R.id.tvHomeTitle );
        homeIncome = (TextView) view.findViewById( R.id.homeIncome );

        incomeDatabaseHelper = new IncomeDatabaseHelper( getContext() );
        expenseDatabaseHelper = new ExpenseDatabaseHelper( getContext() );
        borrowDatabaseHelper = new BorrowDatabaseHelper( getContext() );
        lendDatabaseHelper = new LendDatabaseHelper( getContext() );

        incomeTotal.setText( String.valueOf( incomeDatabaseHelper.getTotalIncome()+" Rs" ) );
        expenseTotal.setText( String.valueOf( expenseDatabaseHelper.getTotalExpense()+" Rs"));
        savingTotal.setText( String.valueOf( incomeDatabaseHelper.getTotalIncome()-expenseDatabaseHelper.getTotalExpense())+" Rs" );
     //   paidtoTotal.setText( String.valueOf( borrowDatabaseHelper.getTotalPaidTo())+" Rs" );
    //    takenTotal.setText( String.valueOf( lendDatabaseHelper.getTotalTaken())+" Rs" );

        Calendar cal = Calendar.getInstance();
        final int year = cal.get( Calendar.YEAR );
        final int month = cal.get( Calendar.MONTH );
        final int day = cal.get( Calendar.DAY_OF_MONTH );

        try {
            todaysAmount = incomeDatabaseHelper.getTodaysIncome(String.valueOf( year ), String.valueOf( month ) , String.valueOf( day ) );
        } catch (Exception e) {
            e.printStackTrace();
        }
        incomeTotal.setText( String.valueOf( todaysAmount ) );

        incomeTotal.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation( getActivity() );
                Intent i = new Intent( getActivity(), IncomeReportActivity.class );
               startActivity( i, options.toBundle() );
            }
        } );

        expenseTotal.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation( getActivity() );
                Intent intent = new Intent( getActivity(), ExpenseReportActivity.class );
                startActivity( intent, options.toBundle() );
            }
        } );

        tvHomeTitle.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvHomeTitle.setText( R.string.homeTitleThisMonth );
                try {
                    thisMonthAmount = incomeDatabaseHelper.getThisMonthData( String.valueOf( month+1 ) );

                } catch (Exception e) {
                    e.printStackTrace();
                }
                incomeTotal.setText( String.valueOf( thisMonthAmount ) );
            }
        } );

     /*   paidtoTotal.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation( getActivity() );
                Intent in = new Intent( getActivity(), BorrowActivity.class );
                startActivity( in, options.toBundle() );
            }
        } );

        takenTotal.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation( getActivity() );
                Intent lend = new Intent( getActivity(), LendActivity.class );
                startActivity( lend, options.toBundle() );
            }
        } );
*/
        ses = new SessionManagement( getContext() );
    //    incomeTotal.setText( String.valueOf(incomeReportActivity.getTotalIncome()));
        tvHello.setVisibility(View.VISIBLE);

   /*    todoLayout.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.addToBackStack( null );
                fragmentTransaction.commit();
                Intent i =  new Intent( getActivity(), TodoActivity.class );
                startActivity( i );
            }

        } );


        loginLayout.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 String username = ses.getUserName();
                 if(ses.getUserName()!=null){
                     Toast.makeText( getActivity(), ses.getUserName()+", you are already Logged in!!!" , Toast.LENGTH_SHORT).show();
                 }
                 else{
                     Toast.makeText( getContext(), "login Layout clicked ", Toast.LENGTH_SHORT ).show();
                     FragmentTransaction fragmentTransaction  = getFragmentManager().beginTransaction();
                     fragmentTransaction.replace( R.id.fragment_container, new LoginFragment());
                     fragmentTransaction.addToBackStack( null );
                     fragmentTransaction.commit();
                 }

            }
        } );
*/
        incomeLayout.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 if (ses.getUserName()!=null){
                     Intent addIncome = new Intent( getActivity(), AddIncomeActivity.class );
                     startActivity( addIncome );
                 }
                else{
                    Toast.makeText( getActivity(), "Please First login into your account!!!",Toast.LENGTH_SHORT ).show();
                }

            }
        } );

        expenseLayout.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ses.getUserName()!=null)
                {
                    Intent addExpense = new Intent( getActivity(), AddExpenseActivity.class );
                    startActivity( addExpense );
                }else{
                    Toast.makeText( getActivity(), "Please First login into your account!!!",Toast.LENGTH_SHORT ).show();
                }

            }
        } );
/*
        incomeReportLayout.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ses.getUserName()!=null)
                {
                    Toast.makeText( getContext(), "login Layout clicked ", Toast.LENGTH_SHORT ).show();
                    FragmentTransaction fragmentTransaction  = getFragmentManager().beginTransaction();
                    fragmentTransaction.replace( R.id.fragment_container, new IncomeReportFragment());
                    fragmentTransaction.addToBackStack( null );
                    fragmentTransaction.commit();
                }else{
                    Toast.makeText( getActivity(), "Please First login into your account!!!",Toast.LENGTH_SHORT ).show();
                }

            }
        } );

        expenseReportLayout.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ses.getUserName()!=null)
                {
                    Toast.makeText( getContext(), "login Layout clicked ", Toast.LENGTH_SHORT ).show();
                    FragmentTransaction fragmentTransaction  = getFragmentManager().beginTransaction();
                    fragmentTransaction.replace( R.id.fragment_container, new ExpenseReportFragment());
                    fragmentTransaction.addToBackStack( null );
                    fragmentTransaction.commit();
                }else{
                    Toast.makeText( getActivity(), "Please First login into your account!!!",Toast.LENGTH_SHORT ).show();
                }

            }
        } );

        borrowReportLayout.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ses.getUserName()!=null)
                {
                    FragmentTransaction fragmentTransaction  = getFragmentManager().beginTransaction();
                    fragmentTransaction.replace( R.id.fragment_container, new BorrowReportFragment());
                    fragmentTransaction.addToBackStack( null );
                    fragmentTransaction.commit();
                }else{
                    Toast.makeText( getActivity(), "Please First login into your account!!!",Toast.LENGTH_SHORT ).show();
                }

            }
        } );

        lendReportLayout.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ses.getUserName()!=null)
                {
                    FragmentTransaction fragmentTransaction  = getFragmentManager().beginTransaction();
                    fragmentTransaction.replace( R.id.fragment_container, new LendReportFragment());
                    fragmentTransaction.addToBackStack( null );
                    fragmentTransaction.commit();

                }else{
                    Toast.makeText( getActivity(), "Please First login into your account!!!",Toast.LENGTH_SHORT ).show();
                }

            }
        } );

        borrowLayout.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ses.getUserName()!=null){
                    Intent addBorrow = new Intent( getActivity(), AddBorrowActivity.class );
                    startActivity( addBorrow );
                }
                else{
                    Toast.makeText( getActivity(), "Please First login into your account!!!",Toast.LENGTH_SHORT ).show();
                }

            }
        } );

    //    lendLayout.setOnClickListener( new View.OnClickListener() {
        //    @Override
            public void onClick(View v) {
                if (ses.getUserName()!=null){
                    Intent addLend = new Intent( getActivity(), AddLendActivity.class );
                    startActivity( addLend );
                }
                else{
                    Toast.makeText( getActivity(), "Please First login into your account!!!",Toast.LENGTH_SHORT ).show();
                }

            }
        } );

        aboutLayout.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation( getActivity() );
                   Intent aboutActivity = new Intent( getActivity(), AboutActivity.class );
                   startActivity(aboutActivity, options.toBundle()  );

            }
        } );
*/
        if(ses.getUserName()!=null)
        {
            tvHello.setText( "Welcome, " + ses.getUserName() );
        }
        else
        { }
            return view;
    }
    public String findId(){
        return id;
    }


}
