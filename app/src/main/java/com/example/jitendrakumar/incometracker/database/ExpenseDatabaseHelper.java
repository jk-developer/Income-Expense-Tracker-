package com.example.jitendrakumar.incometracker.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.Date;

public class ExpenseDatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "Expense.db";
    public static final String TABLE_NAME2 = "expense_table";

    private static final Integer VERSION = 5;
    public static final String COL_1 = "ID";
    public static final String COL_2 = "EXPENSE_TYPE";
    public static final String COL_3 = "AMOUNT";
    public static final String COL_4 = "EXPENSE_YEAR";
    public static final String COL_5 = "EXPENSE_MONTH";
    public static final String COL_6 = "EXPENSE_DAY";
    public static final String COL_7 = "EXPENSE_HOUR";
    public static final String COL_8 = "EXPENSE_MINUTE";
    public static final String COL_9 = "EXPENSE_DESC";

    public ExpenseDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);


    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_NAME2 + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, EXPENSE_TYPE TEXT NOT NULL, AMOUNT FLOAT NOT NULL, EXPENSE_YEAR INTEGER NOT NULL, EXPENSE_MONTH INTEGER NOT NULL, EXPENSE_DAY INTEGER NOT NULL, EXPENSE_HOUR INTEGER NOT NULL, EXPENSE_MINUTE INTEGER NOT NULL, EXPENSE_DESC TEXT ) ");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME2);
        onCreate(db);

    }
    // Function insertData() to insert the data in the table/Database

    public boolean insertExpenseData(String expense_type, float amount, int year, int month, int day, int hour, int minute, String expense_desc){
        SQLiteDatabase db  = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put( COL_2, expense_type );
        contentValues.put( COL_3, amount );
        contentValues.put( COL_4, year );
        contentValues.put( COL_5, month );
        contentValues.put( COL_6, day );
        contentValues.put( COL_7 , hour);
        contentValues.put( COL_8, minute );
        contentValues.put( COL_9, expense_desc );

        long res =  db.insert( TABLE_NAME2, null, contentValues );
        if(res==-1)
        {
            Log.d( "expensedb", "insertExpenseData: "+ res );
            return false;

        }
        else
        {
            Log.d( "expensedb", "insertExpenseData: "+ res );
            return true;

        }
    }

    // Function getAllData() to get all data from the Database using Cursor

    public Cursor getAllExpenseData(){
        SQLiteDatabase db  = this.getWritableDatabase();
        Cursor res  = db.rawQuery( "select * from "+TABLE_NAME2 ,null);
 //       Log.d( "db", "getAllExpenseData: "+ res.getInt( 6 ));
        return res;
    }

    public Cursor getAllExpenseReport(int years, int yearf, int months, int monthf,  int days, int dayf ){
        SQLiteDatabase db  = this.getWritableDatabase();
        Cursor res  = db.rawQuery( "select * from "+TABLE_NAME2 +"where (EXPENSE_YEAR >= "+years + " and EXPENSE_YEAR <= "+ yearf+") and (EXPENSE_MONTH >="+months +" and EXPENSE_MONTH <="+monthf+") and (EXPENSE_DAY >="+days+" and EXPENSE_DAY <="+dayf+")",null);
        return res;
    }

    // Function updateData() to update/change the existing data in database

    public boolean updateExpenseData(String expense_id, String expense_type, float amount, int year , int month , int day , int hour, int minute, String desc){
        SQLiteDatabase db  = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put( COL_1, expense_id );
        contentValues.put( COL_2, expense_type );
        contentValues.put( COL_3, amount );
        contentValues.put( COL_4, year );
        contentValues.put( COL_5, month );
        contentValues.put( COL_6, day );
        contentValues.put( COL_7, hour );
        contentValues.put( COL_8, minute );
        contentValues.put( COL_9, desc );

        db.update( TABLE_NAME2, contentValues, "ID = ?", new String[] {expense_id});
        return true;
    }

    // Function deleteData() to delete any data/record from the database

    public Integer deleteExpenseData(String expense_id){
        SQLiteDatabase db  = this.getWritableDatabase();
        return db.delete( TABLE_NAME2, "ID = ?",new String[] {expense_id}  );

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

    public Cursor getMonthlyExpense()
   {
                SQLiteDatabase DB = this.getWritableDatabase();
                Cursor curs = DB.rawQuery( "SELECT EXPENSE_MONTH, SUM(AMOUNT) FROM "+TABLE_NAME2+" GROUP BY(EXPENSE_MONTH)", null );
                Log.d( "EXPENSE", "getMonthlyIncome: "+curs.getCount() );
                return curs;
   }

    public Cursor getRecordbwMonths(int m1, int m2){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor r = db.rawQuery( "SELECT * FROM "+TABLE_NAME2+ " WHERE EXPENSE_MONTH BETWEEN "+m1 + " AND "+ m2+ " ORDER BY EXPENSE_MONTH", null );
        return r;
    }

    public Cursor getRecordbwDays(int y1, int y2, int m1, int m2){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor r = db.rawQuery( "SELECT * FROM "+TABLE_NAME2+ " WHERE (EXPENSE_YEAR BETWEEN "+y1 + " AND "+ y2+ ") AND (EXPENSE_MONTH BETWEEN "+m1 + " AND "+ m2+ ") ORDER BY EXPENSE_YEAR, EXPENSE_MONTH", null );
        return r;
    }

    public Cursor getRecordbwyears(int y1, int y2, int m1, int m2, int d1, int d2){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor record = db.rawQuery( "SELECT * FROM "+TABLE_NAME2+ " WHERE (EXPENSE_YEAR BETWEEN "+y1 + " AND "+ y2 + ") AND (EXPENSE_MONTH BETWEEN "+m1 + " AND "+ m2+ ") AND (EXPENSE_DAY BETWEEN "+d1 + " AND "+ d2+ ") ORDER BY EXPENSE_YEAR, EXPENSE_MONTH,EXPENSE_DAY", null );
        return record;
    }

    public Cursor getRecordsCategorywise(String cat){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery( "SELECT * FROM "+ TABLE_NAME2+ " WHERE EXPENSE_TYPE = ?", new String[]{cat} );
        return res;
    }
}

