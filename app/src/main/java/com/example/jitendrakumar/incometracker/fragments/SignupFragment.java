package com.example.jitendrakumar.incometracker.fragments;

import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jitendrakumar.incometracker.R;
import com.example.jitendrakumar.incometracker.database.DatabaseHelper;

public class SignupFragment extends Fragment {

    DatabaseHelper myDb;
    EditText name, mobile, email, password;
    Button btnSignup, btnViewAll;
    TextView tvLogin;
    boolean isInserted;
    public static final String TAG = "status";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate( R.layout.fragment_signup, container, false );
        myDb = new DatabaseHelper( getContext());

        name = (EditText) view.findViewById( R.id.name );
        mobile = (EditText) view.findViewById( R.id.mobile );
        email = (EditText) view.findViewById( R.id.email );
        password = (EditText) view.findViewById( R.id.password );
        btnSignup = (Button) view.findViewById( R.id.btnSignup );
        btnViewAll = (Button) view.findViewById( R.id.btnViewAll );
        tvLogin = (TextView) view.findViewById( R.id.tvLogin );

        name.setHintTextColor(getResources().getColor(R.color.colorTexts));
        mobile.setHintTextColor(getResources().getColor(R.color.colorTexts));
        password.setHintTextColor(getResources().getColor(R.color.colorTexts));
        email.setHintTextColor(getResources().getColor(R.color.colorTexts));

        name.setTextColor( Color.parseColor("#00ff00"));
        mobile.setTextColor( Color.parseColor("#00ff00"));
        email.setTextColor( Color.parseColor("#00ff00"));
        password.setTextColor( Color.parseColor("#00ff00"));



        tvLogin.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction fragmentTransaction  = getFragmentManager().beginTransaction();
                fragmentTransaction.replace( R.id.fragment_container, new LoginFragment());
                fragmentTransaction.commit();
            }
        } );

        adddatainDB();
        viewAllData();


      return view;
    }

    public void adddatainDB() {
        btnSignup.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = name.getText().toString();
                String mob =  mobile.getText().toString();
                String em = email.getText().toString();
                String pass = password.getText().toString();
                if(username.length()==0){
                    name.setError( "Username field is required!!!" );
                }
                if(mob.length()==0){
                    mobile.setError( "Mobile field is required!!!" );
                }
                if(em.length()==0){
                    email.setError( "Email field is required!!!" );
                }
                if(pass.length()==0){
                    password.setError( "Password field is required!!!" );
                }
                else{
                    if((validate( name, mobile, email, password)==true)){

                        if((validateMobile( mobile)==true) && validateEmail( email )==true){
                            isInserted = myDb.insertData(username,em, mob , pass );
                            if (isInserted == true) {
                                Toast.makeText( getActivity(), "Data Saved to DataBase.", Toast.LENGTH_SHORT ).show();
                                FragmentTransaction fragmentTransaction  = getFragmentManager().beginTransaction();
                                fragmentTransaction.replace( R.id.fragment_container, new LoginFragment());
                                fragmentTransaction.commit();
                            } else {
                                Toast.makeText( getActivity(), "Data is not Saved to DataBase.", Toast.LENGTH_SHORT ).show();
                            }
                        }
                    }
                  /*  else{
                       Toast.makeText( getActivity(), "something wrong", Toast.LENGTH_SHORT ).show();
                }  */
                }
            }
        } );


    }

    public void viewAllData(){
        btnViewAll.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Cursor res = myDb.getAllData();
                if(res.getCount() == 0)
                {
                    // Show message
                    showMessage( "Error", "Nothing Found" );

                    return;
                }
                else
                {
                    StringBuffer buffer = new StringBuffer(  );
                    while (res.moveToNext()){
                        buffer.append( "Id : "+ res.getString( 0 )+"\n" );
                        buffer.append( "Username : "+ res.getString( 1 )+"\n" );
                        buffer.append( "Email : "+ res.getString( 2 )+"\n" );
                        buffer.append( "Mobile : "+ res.getString( 3 )+"\n" );
                        buffer.append( "Password : "+ res.getString( 4 )+"\n\n" );
                    }
                    // Show all data
                    showMessage( "Data", buffer.toString() );
                }
            }

        } );
    }

    public void showMessage(String title, String Message){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setCancelable( true );
        builder.setTitle( title );
        builder.setMessage( Message );
        builder.show();
    }
/*
    public int validateUsername(String username) {
        int temp = 0;
        Cursor result = myDb.getAllData();
        if (result.getCount() == 0) {

            return 1;
        } else {

            while (result.moveToNext()) {

                String dbuser = result.getString( 1 );
                if (dbuser.equals( username )) {
                    temp = 1;

                } else {
                    temp = 0;

                }
            }
            if (temp == 0) {
                return 1;
            } else {
                return 0;
            }

        }

    }

  */
   public boolean validateMobile(EditText mob) {
       String MobilePattern = "[0-9]{10}";
       if (mob.getText().toString().matches( MobilePattern )) {

           Toast.makeText( getActivity(), "phone number is valid", Toast.LENGTH_SHORT ).show();
           return true;

       } else if (!mob.getText().toString().matches( MobilePattern )) {

           mob.setError( "Please enter valid 10 digit phone number" );
           return false;
       }
       return false;
   }

   public boolean validateEmail(EditText em)
   {
           String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
           if (em.getText().toString().matches(emailPattern)) {

               Toast.makeText( getActivity(), "Email is Valid ", Toast.LENGTH_SHORT ).show();
                return true;

           } else if(!em.getText().toString().matches(emailPattern)) {

               //  Toast.makeText(getApplicationContext(),"Please Enter Valid Email Address",Toast.LENGTH_SHORT).show();
               em.setError( "Please Enter Valid Address " );
              return false;

           }
           return false;
       }

    private boolean validate(EditText name, EditText mob, EditText em, EditText password){


           if (name.getText().toString().length() > 25) {

               // Toast.makeText(getApplicationContext(), "pls enter less the 25 character in user name", Toast.LENGTH_SHORT).show();
               name.setError( "Please enter less than 25 characters in Username" );
               return false;
           }

           if (name.getText().toString().length() == 0 || mob.getText().toString().length() == 0 || em.getText().toString().length() == 0 || password.length() == 0) {
               Toast.makeText( getActivity(), "Please Fill All Empty Fields!!!", Toast.LENGTH_SHORT ).show();
               return false;
           }
           return true;
       }

}
