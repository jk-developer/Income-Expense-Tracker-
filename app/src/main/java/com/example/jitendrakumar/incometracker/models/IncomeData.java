package com.example.jitendrakumar.incometracker.models;

public class IncomeData {
    int incomeId;
    String inputType;
    Double inputAmount;

    public IncomeData(int incomeId, String inputType, Double inputAmount, String date, String time) {
        this.incomeId = incomeId;
        this.inputType = inputType;
        this.inputAmount = inputAmount;
        Date = date;
        Time = time;
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

    public Double getInputAmount() {
        return inputAmount;
    }

    public void setInputAmount(Double inputAmount) {
        this.inputAmount = inputAmount;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getTime() {
        return Time;
    }

    public void setTime(String time) {
        Time = time;
    }

    // String Day;
    String Date;
    String Time;

}
