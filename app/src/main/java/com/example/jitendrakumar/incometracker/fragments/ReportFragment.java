package com.example.jitendrakumar.incometracker.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.jitendrakumar.incometracker.R;

public class ReportFragment extends Fragment {
    EditText etFrom, etTo;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate( R.layout.fragment_report, container, false );
        etFrom = (EditText)view.findViewById( R.id.etFrom);
        etTo = (EditText) view.findViewById( R.id.etTo);

        etFrom.setHintTextColor(getResources().getColor(R.color.colorTexts));
        etTo.setHintTextColor(getResources().getColor(R.color.colorTexts));
        return view;
    }
}
