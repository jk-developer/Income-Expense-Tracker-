package com.example.jitendrakumar.incometracker;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.jitendrakumar.incometracker.database.DatabaseHelper;

import static com.example.jitendrakumar.incometracker.database.DatabaseHelper.TABLE_NAME;

public class LoginFragment extends Fragment {

    DatabaseHelper myDb;
    Cursor res;
    public final static String TAG = "RES";
    String username;
    EditText etUsername, etPassword;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view =  inflater.inflate( R.layout.fragment_login, container, false );
        Button btnRegister = (Button)view.findViewById( R.id.btnRegister );
        Button btnLogin = (Button) view.findViewById( R.id.btnLogin );
        etUsername = (EditText) view.findViewById( R.id.etUsername );
        etPassword = (EditText)view.findViewById( R.id.etPassword );

        btnRegister.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction fragmentTransaction  = getFragmentManager().beginTransaction();
                fragmentTransaction.replace( R.id.fragment_container, new SignupFragment());
                fragmentTransaction.commit();
            }
        } );
       final String username = etUsername.getText().toString();
       final String password = etPassword.getText().toString();
        btnLogin.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d( TAG, "onClick:  "+username +password.length()+" is currently ." );
                if(username.length() == 0 || password.length() == 0){
                    Toast.makeText( getContext(), "Please First Fill all the Fields.", Toast.LENGTH_SHORT ).show();

                }else
                {
                    res = myDb.getLoginData( username );
                    Integer dbId = res.getInt( 0 );
                    String dbusername =  res.getString( 1 );
                    String dbpassword =  res.getString( 5 );
                    if(username != dbusername || password != dbpassword)
                    {
                        Toast.makeText( getContext(), "Either Wrong username/password or Please Register First if not Registered", Toast.LENGTH_SHORT ).show();
                    }
                    else
                    {
                        Toast.makeText( getContext(), "Logged in Successfully !!! ", Toast.LENGTH_SHORT ).show();
                        Log.d( TAG, dbId+dbusername+dbpassword );
                        FragmentTransaction fragmentTransaction  = getFragmentManager().beginTransaction();
                        fragmentTransaction.replace( R.id.fragment_container, new HomeFragment());
                        fragmentTransaction.commit();
                    }

                }
            }
        } );

        return view;
    }

}
