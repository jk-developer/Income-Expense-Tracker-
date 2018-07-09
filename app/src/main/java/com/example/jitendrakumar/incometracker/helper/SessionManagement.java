package com.example.jitendrakumar.incometracker.helper;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

public class SessionManagement {
    private Context context;
    SharedPreferences sharedPreferences;
    private String userName;
    public static final String TAG = "user";

    public String getUserId() {
        String userid = sharedPreferences.getString( "userid", null );
        return userid;
    }

    public void setUserId(String userId) {
        this.userId = userId;
        sharedPreferences.edit().putString( "userid", userId).commit();
    }

    private String userId;

    public String getUserName() {
       String username = sharedPreferences.getString( "username", null );
       return username;
    }

    public void setUserName(String userName) {
        this.userName = userName;
        sharedPreferences.edit().putString( "username", userName ).commit();
    }

    public String getUserPassword() {
        String userpassword = sharedPreferences.getString( "userpassword", null );
        return userpassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
        sharedPreferences.edit().putString( "userpassword", userName ).commit();
    }

    private String userPassword;

    public void removeUser(){
        sharedPreferences.edit().clear().commit();
        Log.d( TAG, "removeUser: remove fun is called "+userName );
    }

    public SessionManagement(Context context){
        this.context = context;
        sharedPreferences = context.getSharedPreferences( "user_info", context.MODE_PRIVATE );
    }
}
