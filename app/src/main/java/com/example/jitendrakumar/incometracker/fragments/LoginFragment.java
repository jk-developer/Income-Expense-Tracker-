package com.example.jitendrakumar.incometracker.fragments;

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
import android.widget.TextView;
import android.widget.Toast;

import com.example.jitendrakumar.incometracker.R;
import com.example.jitendrakumar.incometracker.helper.UserData;
import com.example.jitendrakumar.incometracker.database.DatabaseHelper;

public class LoginFragment extends Fragment {

    DatabaseHelper myDb;
    public final static String TAG = "RES";
    EditText etUsername, etPassword;
    TextView tvRegister;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        myDb = new  DatabaseHelper(getActivity());

        View view =  inflater.inflate( R.layout.fragment_login, container, false );
        TextView tvRegister = (TextView)view.findViewById( R.id.tvRegister );
        Button btnLogin = (Button) view.findViewById( R.id.btnLogin );
        etUsername = (EditText) view.findViewById( R.id.etUsername );
        etPassword = (EditText)view.findViewById( R.id.etPassword );
        etUsername.setHintTextColor(getResources().getColor(R.color.colorTexts));
        etPassword.setHintTextColor(getResources().getColor(R.color.colorTexts));

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
                String username = etUsername.getText().toString();
                String password = etPassword.getText().toString();
                Log.d( TAG, "onClick:  "+username +password.length()+" is currently ." );

                if(username.length() == 0 || password.length() == 0){
                    Toast.makeText( getContext(), "Please First Fill all the Fields.", Toast.LENGTH_SHORT ).show();

                }else
                {
                    try {
                         UserData res = myDb.getLoginData(username);
                         Integer dbid = res.getId();
                         String dbusername =  res.getUserName();
                         String dbpassword =  res.getPass();

                            if(!(username.equals( dbusername )) || !(password.equals(dbpassword)))
                            {
                              Toast.makeText(getContext(), "Either Wrong username/password or Please Register First if not Registered"+dbusername+username, Toast.LENGTH_SHORT ).show();
                            }
                            else
                            {
                            Toast.makeText( getActivity(), "Logged in Successfully !!! ", Toast.LENGTH_SHORT ).show();
                               // HomeFragment hf = new HomeFragment();
                                Bundle args = new Bundle();
                                args.putString("id", dbid.toString());
                                args.putString( "username", dbusername );
                               // hf.setArguments(args);
                                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                               FragmentTransaction fragmentTransaction  = getFragmentManager().beginTransaction();
                               HomeFragment hf = new HomeFragment();
                               hf.setArguments( args );
                            fragmentTransaction.replace( R.id.fragment_container,hf);
                            fragmentTransaction.commit();
                           }

                        }catch (Exception e){
                        e.printStackTrace();

                    }

                }
            }
        });
     return view;
    }


}
