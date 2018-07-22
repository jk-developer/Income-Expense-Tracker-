package com.example.jitendrakumar.incometracker.database.db

import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.content.Context
import android.util.Log
import com.example.jitendrakumar.incometracker.database.db.TaskTable.Companion.TABLE_NAME

val DB_NAME = "todo.db"
val DB_VER = 8

class TodoDbHelper(context: Context?) : SQLiteOpenHelper(context, DB_NAME, null, DB_VER) {

    override fun onCreate(db: SQLiteDatabase?) {
        db?.let{
            it.execSQL(TaskTable.CMD_CREATE_TABLE)
            Log.d("table", "created")
        }
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

}