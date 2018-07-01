package com.example.jitendrakumar.incometracker.helper;

public class UserData {
    Integer id;
    String userName;
    String pass;

   public UserData(String userName, String pass, Integer id){
        this.id = id;
        this.userName=userName;
        this.pass=pass;
    }

    public String  getUserName(){
        return  userName;
    }
    public  String getPass(){
        return pass;
    }

    public Integer getId() {
        return id;
    }
}
