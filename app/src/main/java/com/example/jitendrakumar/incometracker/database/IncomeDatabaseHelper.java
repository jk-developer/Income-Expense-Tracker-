package com.example.jitendrakumar.incometracker.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import java.util.Date;

public class IncomeDatabaseHelper extends SQLiteOpenHelper {
    public final static String TAG = "db";

    public static final String DATABASE_NAME = "Income.db";
    public static final String TABLE_NAME3 = "income_table";
    private static final Integer VERSION = 2;
    public static final String COL_1 = "INCOME_ID";
    public static final String COL_2 = "INCOME_TYPE";
    public static final String COL_3 = "AMOUNT";
    public static final String COL_4 = "DATE";
    public static final String COL_5 = "TIME";


    public IncomeDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);


    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_NAME3 + " (INCOME_ID INTEGER PRIMARY KEY AUTOINCREMENT,INCOME_TYPE TEXT NOT NULL,AMOUNT FLOAT, DATE TEXT NOT NULL, TIME TEXT NOT NULL) ");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME3);
        onCreate(db);

    }
    // Function insertData() to insert the data in the table/Database

    public boolean insertIncomeData(String income_type, String amount, String date, String time, boolean isChecked){
        SQLiteDatabase db  = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put( COL_2, income_type );
        contentValues.put( COL_3, amount );
        contentValues.put( COL_4, date );
        contentValues.put( COL_5, time );


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

    public Cursor getAllIncomeReport(String dateFrom, String dateTo){
        SQLiteDatabase db  = this.getWritableDatabase();
        Log.d( TAG, "getAllIncomeReport: "+dateFrom+dateFrom );

        Cursor res  = db.rawQuery( "select * from "+TABLE_NAME3+" where DATE >= "+ dateFrom + " and DATE <= "+ dateTo, null );
        res.moveToNext();
        //Log.d( TAG, "getAllIncomeReport: "+res.getString( 2 ) );
            return res;
    }

    // Function updateData() to update/change the existing data in database

    public boolean updateIncomeData(String income_id, String income_type, String amount, String date, String time){
        SQLiteDatabase db  = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put( COL_1, income_id );
        contentValues.put( COL_2, income_type );
        contentValues.put( COL_3, amount );
        contentValues.put( COL_4,  date );
        contentValues.put( COL_5, time );
        db.update( TABLE_NAME3, contentValues, "INCOME_ID = ?", new String[] {income_id});
        return true;
    }

    // Function deleteData() to delete any data/record from the database

    public Integer deleteIncomeData(String income_id){
        SQLiteDatabase db  = this.getWritableDatabase();
        return db.delete( TABLE_NAME3, "iNCOME_ID = ?",new String[] {income_id}  );

    }

}

