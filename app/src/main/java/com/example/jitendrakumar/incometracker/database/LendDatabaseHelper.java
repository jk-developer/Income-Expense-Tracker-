package com.example.jitendrakumar.incometracker.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class LendDatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "Taken.db";
    public static final String TABLE_NAME5 = "taken_table";
    public static final int LEND_VERSION = 2;
    public static final String COL_1 = "TAKEN_ID";
    public static final String COL_2 = "PERSON_NAME";
    public static final String COL_3 = "TAKEN_AMOUNT";
    public static final String COL_4 = "TAKEN_REASON";
    public static final String COL_5 = "LEND_YEAR";
    public static final String COL_6 = "LEND_MONTH";
    public static final String COL_7 = "LEND_DAY";
    public LendDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, LEND_VERSION);


    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_NAME5 + " (TAKEN_ID INTEGER PRIMARY KEY AUTOINCREMENT, PERSON_NAME TEXT NOT NULL, TAKEN_AMOUNT FLOAT NOT NULL, TAKEN_REASON TEXT NOT NULL, LEND_YEAR INTEGER NOT NULL, LEND_MONTH INTEGER NOT NULL, LEND_DAY INTEGER NOT NULL)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME5);
        onCreate(db);

    }
    // Function insertData() to insert the data in the table/Database

    public boolean insertTakenData(String person_name,float taken_amount, String taken_reason,int lend_year, int lend_month, int lend_day){
        SQLiteDatabase db  = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put( COL_2, person_name );
        contentValues.put( COL_3, taken_amount );
        contentValues.put( COL_4, taken_reason );
        contentValues.put( COL_5, lend_year );
        contentValues.put( COL_6, lend_month );
        contentValues.put( COL_7, lend_day );
        long res =  db.insert( TABLE_NAME5, null, contentValues );
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

    public Cursor getAllTakenData(){
        SQLiteDatabase db  = this.getWritableDatabase();
        Cursor res  = db.rawQuery( "select * from "+TABLE_NAME5, null );
        return res;
    }

    public Cursor getAllTakenReport(String takenid, String dateFrom, String dateTo){
        SQLiteDatabase db  = this.getWritableDatabase();

        Cursor res  = db.rawQuery( "select * from "+TABLE_NAME5+" where TAKEN_ID = "+takenid+" and TAKEN_DATE >= "+ dateFrom + " and TAKEN_DATE <= "+ dateTo, null );
        return res;
    }

    // Function updateData() to update/change the existing data in database

    public boolean updateLendData(String lend_id, String person, float amount, String desc, int year, int month, int day){
        SQLiteDatabase db  = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put( COL_1, lend_id );
        contentValues.put( COL_2, person );
        contentValues.put( COL_3, amount );
        contentValues.put( COL_4,  desc);
        contentValues.put( COL_5,  year );
        contentValues.put( COL_6, month );
        contentValues.put( COL_7, day );
        db.update( TABLE_NAME5, contentValues, "TAKEN_ID = ?", new String[] {lend_id});
        return true;
    }

    // Function deleteData() to delete any data/record from the database

    public Integer deleteLendData(String lend_id){
        SQLiteDatabase db  = this.getWritableDatabase();
        return db.delete( TABLE_NAME5, "TAKEN_ID = ?",new String[] {lend_id}  );

    }

    public float getTotalTaken()
    {
        SQLiteDatabase db  = this.getWritableDatabase();
        Cursor cur = db.rawQuery( "SELECT SUM(TAKEN_AMOUNT) FROM "+TABLE_NAME5, null );
        if(cur.moveToFirst()){
            return cur.getFloat( 0 );
        }
        return (float) 0.0;
    }



    public Cursor getDataNamewise(String pName){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor name = db.rawQuery( "SELECT * FROM "+ TABLE_NAME5+ " WHERE PERSON_NAME = ?", new String[]{pName} );
        return name;
    }


    public Cursor getRecordbwyears(int y1, int y2, int m1, int m2, int d1, int d2){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor record = db.rawQuery( "SELECT * FROM "+TABLE_NAME5+ " WHERE (LEND_YEAR BETWEEN "+y1 + " AND "+ y2 + ") AND (LEND_MONTH BETWEEN "+m1 + " AND "+ m2+ ") AND (LEND_DAY BETWEEN "+d1 + " AND "+ d2+ ") ORDER BY LEND_YEAR,LEND_MONTH,LEND_DAY", null );
        return record;
    }

    public Cursor getRecordbwDays(int y1, int y2, int m1, int m2){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor r = db.rawQuery( "SELECT * FROM "+TABLE_NAME5+ " WHERE (LEND_YEAR BETWEEN "+y1 + " AND "+ y2+ ") AND (LEND_MONTH BETWEEN "+m1 + " AND "+ m2+ ") ORDER BY LEND_YEAR, LEND_MONTH", null );
        return r;
    }

    public Cursor getMonthlyLend()
    {
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor curs = DB.rawQuery( "SELECT LEND_MONTH, SUM(TAKEN_AMOUNT) FROM "+TABLE_NAME5+" GROUP BY(LEND_MONTH)", null );
        return curs;

    }
    public void deleteAllLendData(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from "+ TABLE_NAME5);
    }


}
