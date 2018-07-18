package com.example.jitendrakumar.incometracker.fragments;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jitendrakumar.incometracker.R;
import com.example.jitendrakumar.incometracker.activities.UpdatePasswordActivity;
import com.example.jitendrakumar.incometracker.helper.SessionManagement;
import com.example.jitendrakumar.incometracker.models.UserData;
import com.example.jitendrakumar.incometracker.database.DatabaseHelper;
import com.example.jitendrakumar.incometracker.validations.InputValidation;

public class LoginFragment extends Fragment {

    DatabaseHelper myDb;
    public final static String TAG = "RES";
    EditText etUsername, etPassword;
    TextView tvRegister, tvUpdatePassword;
    InputValidation inputValidation;
    SQLiteDatabase db;
    int dbid;
    //  UserData userData;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        myDb = new  DatabaseHelper(getActivity());

        View view =  inflater.inflate( R.layout.fragment_login, container, false );
        TextView tvRegister = (TextView)view.findViewById( R.id.tvRegister );
        Button btnLogin = (Button) view.findViewById( R.id.btnLogin );
        etUsername = (EditText) view.findViewById( R.id.etUsername );
        etPassword = (EditText)view.findViewById( R.id.etPassword );
        tvUpdatePassword = (TextView)view.findViewById( R.id.tvUpdatePassword );
        etUsername.setHintTextColor(getResources().getColor(R.color.colorTexts));
        etPassword.setHintTextColor(getResources().getColor(R.color.colorTexts));
        etUsername.setTextColor( Color.parseColor("#00ff00"));
        etPassword.setTextColor( Color.parseColor("#00ff00"));

        tvRegister.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction fragmentTransaction  = getFragmentManager().beginTransaction();
                fragmentTransaction.replace( R.id.fragment_container, new SignupFragment());
                fragmentTransaction.commit();
            }
        } );


        btnLogin.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SQLiteDatabase db = myDb.getDB();
                String username = etUsername.getText().toString();
                String password = etPassword.getText().toString();
                try {
                    if (username.length() == 0 || password.length() == 0) {
                        Toast.makeText( getContext(), "Please First Fill all the Fields.", Toast.LENGTH_SHORT ).show();
                        if (username.length() == 0 ){
                            // showToast("Enter Your Name");
                            etUsername.setError( "username is required!!!" );
                        }
                        if(password.length()==0)
                        {
                            etPassword.setError( "Password is required!!!" );
                        }

                    }
                    else if (username.length()!=0 && password.length()!=0) {
                        //  userData = new UserData( username, password );
                        UserData Data = myDb.getLoginData( username );
                        if(Data !=null){
                            String dbusername = Data.getUserName();
                            String dbpassword = Data.getPass();
                             dbid = Data.getId();
                            if (dbusername.equals( username )) {

                                  if(!dbpassword.equals( password )){
                                      tvUpdatePassword.setVisibility( View.VISIBLE );
                                  }
                                  if(dbpassword.equals( password )) {
                                      SessionManagement sessionManagement = new SessionManagement( getActivity() );
                                      sessionManagement.setUserName( dbusername );
                                      sessionManagement.setUserPassword( dbpassword );
                                      sessionManagement.setUserId( String.valueOf( dbid ) );
                                      Toast.makeText( getActivity(), "Logged in Successfully !!! ", Toast.LENGTH_SHORT ).show();
                                      tvUpdatePassword.setVisibility( View.INVISIBLE );

                                      FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                                      HomeFragment hf = new HomeFragment();
                                      //  hf.setArguments( args );
                                      fragmentTransaction.replace( R.id.fragment_container, hf );
                                      fragmentTransaction.addToBackStack( null );
                                      fragmentTransaction.commit();
                                      Log.d( TAG, "onClick: sessionmange" + sessionManagement.getUserName() );

                                  }else{
                                      Toast.makeText( getActivity(), "Password is incorrect !!! ", Toast.LENGTH_SHORT ).show();
                                      tvUpdatePassword.setVisibility( View.VISIBLE );
                                  }

                            }
                            else{
                                Toast.makeText( getActivity(), "Username is incorrect !!! ", Toast.LENGTH_SHORT ).show();
                            }
                        }
                        else {
                            Toast.makeText( getActivity(), "No Account Found for this Username, Please First Register", Toast.LENGTH_SHORT ).show();
                        }

                    }
                    else {

                    }


                        /*    UserData res = myDb.getLoginData(username);
                         Integer dbid = res.getId();
                         String dbusername =  res.getUserName();
                         String dbpassword =  res.getPass();
                            if(!(username.equals( dbusername )) || !(password.equals(dbpassword)))
                            {
                              Toast.makeText(getContext(), "Either Wrong username/password or Please Register First if not Registered"+dbusername+username, Toast.LENGTH_SHORT ).show();
                            }
                            else
                            {  */

                }
                catch (Exception e){
                    e.printStackTrace();

                }

            }
        });

        tvUpdatePassword.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), UpdatePasswordActivity.class);
                i.putExtra( "userid", dbid );
                Log.d( TAG, "onClick: dbid"+ dbid );
                startActivity( i );
            }
        } );

        return view;
    }


}