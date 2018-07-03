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

public class ExpenseFragment extends Fragment {
    EditText etExpenseType, etExpenseAmount, etExpenseDate, etExpenseTime;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate( R.layout.fragment_expense, container, false );

        etExpenseType = (EditText) view.findViewById( R.id.etExpenseType );
        etExpenseAmount = (EditText) view.findViewById( R.id.etExpenseAmount);
        etExpenseDate = (EditText) view.findViewById( R.id.etExpenseDate );
        etExpenseTime = (EditText) view.findViewById( R.id.etExpenseTime );

        etExpenseType.setHintTextColor(getResources().getColor(R.color.colorTexts));
        etExpenseAmount.setHintTextColor(getResources().getColor(R.color.colorTexts));
        etExpenseDate.setHintTextColor(getResources().getColor(R.color.colorTexts));
        etExpenseTime.setHintTextColor(getResources().getColor(R.color.colorTexts));

        return view;
    }
}
