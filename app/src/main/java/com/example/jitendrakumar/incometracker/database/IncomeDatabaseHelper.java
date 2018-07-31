package com.example.jitendrakumar.incometracker.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

public class IncomeDatabaseHelper extends SQLiteOpenHelper {
    public final static String TAG = "db";

    public static final String DATABASE_NAME = "Income.db";
    public static final String TABLE_NAME3 = "income_table";
    private static final Integer VERSION = 4;
    public static final String COL_1 = "INCOME_ID";
    public static final String COL_2 = "INCOME_TYPE";
    public static final String COL_3 = "AMOUNT";
    public static final String COL_4 = "DATE_YEAR";
    public static final String COL_5 = "DATE_MONTH";
    public static final String COL_6 = "DATE_DAY";
    public static final String COL_7 = "TIME_HOUR";
    public static final String COL_8 = "TIME_MINUTE";
    public static final String COL_9 = "INCOME_DESC";


    public IncomeDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);


    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_NAME3 + " (INCOME_ID INTEGER PRIMARY KEY AUTOINCREMENT, INCOME_TYPE TEXT NOT NULL, AMOUNT FLOAT NOT NULL, DATE_YEAR INTEGER NOT NULL, DATE_MONTH INTEGER NOT NULL, DATE_DAY INTEGER NOT NULL, TIME_HOUR INTEGER NOT NULL, TIME_MINUTE INTEGER NOT NULL, INCOME_DESC TEXT) ");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME3);
        onCreate(db);

    }
    // Function insertData() to insert the data in the table/Database

    public boolean insertIncomeData(String income_type, float amount, int year, int month, int day, int hour, int minute, String descrip){
        SQLiteDatabase db  = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put( COL_2, income_type );
        contentValues.put( COL_3, amount );
        contentValues.put( COL_4, year );
        contentValues.put( COL_5, month );
        contentValues.put( COL_6, day );
        contentValues.put( COL_7 , hour);
        contentValues.put( COL_8, minute );
        contentValues.put( COL_9, descrip );

        long res =  db.insert( TABLE_NAME3, null, contentValues );
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

    public Cursor getAllIncomeData(){
        SQLiteDatabase db  = this.getWritableDatabase();
        Cursor res  = db.rawQuery( "select * from "+TABLE_NAME3, null );
        return res;
    }

    public Cursor getMonthlyIncomeReport(int months, int monthf){
        SQLiteDatabase db  = this.getWritableDatabase();
        Cursor res  = db.rawQuery( "select * from "+TABLE_NAME3+" where (DATE_MONTH >="+months +" )", null );
            return res;
    }

    // Function updateData() to update/change the existing data in database

    public boolean updateIncomeData(String income_id, String income_type, float amount, int year, int month, int day, int hour, int minute, String descrip){
        SQLiteDatabase db  = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put( COL_1, income_id );
        contentValues.put( COL_2, income_type );
        contentValues.put( COL_3, amount );
        contentValues.put( COL_4,  year);
        contentValues.put( COL_5, month);
        contentValues.put( COL_5, month );
        contentValues.put( COL_6, day );
        contentValues.put( COL_7, hour );
        contentValues.put( COL_8, minute );
        contentValues.put( COL_9, descrip );

        db.update( TABLE_NAME3, contentValues, "INCOME_ID = ?", new String[] {income_id});
        return true;
    }

    // Function deleteData() to delete any data/record from the database

    public Integer deleteIncomeData(String income_id){
        SQLiteDatabase db  = this.getWritableDatabase();
        return db.delete( TABLE_NAME3, "INCOME_ID = ?",new String[] {income_id}  );

    }

    public float getTotalIncome()
    {
        SQLiteDatabase db  = this.getWritableDatabase();
        Cursor cur = db.rawQuery( "SELECT SUM(AMOUNT) FROM "+TABLE_NAME3, null );
        if(cur.moveToFirst()){
            return cur.getFloat( 0 );
        }
        return (float) 0.0;
    }

    public Cursor getMonthlyIncome()
    {
        SQLiteDatabase DB = this.getWritableDatabase();
            Cursor curs = DB.rawQuery( "SELECT DATE_MONTH, SUM(AMOUNT) FROM "+TABLE_NAME3+" GROUP BY(DATE_MONTH)", null );
        Log.d( TAG, "getMonthlyIncome: "+curs.getCount() );
           return curs;

    }

    public float getTodaysIncome(String year, String month, String  day)
    {   float income;
      SQLiteDatabase DB = this.getWritableDatabase();
            Cursor curs = DB.rawQuery( "SELECT SUM(AMOUNT) FROM "+TABLE_NAME3+" WHERE DATE_YEAR = ? AND DATE_MONTH = ? AND DATE_DAY = ?" ,new String[] {year, month, day});
            Log.d( TAG, "getTodaysIncome: "+curs.getCount() );
            if(curs.getCount()==0){
                return (float) 0.0;

            }
            else{
                curs.moveToNext();
                income = curs.getFloat( 0 );
                Log.d( TAG, "getTodaysIncome: amount"+income );
            }
         return income;
    }

    public float getThisMonthData(String m){
        float thisMonthAmount;
        Log.d( TAG, "getThisMonthData: month"+ m );
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor curs = db.rawQuery( "SELECT SUM(AMOUNT) FROM "+TABLE_NAME3+" WHERE DATE_MONTH = ?", new String[] {m} );
        if (curs.getCount() == 0) {
          //  Toast.makeText(, "No Income  is added This Month !!!", Toast.LENGTH_SHORT ).show();
            return (float)0.0;

        } else {
            curs.moveToNext();
            thisMonthAmount = curs.getFloat( 0 );
            Log.d( "hel", "onClick: income"+ thisMonthAmount );

        }
        return thisMonthAmount;
    }

    public void deleteAllRecords(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL( "DELETE FROM "+TABLE_NAME3 );

    }


    public Cursor getRecordbwMonths(int m1, int m2){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor r = db.rawQuery( "SELECT * FROM "+TABLE_NAME3+ " WHERE DATE_MONTH BETWEEN "+m1 + " AND "+ m2+ " ORDER BY DATE_MONTH", null );
        return r;
    }

    public Cursor getRecordbwDays(int y1, int y2, int m1, int m2){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor r = db.rawQuery( "SELECT * FROM "+TABLE_NAME3+ " WHERE (DATE_YEAR BETWEEN "+y1 + " AND "+ y2+ ") AND (DATE_MONTH BETWEEN "+m1 + " AND "+ m2+ ") ORDER BY DATE_YEAR, DATE_MONTH", null );
        return r;
    }

    public Cursor getRecordbwyears(int y1, int y2, int m1, int m2, int d1, int d2){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor record = db.rawQuery( "SELECT * FROM "+TABLE_NAME3+ " WHERE (DATE_YEAR BETWEEN "+y1 + " AND "+ y2 + ") AND (DATE_MONTH BETWEEN "+m1 + " AND "+ m2+ ") AND (DATE_DAY BETWEEN "+d1 + " AND "+ d2+ ") ORDER BY DATE_YEAR, DATE_MONTH, DATE_DAY", null );
        return record;
    }

    public Cursor getRecordsCategorywise(String cat){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery( "SELECT * FROM "+ TABLE_NAME3+ " WHERE INCOME_TYPE = ?", new String[] {cat} );
        return res;
    }


}

