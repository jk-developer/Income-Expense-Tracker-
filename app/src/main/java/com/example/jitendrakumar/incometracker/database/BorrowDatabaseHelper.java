package com.example.jitendrakumar.incometracker.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class BorrowDatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "Paid.db";
    public static final String TABLE_NAME4 = "paying_table";
    public static final int  BORROW_VERSION = 2;
    public static final String COL_1 = "PAYING_ID";
    public static final String COL_2 = "PERSON_NAME";
    public static final String COL_3 = "PAYING_AMOUNT";
    public static final String COL_4 = "PAYING_REASON";
    public static final String COL_5 = "PAYING_YEAR";
    public static final String COL_6 = "PAYING_MONTH";
    public static final String COL_7 = "PAYING_DAY";

    public BorrowDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, BORROW_VERSION);


    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_NAME4 + " (PAYING_ID INTEGER PRIMARY KEY AUTOINCREMENT,PERSON_NAME TEXT NOT NULL,PAYING_AMOUNT FLOAT NOT NULL, PAYING_REASON TEXT NOT NULL, PAYING_YEAR INTEGER NOT NULL, PAYING_MONTH INTEGER NOT NULL, PAYING_DAY INTEGER NOT NULL)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME4);
        onCreate(db);

    }
    // Function insertData() to insert the data in the table/Database

    public boolean insertPayingData(String person_name, float paying_amount, String paying_reason, int paying_year, int paying_month, int paying_day){
        SQLiteDatabase db  = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put( COL_2, person_name );
        contentValues.put( COL_3, paying_amount );
        contentValues.put( COL_4, paying_reason );
        contentValues.put( COL_5, paying_year );
        contentValues.put( COL_6, paying_month );
        contentValues.put( COL_7, paying_day );
        long res =  db.insert( TABLE_NAME4, null, contentValues );
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

    public Cursor getAllPayingData(){
        SQLiteDatabase db  = this.getWritableDatabase();
        Cursor res  = db.rawQuery( "select * from "+TABLE_NAME4, null );
        return res;
    }

    public Cursor getAllPayingReport(String payingid, String dateFrom, String dateTo){
        SQLiteDatabase db  = this.getWritableDatabase();

        Cursor res  = db.rawQuery( "select * from "+TABLE_NAME4+" where PAYING_ID = "+payingid+" and PAYING_DATE >= "+ dateFrom + " and PAYING_DATE <= "+ dateTo, null );
        return res;
    }

    // Function updateData() to update/change the existing data in database

    public boolean updateBorrowData(String borrow_id, String person, float amount, String desc, int year, int month, int day){
        SQLiteDatabase db  = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put( COL_1, borrow_id );
        contentValues.put( COL_2, person );
        contentValues.put( COL_3, amount );
        contentValues.put( COL_4, desc );
        contentValues.put( COL_5,  year );
        contentValues.put( COL_6, month );
        contentValues.put( COL_7, day );
        db.update( TABLE_NAME4, contentValues, "PAYING_ID = ?", new String[] {borrow_id});
        return true;
    }

    // Function deleteData() to delete any data/record from the database

    public Integer deleteBorrowData(String borrow_id){
        SQLiteDatabase db  = this.getWritableDatabase();
        return db.delete( TABLE_NAME4, "PAYING_ID = ?",new String[] {borrow_id}  );

    }

    public float getTotalPaidTo()
    {
        SQLiteDatabase db  = this.getWritableDatabase();
        Cursor cur = db.rawQuery( "SELECT SUM(PAYING_AMOUNT) FROM "+TABLE_NAME4, null );
        if(cur.moveToFirst()){
            return cur.getFloat( 0 );
        }
        return (float) 0.0;
    }

    public Cursor getDataNamewise(String pName){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor name = db.rawQuery( "SELECT * FROM "+ TABLE_NAME4+ " WHERE PERSON_NAME = ?", new String[]{pName} );
        return name;
    }


    public Cursor getRecordbwyears(int y1, int y2, int m1, int m2, int d1, int d2){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor record = db.rawQuery( "SELECT * FROM "+TABLE_NAME4+ " WHERE (PAYING_YEAR BETWEEN "+y1 + " AND "+ y2 + ") AND (PAYING_MONTH BETWEEN "+m1 + " AND "+ m2+ ") AND (PAYING_DAY BETWEEN "+d1 + " AND "+ d2+ ") ORDER BY PAYING_YEAR,PAYING_MONTH, PAYING_DAY", null );
        return record;
    }

    public Cursor getRecordbwDays(int y1, int y2, int m1, int m2){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor r = db.rawQuery( "SELECT * FROM "+TABLE_NAME4+ " WHERE (PAYING_YEAR BETWEEN "+y1 + " AND "+ y2+ ") AND (PAYING_MONTH BETWEEN "+m1 + " AND "+ m2+ ") ORDER BY PAYING_YEAR, PAYING_MONTH", null );
        return r;
    }

    public Cursor getMonthlyBorrow()
    {
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor curs = DB.rawQuery( "SELECT PAYING_MONTH, SUM(PAYING_AMOUNT) FROM "+TABLE_NAME4+" GROUP BY(PAYING_MONTH)", null );
        return curs;

    }

    public void deleteAllBorrowData(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from "+ TABLE_NAME4);
    }



}
