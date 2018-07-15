package com.example.jitendrakumar.incometracker.models;

public class ExpenseData {
    int expenseId;
    String expenseType;
    float expenseAmount;

    public ExpenseData(int expensId, String expenseType, float expenseAmount, String expenseDate, String expenseTime) {
        this.expenseId = expensId;
        this.expenseType = expenseType;
        this.expenseAmount = expenseAmount;
        this.expenseDate = expenseDate;
        this.expenseTime = expenseTime;
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
