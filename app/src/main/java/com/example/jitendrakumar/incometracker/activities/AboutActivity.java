package com.example.jitendrakumar.incometracker.activities;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.transition.Explode;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import com.example.jitendrakumar.incometracker.*;

import com.example.jitendrakumar.incometracker.R;

public class AboutActivity extends AppCompatActivity {
    ImageView ivFacebook, ivMail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        getWindow().requestFeature( Window.FEATURE_CONTENT_TRANSITIONS );
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_about );

        initPage();
        initAnimation();

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
                Uri uri = Uri.parse("mailto:" );
                Intent intent = new Intent( Intent.ACTION_SENDTO);
                intent.setData(uri);
                String[] to = {"jkgupta4398@gmail.com"};
                intent.putExtra( Intent.EXTRA_EMAIL, to );
                startActivity(intent);
            }
        } );


    }
   public void initPage(){

       getSupportActionBar().setDisplayHomeAsUpEnabled( true );
       getSupportActionBar().setTitle( "About Developer" );

   }

    @Override
    public boolean onSupportNavigateUp() {
        finishAfterTransition();
        return true;    }

        public void initAnimation(){
            Explode explode = new Explode( );
            explode.setDuration( 800 );
            getWindow().setEnterTransition( explode );

        }
}
