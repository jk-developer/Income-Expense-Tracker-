package com.example.jitendrakumar.incometracker.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.Date;

public class ExpenseDatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "Expense.db";
    public static final String TABLE_NAME2 = "expense_table";

    private static final Integer VERSION = 1;
    public static final String COL_1 = "ID";
    public static final String COL_2 = "EXPENSE_TYPE";
    public static final String COL_3 = "AMOUNT";
    public static final String COL_4 = "DATE";
    public static final String COL_5 = "TIME";

    public ExpenseDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);


    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_NAME2 + " (ID INTEGER PRIMARY KEY AUTOINCREMENT,EXPENSE_TYPE TEXT NOT NULL,AMOUNT FLOAT, DATE DATE NOT NULL, TIME TEXT NOT NULL) ");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME2);
        onCreate(db);

    }
    // Function insertData() to insert the data in the table/Database

    public boolean insertExpenseData(String expense_type, String amount, String date, String time){
        SQLiteDatabase db  = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put( COL_2, expense_type );
        contentValues.put( COL_3, amount );
        contentValues.put( COL_4, date );
        contentValues.put( COL_5, time );
        long res =  db.insert( TABLE_NAME2, null, contentValues );
        if(res==-1)
        {
            return false;
        }
        else
        {
            return true;
        }
    }

    // Function getAllData() to get all data from the Database using Cursor

    public Cursor getAllExpenseData(){
        SQLiteDatabase db  = this.getWritableDatabase();
        Cursor res  = db.rawQuery( "select * from "+TABLE_NAME2 ,null);
        return res;
    }

    public Cursor getAllExpenseReport(String datefrom, String dateto){
        SQLiteDatabase db  = this.getWritableDatabase();
        Cursor res  = db.rawQuery( "select * from "+TABLE_NAME2 +"where "+ "DATE >= "+ datefrom + "and DATE <= "+ dateto,null);
        return res;
    }

    // Function updateData() to update/change the existing data in database

    public boolean updateExpenseData(String expense_id, String expense_type, Float amount, Date date, String time){
        SQLiteDatabase db  = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put( COL_1, expense_id );
        contentValues.put( COL_2, expense_type );
        contentValues.put( COL_3, amount );
        contentValues.put( COL_4, String.valueOf( date ) );
        contentValues.put( COL_5, time );
        db.update( TABLE_NAME2, contentValues, "EXPENSE_ID = ?", new String[] {expense_id});
        return true;
    }

    // Function deleteData() to delete any data/record from the database

    public Integer deleteExpenseData(String expense_id){
        SQLiteDatabase db  = this.getWritableDatabase();
        return db.delete( TABLE_NAME2, "EXPENSE_ID = ?",new String[] {expense_id}  );

    }

    public void deleteAllData(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from "+ TABLE_NAME2);
    }

    public float getTotalExpense()
    {
        SQLiteDatabase db  = this.getWritableDatabase();
        Cursor cur = db.rawQuery( "SELECT SUM(AMOUNT) FROM "+TABLE_NAME2, null );
        if(cur.moveToFirst()){
            return cur.getFloat( 0 );
        }
        return (float) 0.0;
    }

  /*  public float getMonthlyExpense(int i)
    {
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor curs = DB.rawQuery( "SELECT SUM(AMOUNT) FROM "+TABLE_NAME2+" WHERE DATE_MONTH ="+i, null );
        if(curs.moveToFirst()){
            return curs.getFloat( 0 );
        }
        return (float) 0.0;

    } */


}

