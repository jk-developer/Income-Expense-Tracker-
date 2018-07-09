package com.example.jitendrakumar.incometracker.validations;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

import com.example.jitendrakumar.incometracker.database.DatabaseHelper;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class InputValidation {

    private Context context;

    public InputValidation(Context context) {
        this.context = context;
    }

    public boolean isInputEditTextEmail(TextInputEditText textInputEditText, TextInputLayout textInputLayout, String message) {
        String value = textInputEditText.getText().toString().trim();
        if (value.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(value).matches()) {
            textInputLayout.setError(message);
            hideKeyboardFrom(textInputEditText);
            return false;
        } else {
            textInputLayout.setErrorEnabled(false);
        }
        return true;
    }

    public boolean isInputEditTextMatches(TextInputEditText textInputEditText1, TextInputEditText textInputEditText2, TextInputLayout textInputLayout, String message) {
        String value1 = textInputEditText1.getText().toString().trim();
        String value2 = textInputEditText2.getText().toString().trim();
        if (!value1.contentEquals(value2)) {
            textInputLayout.setError(message);
            hideKeyboardFrom(textInputEditText2);
            return false;
        } else {
            textInputLayout.setErrorEnabled(false);
        }
        return true;
    }

    /**
     * method to Hide keyboard
     *
     * @param view
     */
    private void hideKeyboardFrom(View view) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService( Activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }

    public String authentication(SQLiteDatabase db, DatabaseHelper myDb, String userName, String password) {


        String retrievedUserName = "";
        String retrievedPassword = "";
        String status = "";
        try {
            String tablename = myDb.TABLE_NAME;
            String user = myDb.COL_2;
            String statement = "SELECT * FROM "+tablename+" WHERE "+user+" = " + userName;
            Cursor result = db.rawQuery( statement,null,null );
            if(result.getCount() == 0)
            {
                status = "Login failed!!!";
            }
            else
            {
                while (result.moveToNext()){
                    retrievedUserName = result.getString( 1 );
                    retrievedPassword = result.getString( 4 );

                }

            }
            if (retrievedUserName.equals(userName) && retrievedPassword.equals(password)) {
                status = "Success!";
            } else {
                status = "Login failed!!!";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return status;
    }
}
