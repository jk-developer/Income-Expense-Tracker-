package com.example.jitendrakumar.incometracker;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.jitendrakumar.incometracker.database.DatabaseHelper;

public class SignupFragment extends Fragment {

    DatabaseHelper myDb;
    EditText name, mobile, email, password;
    Button btnSignup, btnViewAll;
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

        adddatainDB();
        viewAllData();


      return view;
    }

    public void adddatainDB() {
        btnSignup.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean isInserted = myDb.insertData( name.getText().toString(), email.getText().toString(), mobile.getText().toString(), password.getText().toString() );
                if (isInserted == true) {
                    Toast.makeText( getActivity(), "Data Saved to DataBase.", Toast.LENGTH_SHORT ).show();
                    FragmentTransaction fragmentTransaction  = getFragmentManager().beginTransaction();
                    fragmentTransaction.replace( R.id.fragment_container, new LoginFragment());
                    fragmentTransaction.commit();
                } else {
                    Toast.makeText( getActivity(), "Data is not Saved to DataBase.", Toast.LENGTH_SHORT ).show();
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


}
