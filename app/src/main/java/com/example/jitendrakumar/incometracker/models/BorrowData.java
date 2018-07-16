package com.example.jitendrakumar.incometracker.models;

public class BorrowData {
    int bId;
    float bAmount;

    public BorrowData(int bId, float bAmount, String bDesc, String bPerson, String bDate) {
        this.bId = bId;
        this.bAmount = bAmount;
        this.bDesc = bDesc;
        this.bPerson = bPerson;
        this.bDate = bDate;
    }

    public int getbId() {
        return bId;
    }

    public void setbId(int bId) {
        this.bId = bId;
    }

    public float getbAmount() {
        return bAmount;
    }

    public void setbAmount(float bAmount) {
        this.bAmount = bAmount;
    }

    public String getbDesc() {
        return bDesc;
    }

    public void setbDesc(String bDesc) {
        this.bDesc = bDesc;
    }

    public String getbPerson() {
        return bPerson;
    }

    public void setbPerson(String bPerson) {
        this.bPerson = bPerson;
    }

    public String getbDate() {
        return bDate;
    }

    public void setbDate(String bDate) {
        this.bDate = bDate;
    }

    String bDesc;
    String bPerson;
    String bDate;
}
