package com.example.jitendrakumar.incometracker.fragments;

import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jitendrakumar.incometracker.R;
import com.example.jitendrakumar.incometracker.database.DatabaseHelper;

public class SignupFragment extends Fragment {

    DatabaseHelper myDb;
    EditText name, mobile, email, password;
    private TextInputLayout inputLayoutName, inputLayoutEmail, inputLayoutPassword,inputLayoutMobile;
    Button btnSignup, btnViewAll;
    TextView tvLogin;
    boolean isInserted;
    public static final String TAG = "status";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate( R.layout.fragment_signup, container, false );
        myDb = new DatabaseHelper( getContext());

        inputLayoutName = (TextInputLayout) view.findViewById(R.id.input_layout_name);
        inputLayoutMobile = (TextInputLayout) view.findViewById(R.id.input_layout_mobile);
        inputLayoutEmail = (TextInputLayout) view.findViewById(R.id.input_layout_email);
        inputLayoutPassword = (TextInputLayout) view.findViewById(R.id.input_layout_password);
        name = (EditText) view.findViewById( R.id.name );
        mobile = (EditText) view.findViewById( R.id.mobile );
        email = (EditText) view.findViewById( R.id.email );
        password = (EditText) view.findViewById( R.id.password );
        btnSignup = (Button) view.findViewById( R.id.btnSignup );
        btnViewAll = (Button) view.findViewById( R.id.btnViewAll );
        tvLogin = (TextView) view.findViewById( R.id.tvLogin );

        name.addTextChangedListener(new MyTextWatcher(name));
        email.addTextChangedListener(new MyTextWatcher(email));
        mobile.addTextChangedListener(new MyTextWatcher(mobile));
        password.addTextChangedListener(new MyTextWatcher(password));

        name.setHintTextColor(getResources().getColor(R.color.colorTexts));
        mobile.setHintTextColor(getResources().getColor(R.color.colorTexts));
        password.setHintTextColor(getResources().getColor(R.color.colorTexts));
        email.setHintTextColor(getResources().getColor(R.color.colorTexts));

        name.setTextColor( Color.parseColor("#00ff00"));
        mobile.setTextColor( Color.parseColor("#00ff00"));
        email.setTextColor( Color.parseColor("#00ff00"));
        password.setTextColor( Color.parseColor("#00ff00"));



        tvLogin.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction fragmentTransaction  = getFragmentManager().beginTransaction();
                fragmentTransaction.replace( R.id.fragment_container, new LoginFragment());
                fragmentTransaction.commit();
            }
        } );

        adddatainDB();
        viewAllData();


      return view;

    }

    public void adddatainDB() {
        btnSignup.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = name.getText().toString().trim();
                String mob =  mobile.getText().toString().trim();
                String em = email.getText().toString().trim();
                String pass = password.getText().toString();
          /*      if(username.length()==0){
                    name.setError( "Username field is required!!!" );
                }
                if(mob.length()==0){
                    mobile.setError( "Mobile field is required!!!" );
                }
                if(em.length()==0){
                    email.setError( "Email field is required!!!" );
                }
                if(pass.length()==0){
                    password.setError( "Password field is required!!!" );
                }
                else{
                   // if((validate( name, mobile, email, password)==true)){   */
                         if(submitForm())
                         {
                             isInserted = myDb.insertData(username,em, mob , pass );
                             if (isInserted == true) {
                                 Toast.makeText( getActivity(), "Data Saved to DataBase.", Toast.LENGTH_SHORT ).show();
                                 FragmentTransaction fragmentTransaction  = getFragmentManager().beginTransaction();
                                 fragmentTransaction.replace( R.id.fragment_container, new LoginFragment());
                                 fragmentTransaction.commit();
                             } else {
                                 Toast.makeText( getActivity(), "Data is not Saved to DataBase.", Toast.LENGTH_SHORT ).show();
                             }
                         }

                       // }
                //}
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

   public boolean validateMobile() {

       if (mobile.getText().toString().trim().isEmpty() || mobile.getText().toString().trim().length()!=10) {
           inputLayoutMobile.setError(getString(R.string.err_msg_mobile));
           requestFocus(mobile);
           return false;
       } else {
           inputLayoutMobile.setErrorEnabled(false);
       }

       return true;
   }


    /**
     * Validating form
     */
    private boolean submitForm() {
        if (!validateName()) {
            return false;
        }

        if (!validateEmail()) {
            return false;
        }

        if (!validatePassword()) {
            return false;
        }
        if(!validateMobile()){
            return false;
        }

        Toast.makeText(getActivity(), "Thank You!", Toast.LENGTH_SHORT).show();
        return true;
    }

    private boolean validateName() {
        if (name.getText().toString().trim().isEmpty()) {
            inputLayoutName.setError(getString(R.string.err_msg_name));
            requestFocus(name);
            return false;
        } else {
            inputLayoutName.setErrorEnabled(false);
        }

        return true;
    }

    private boolean validateEmail() {
        String Email = email.getText().toString().trim();

        if (Email.isEmpty() || !isValidEmail(Email)) {
            inputLayoutEmail.setError(getString(R.string.err_msg_email));
            requestFocus(email);
            return false;
        } else {
            inputLayoutEmail.setErrorEnabled(false);
        }

        return true;
    }

    private boolean validatePassword() {
        if (password.getText().toString().trim().isEmpty()) {
            inputLayoutPassword.setError(getString(R.string.err_msg_password));
            requestFocus(password);
            return false;
        } else {
            inputLayoutPassword.setErrorEnabled(false);
        }

        return true;
    }

    private static boolean isValidEmail(String email) {
        return !TextUtils.isEmpty(email) && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

    public class MyTextWatcher implements TextWatcher {

        private View view;

        private MyTextWatcher(View view) {
            this.view = view;
        }

        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void afterTextChanged(Editable editable) {
            switch (view.getId()) {
                case R.id.name:
                    validateName();
                    break;
                case R.id.email:
                    validateEmail();
                    break;

                case R.id.mobile:
                    validateMobile();
                    break;
                case R.id.password:
                    validatePassword();
                    break;
            }
        }
    }


}
