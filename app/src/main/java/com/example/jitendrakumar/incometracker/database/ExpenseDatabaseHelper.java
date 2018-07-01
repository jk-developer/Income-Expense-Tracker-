package com.example.jitendrakumar.incometracker.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.Date;

public class ExpenseDatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "UserExpense.db";
    public static final String TABLE_NAME = "expense_table";
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
        db.execSQL("create table " + TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT,EXPENSE_TYPE TEXT NOT NULL,AMOUNT FLOAT, DATE DATE NOT NULL, TIME TEXT NOT NULL) ");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(db);

    }
    // Function insertData() to insert the data in the table/Database

    public boolean insertData(String expense_type, Float amount, Date date, String time){
        SQLiteDatabase db  = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put( COL_2, expense_type );
        contentValues.put( COL_3, amount );
        contentValues.put( COL_4, String.valueOf( date ) );
        contentValues.put( COL_5, time );
        long res =  db.insert( TABLE_NAME, null, contentValues );
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

    public Cursor getAllData(){
        SQLiteDatabase db  = this.getWritableDatabase();
        Cursor res  = db.rawQuery( "select * from "+TABLE_NAME, null );
        return res;
    }

    // Function updateData() to update/change the existing data in database

    public boolean updateData(String id, String expense_type, Float amount, Date date, String time){
        SQLiteDatabase db  = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put( COL_1, id );
        contentValues.put( COL_2, expense_type );
        contentValues.put( COL_3, amount );
        contentValues.put( COL_4, String.valueOf( date ) );
        contentValues.put( COL_5, time );
        db.update( TABLE_NAME, contentValues, "ID = ?", new String[] {id});
        return true;
    }

    // Function deleteData() to delete any data/record from the database

    public Integer deleteData(String id){
        SQLiteDatabase db  = this.getWritableDatabase();
        return db.delete( TABLE_NAME, "ID = ?",new String[] {id}  );

    }

}

