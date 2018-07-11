package com.example.jitendrakumar.incometracker.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jitendrakumar.incometracker.R;
import com.example.jitendrakumar.incometracker.activities.TodoActivity;
import com.example.jitendrakumar.incometracker.helper.SessionManagement;

public class HomeFragment extends Fragment {
    TextView tvHello;
    public String id;
    LinearLayout todoLayout,incomeLayout,expenseLayout,incomeReportLayout,expenseReportLayout,aboutLayout,loginLayout,tobePaidLayout, tobeTakenLayout;
    SessionManagement ses;

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
        ses = new SessionManagement( getContext() );

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
                     fragmentTransaction.replace( R.id.fragment_container, new IncomeFragment());
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
                    fragmentTransaction.replace( R.id.fragment_container, new ExpenseFragment());
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
                    fragmentTransaction.replace( R.id.fragment_container, new TobePaidFragment() );
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
                    fragmentTransaction.replace( R.id.fragment_container, new TobeTakenFragment() );
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
