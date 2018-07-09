package com.example.jitendrakumar.incometracker.activities;

import android.content.Context;
import android.content.SharedPreferences;

public class UserSessionManagement {

    private String username;
    Context context;
    SharedPreferences sharedPreferences;
    private String password;

    public int getUserid() {
        int userid = sharedPreferences.getInt( "userid",0 );
        return userid;
    }

    public void setUserid(int userid) {
        sharedPreferences.edit().putInt( "userid", userid );
        this.userid = userid;
    }

    private int userid;

    public void removeUser(){
        sharedPreferences.edit().clear().commit();
    }

    public String getPassword() {
        String userpassword = sharedPreferences.getString( "userpassword"," " );
        return password;
    }

    public void setPassword(String password) {
        sharedPreferences.edit().putString( "userpassword", password );
        this.password = password;

    }

    public void setUsername(String username) {
        this.username = username;
        sharedPreferences.edit().putString( "name",username ).commit();
    }

    public String getUsername() {
       String username =  sharedPreferences.getString( "name", " " );
        return username;
    }



    public UserSessionManagement(Context context){
        this.context = context;
        sharedPreferences = context.getSharedPreferences( "UserInfo",Context.MODE_PRIVATE );
    }
}
