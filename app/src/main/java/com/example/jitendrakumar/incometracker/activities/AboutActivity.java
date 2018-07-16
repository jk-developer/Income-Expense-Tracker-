package com.example.jitendrakumar.incometracker.activities;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.jitendrakumar.incometracker.R;

public class AboutActivity extends AppCompatActivity {
    ImageView ivFacebook, ivMail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_about );

        getSupportActionBar().setDisplayHomeAsUpEnabled( true );
        getSupportActionBar().setTitle( "About Developer" );

        ivFacebook = (ImageView)findViewById( R.id.ivFacebook );
        ivMail = (ImageView)findViewById( R.id.ivMail );

        ivFacebook.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "https://m.facebook.com/profile.php";
                Uri uri = Uri.parse( url );
                try {
                    Intent i = new Intent( Intent.ACTION_VIEW, uri );
                    startActivity( i );
                }catch (ActivityNotFoundException e){
                    e.printStackTrace();
                }
            }
        } );

        ivMail.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mailurl = "mailto:";
                Uri uri = Uri.parse( mailurl );
                Intent intent = new Intent( Intent.ACTION_SEND );
                intent.setData(uri);
                String[] to = {"jkgupta15798@gmail.com"};
                intent.putExtra( Intent.EXTRA_EMAIL, to );
                intent.setType( "message/rfc822" );
                startActivity(Intent.createChooser(intent, "Send email using ..."));
            }
        } );


    }
}
