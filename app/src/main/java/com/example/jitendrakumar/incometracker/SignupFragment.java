package com.example.jitendrakumar.incometracker;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.jitendrakumar.incometracker.database.DatabaseHelper;

public class SignupFragment extends Fragment {

    DatabaseHelper myDb;
    EditText name, mobile, email, password, etId;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate( R.layout.fragment_signup, container, false );
    }
}
