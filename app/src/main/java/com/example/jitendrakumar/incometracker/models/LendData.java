package com.example.jitendrakumar.incometracker.models;

public class LendData {
    int Lid;
    float Lamount;

    public LendData(int lid, float lamount, String ldesc, String lperson, String ldate) {
        Lid = lid;
        Lamount = lamount;
        Ldesc = ldesc;
        Lperson = lperson;
        Ldate = ldate;
    }

    public int getLid() {
        return Lid;
    }

    public void setLid(int lid) {
        Lid = lid;
    }

    public float getLamount() {
        return Lamount;
    }

    public void setLamount(float lamount) {
        Lamount = lamount;
    }

    public String getLdesc() {
        return Ldesc;
    }

    public void setLdesc(String ldesc) {
        Ldesc = ldesc;
    }

    public String getLperson() {
        return Lperson;
    }

    public void setLperson(String lperson) {
        Lperson = lperson;
    }

    public String getLdate() {
        return Ldate;
    }

    public void setLdate(String ldate) {
        Ldate = ldate;
    }

    String Ldesc, Lperson, Ldate;
}
