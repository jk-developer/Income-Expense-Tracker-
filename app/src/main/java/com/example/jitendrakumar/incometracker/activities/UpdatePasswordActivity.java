package com.example.jitendrakumar.incometracker.activities;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jitendrakumar.incometracker.R;
import com.example.jitendrakumar.incometracker.database.DatabaseHelper;

public class UpdatePasswordActivity extends AppCompatActivity {
   private int uid;
   DatabaseHelper databaseHelper;
   EditText etUpdatePassword, etRepassword;
   TextView tvUpdate;
   String email, mobile, username, password;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_update_password );

        getSupportActionBar().setTitle( "Update password" );
        getSupportActionBar().setDisplayHomeAsUpEnabled( true );

        etRepassword = (EditText) findViewById( R.id.etRepassword );
        etUpdatePassword = (EditText) findViewById( R.id.etUpdatePassword );
        tvUpdate = (TextView) findViewById( R.id.tvUpdate );
        databaseHelper = new DatabaseHelper( this );

        Bundle pass = getIntent().getExtras();
        if(pass!=null)
        {
            uid = pass.getInt( "userid" );
        }

        tvUpdate.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d( "id", "onClick: id "+uid );
                Cursor res = databaseHelper.getParticularData( String.valueOf( uid ) );
                if(res.getCount()==0){
                    Toast.makeText( UpdatePasswordActivity.this, "No User found ", Toast.LENGTH_SHORT ).show();
                    return;
                }
                else{
                    while (res.moveToNext()){
                         username = res.getString( 1 );
                         email = res.getString( 2 );
                         mobile = res.getString( 3 );
                         password = res.getString( 4 );
                    }
                }
                if(etRepassword.getText().toString().equals( etUpdatePassword.getText().toString() )){
                    if(databaseHelper.updateData( String.valueOf( uid ), username,email, mobile, etUpdatePassword.getText().toString())){
                        Toast.makeText( UpdatePasswordActivity.this, " Password updated successfully!!! ", Toast.LENGTH_SHORT ).show();

                    }else{
                        Toast.makeText( UpdatePasswordActivity.this, " Error !!! ", Toast.LENGTH_SHORT ).show();
                    }
                }
                else {
                    Toast.makeText( UpdatePasswordActivity.this, " Passwords did not match!!! ", Toast.LENGTH_SHORT ).show();
                }

            }
        } );

    }
}
