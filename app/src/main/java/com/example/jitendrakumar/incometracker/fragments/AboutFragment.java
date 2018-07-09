package com.example.jitendrakumar.incometracker.fragments;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.jitendrakumar.incometracker.R;

public class AboutFragment extends Fragment {
    ImageView ivFacebook, ivWhatsapp, ivMail;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate( R.layout.fragment_about, container, false );
        ivFacebook = (ImageView)view.findViewById( R.id.ivFacebook );
        ivWhatsapp = (ImageView)view.findViewById( R.id.ivWhatsapp );
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
                String mailurl = "mailto:a@cb.lk";
                Uri uri = Uri.parse( mailurl );
                try {
                    Intent i = new Intent( Intent.ACTION_SEND, uri );
                    startActivity( i );
                }catch (ActivityNotFoundException e){
                    e.printStackTrace();
                }
            }
        } );

        return  view;
    }
}
