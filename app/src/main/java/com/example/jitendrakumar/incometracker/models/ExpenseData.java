package com.example.jitendrakumar.incometracker.models;

public class ExpenseData {
    int expenseId;
    String expenseType;
    float expenseAmount;

    public String getExpenseDesc() {
        return expenseDesc;
    }

    public void setExpenseDesc(String expenseDesc) {
        this.expenseDesc = expenseDesc;
    }

    String expenseDesc;

    public ExpenseData(int expensId, String expenseType, float expenseAmount, String expenseDate, String expenseTime,String expenseDesc) {
        this.expenseId = expensId;
        this.expenseType = expenseType;
        this.expenseAmount = expenseAmount;
        this.expenseDate = expenseDate;
        this.expenseTime = expenseTime;
        this.expenseDesc = expenseDesc;
    }

    public int getExpenseId() {
        return expenseId;
    }

    public void setExpenseId(int expensId) {
        this.expenseId = expensId;
    }

    public String getExpenseType() {
        return expenseType;
    }

    public void setExpenseType(String expenseType) {
        this.expenseType = expenseType;
    }

    public float getExpenseAmount() {
        return expenseAmount;
    }

    public void setExpenseAmount(float expenseAmount) {
        this.expenseAmount = expenseAmount;
    }

    public String getExpenseDate() {
        return expenseDate;
    }

    public void setExpenseDate(String expenseDate) {
        this.expenseDate = expenseDate;
    }

    public String getExpenseTime() {
        return expenseTime;
    }

    public void setExpenseTime(String expenseTime) {
        this.expenseTime = expenseTime;
    }

    String expenseDate;
    String expenseTime;
}
