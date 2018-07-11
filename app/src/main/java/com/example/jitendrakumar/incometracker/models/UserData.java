package com.example.jitendrakumar.incometracker.models;

public class UserData {
    String userName;
    String pass;
    int id;

   public UserData(String userName, String pass,int id){

        this.userName=userName;
        this.pass=pass;
        this.id=id;
    }

    public String  getUserName(){
        return  userName;
    }
    public  String getPass(){
        return pass;
    }
    public int getId(){return id ;}

}
