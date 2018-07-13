package com.example.jitendrakumar.incometracker.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jitendrakumar.incometracker.R;
import com.example.jitendrakumar.incometracker.activities.ExpenseReportActivity;
import com.example.jitendrakumar.incometracker.activities.IncomeReportActivity;
import com.example.jitendrakumar.incometracker.activities.TodoActivity;
import com.example.jitendrakumar.incometracker.database.ExpenseDatabaseHelper;
import com.example.jitendrakumar.incometracker.database.IncomeDatabaseHelper;
import com.example.jitendrakumar.incometracker.database.BorrowDatabaseHelper;
import com.example.jitendrakumar.incometracker.database.LendDatabaseHelper;
import com.example.jitendrakumar.incometracker.helper.SessionManagement;

public class HomeFragment extends Fragment {
    TextView tvHello;
    public String id;
    LinearLayout todoLayout,incomeLayout,expenseLayout,incomeReportLayout,expenseReportLayout,aboutLayout,loginLayout,tobePaidLayout, tobeTakenLayout,aboutAppLayout;
    SessionManagement ses;
    TextView incomeTotal,expenseTotal,savingTotal, paidtoTotal, takenTotal;
    IncomeDatabaseHelper incomeDatabaseHelper;
    ExpenseDatabaseHelper expenseDatabaseHelper;
    BorrowDatabaseHelper borrowDatabaseHelper;
    LendDatabaseHelper lendDatabaseHelper;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate( R.layout.fragment_home,container, false );
        tvHello = (TextView) view.findViewById( R.id.tvHello );
        todoLayout = (LinearLayout) view.findViewById( R.id.todoLayout );
        incomeLayout = (LinearLayout) view.findViewById( R.id.incomeLayout );
        expenseLayout = (LinearLayout) view.findViewById( R.id.expenseLayout );
        incomeReportLayout = (LinearLayout) view.findViewById( R.id.incomeReportLayout );
        expenseReportLayout = (LinearLayout) view.findViewById( R.id.expenseReportLayout );
        loginLayout = (LinearLayout) view.findViewById( R.id.loginLayout );
        aboutLayout = (LinearLayout) view.findViewById( R.id.aboutLayout );
        tobePaidLayout = (LinearLayout) view.findViewById( R.id.tobePaidLayout ) ;
        tobeTakenLayout = (LinearLayout) view.findViewById( R.id.tobeTakenLayout );
        aboutAppLayout = (LinearLayout)view.findViewById( R.id.aboutAppLayout );
        incomeTotal = (TextView) view.findViewById( R.id.incomeTotal );
        expenseTotal = (TextView) view.findViewById( R.id.expenseTotal );
        savingTotal = (TextView)view.findViewById( R.id.savingTotal );
        paidtoTotal = (TextView) view.findViewById( R.id.paidtoTotal );
        takenTotal = (TextView) view.findViewById( R.id.takenTotal );

        incomeDatabaseHelper = new IncomeDatabaseHelper( getContext() );
        expenseDatabaseHelper = new ExpenseDatabaseHelper( getContext() );
        borrowDatabaseHelper = new BorrowDatabaseHelper( getContext() );
        lendDatabaseHelper = new LendDatabaseHelper( getContext() );

        incomeTotal.setText( String.valueOf( incomeDatabaseHelper.getTotalIncome()+" Rs" ) );
        expenseTotal.setText( String.valueOf( expenseDatabaseHelper.getTotalExpense()+" Rs"));
        savingTotal.setText( String.valueOf( incomeDatabaseHelper.getTotalIncome()-expenseDatabaseHelper.getTotalExpense())+" Rs" );
        paidtoTotal.setText( String.valueOf( borrowDatabaseHelper.getTotalPaidTo())+" Rs" );
        takenTotal.setText( String.valueOf( lendDatabaseHelper.getTotalTaken())+" Rs" );

        incomeTotal.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent( getActivity(), IncomeReportActivity.class );
                startActivity( i );
            }
        } );

        expenseTotal.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent( getActivity(), ExpenseReportActivity.class );
                startActivity( intent );
            }
        } );

        ses = new SessionManagement( getContext() );
    //    incomeTotal.setText( String.valueOf(incomeReportActivity.getTotalIncome()));
        tvHello.setVisibility(View.VISIBLE);

       todoLayout.setOnClickListener( new View.OnClickListener() {
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

        incomeLayout.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 if (ses.getUserName()!=null){

                     Toast.makeText( getContext(), "login Layout clicked ", Toast.LENGTH_SHORT ).show();
                     FragmentTransaction fragmentTransaction  = getFragmentManager().beginTransaction();
                     fragmentTransaction.replace( R.id.fragment_container, new AddIncomeFragment());
                     fragmentTransaction.addToBackStack( null );
                     fragmentTransaction.commit();
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
                    Toast.makeText( getContext(), "login Layout clicked ", Toast.LENGTH_SHORT ).show();
                    FragmentTransaction fragmentTransaction  = getFragmentManager().beginTransaction();
                    fragmentTransaction.replace( R.id.fragment_container, new AddExpenseFragment());
                    fragmentTransaction.addToBackStack( null );
                    fragmentTransaction.commit();
                }else{
                    Toast.makeText( getActivity(), "Please First login into your account!!!",Toast.LENGTH_SHORT ).show();
                }

            }
        } );

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

        tobePaidLayout.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ses.getUserName()!=null){
                    FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                    fragmentTransaction.replace( R.id.fragment_container, new BorrowFragment() );
                    fragmentTransaction.addToBackStack( null );
                    fragmentTransaction.commit();
                }else{
                    Toast.makeText( getActivity(), "Please First login into your account!!!",Toast.LENGTH_SHORT ).show();
                }

            }
        } );

        tobeTakenLayout.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ses.getUserName()!=null){
                    FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                    fragmentTransaction.replace( R.id.fragment_container, new LendFragment() );
                    fragmentTransaction.addToBackStack( null );
                    fragmentTransaction.commit();
                }else{
                    Toast.makeText( getActivity(), "Please First login into your account!!!",Toast.LENGTH_SHORT ).show();
                }

            }
        } );

        aboutAppLayout.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ses.getUserName()!=null){
                    FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                    fragmentTransaction.replace( R.id.fragment_container, new AboutAppFragment() );
                    fragmentTransaction.addToBackStack( null );
                    fragmentTransaction.commit();
                }else{
                    Toast.makeText( getActivity(), "Please First login into your account!!!",Toast.LENGTH_SHORT ).show();
                }

            }
        } );

        aboutLayout.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText( getContext(), "login Layout clicked ", Toast.LENGTH_SHORT ).show();
                FragmentTransaction fragmentTransaction  = getFragmentManager().beginTransaction();
                fragmentTransaction.replace( R.id.fragment_container, new AboutFragment());
                fragmentTransaction.addToBackStack( null );
                fragmentTransaction.commit();
            }
        } );


        /* Bundle bundle = getArguments();
        if(bundle != null) {

            String username = bundle.getString( "username" );
            id = bundle.getString( "userid" );
            //Toast.makeText( getContext(), id+username, Toast.LENGTH_SHORT ).show();
            */
        if(ses.getUserName()!=null)
        {
            tvHello.setText( "Hello, " + ses.getUserName() );
        }
        else
        { }
            return view;
    }
    public String findId(){
        return id;
    }
}
