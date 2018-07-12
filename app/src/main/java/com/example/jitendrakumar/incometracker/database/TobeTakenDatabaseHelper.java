package com.example.jitendrakumar.incometracker.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class TobeTakenDatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "Taken.db";
    public static final String TABLE_NAME5 = "taken_table";
    public static final String COL_1 = "TAKEN_ID";
    public static final String COL_2 = "PERSON_NAME";
    public static final String COL_3 = "TAKEN_AMOUNT";
    public static final String COL_4 = "TAKEN_REASON";
    public static final String COL_5 = "TAKEN_DATE";

    public TobeTakenDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);


    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_NAME5 + " (TAKEN_ID INTEGER PRIMARY KEY AUTOINCREMENT,PERSON_NAME TEXT NOT NULL,TAKEN_AMOUNT FLOAT NOT NULL, TAKEN_REASON TEXT NOT NULL, TAKEN_DATE TEXT NOT NULL)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME5);
        onCreate(db);

    }
    // Function insertData() to insert the data in the table/Database

    public boolean insertTakenData(String person_name, String taken_amount, String taken_reason, String taken_date){
        SQLiteDatabase db  = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put( COL_2, person_name );
        contentValues.put( COL_3, taken_amount );
        contentValues.put( COL_4, taken_reason );
        contentValues.put( COL_5, taken_date );
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

    public boolean updateIncomeData(String income_id, String income_type, String amount, String date, String time){
        SQLiteDatabase db  = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put( COL_1, income_id );
        contentValues.put( COL_2, income_type );
        contentValues.put( COL_3, amount );
        contentValues.put( COL_4,  date );
        contentValues.put( COL_5, time );
        db.update( TABLE_NAME5, contentValues, "INCOME_ID = ?", new String[] {income_id});
        return true;
    }

    // Function deleteData() to delete any data/record from the database

    public Integer deleteIncomeData(String income_id){
        SQLiteDatabase db  = this.getWritableDatabase();
        return db.delete( TABLE_NAME5, "iNCOME_ID = ?",new String[] {income_id}  );

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


}
