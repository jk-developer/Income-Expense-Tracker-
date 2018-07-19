package com.example.jitendrakumar.incometracker.models;

public class IncomeData {
    int incomeId;
    String inputType;
    float inputAmount;

    public String getIncomeDesc() {
        return incomeDesc;
    }

    public void setIncomeDesc(String incomeDesc) {
        this.incomeDesc = incomeDesc;
    }

    String incomeDesc;

    public IncomeData(int incomeId, String inputType, float inputAmount, String incomeDate, String incomeTime,String incomeDesc) {
        this.incomeId = incomeId;
        this.inputType = inputType;
        this.inputAmount = inputAmount;
        this.incomeDate = incomeDate;
        this.incomeTime = incomeTime;
        this.incomeDesc  = incomeDesc;
    }

    public int getIncomeId() {
        return incomeId;
    }

    public void setIncomeId(int incomeId) {
        this.incomeId = incomeId;
    }

    public String getInputType() {
        return inputType;
    }

    public void setInputType(String inputType) {
        this.inputType = inputType;
    }

    public float getInputAmount() {
        return inputAmount;
    }

    public void setInputAmount(float inputAmount) {
        this.inputAmount = inputAmount;
    }

    public String getIncomeDate() {
        return incomeDate;
    }

    public void setIncomeDate(String incomeDate) {
        this.incomeDate = incomeDate;
    }

    public String getIncomeTime() {
        return incomeTime;
    }

    public void setIncomeTime(String incomeTime) {
        this.incomeTime = incomeTime;
    }

    String incomeDate;
    String incomeTime;
}
