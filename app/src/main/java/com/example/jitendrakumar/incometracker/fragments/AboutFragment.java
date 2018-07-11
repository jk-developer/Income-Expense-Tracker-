package com.example.jitendrakumar.incometracker.fragments;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.service.chooser.ChooserTarget;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.jitendrakumar.incometracker.R;

public class AboutFragment extends Fragment {
    ImageView ivFacebook, ivMail;

    @Nullable
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate( R.layout.fragment_about, container, false );
        ivFacebook = (ImageView)view.findViewById( R.id.ivFacebook );
        ivMail = (ImageView)view.findViewById( R.id.ivMail );

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



        return  view;
    }
}
