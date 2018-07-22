package com.example.jitendrakumar.incometracker.database.db

import android.content.ContentValues
import android.database.sqlite.SQLiteDatabase
import android.util.Log
import com.example.jitendrakumar.incometracker.models.Todo

class TaskTable {
    companion object {
        val TABLE_NAME = "tasks"
        // PRIMARY KEY to be set before AUTOINCREMENT
        val CMD_CREATE_TABLE = """
            CREATE TABLE IF NOT EXISTS ${TABLE_NAME} (
                ${Columns.ID} INTEGER PRIMARY KEY AUTOINCREMENT,
                ${Columns.TASK} TEXT,
                ${Columns.TASK_DATE} TEXT,
                ${Columns.TASK_TIME} TEXT,
                ${Columns.DONE} BOOLEAN
            );
        """.trimIndent()

        fun addTask(db: SQLiteDatabase, task: Todo): Long {
            val row = ContentValues()
            row.put(Columns.TASK, task.taskName)
            row.put(Columns.TASK_DATE, task.taskDate)
            row.put(Columns.TASK_TIME, task.taskTime)
            row.put(Columns.DONE, task.done)
            return db.insert(TABLE_NAME, null, row)
        }
        fun deleteTask(db: SQLiteDatabase, task: Todo): Int {
            return db.delete(
                    TABLE_NAME,
                    "${Columns.ID} = ?",
                    arrayOf(task.id.toString())
            )
        }
        fun deleteTask(db: SQLiteDatabase, doneStatus: Boolean): Int {
            var doneVal = 0
            if (doneStatus) doneVal = 1
            return db.delete(
                    TABLE_NAME,
                    "${Columns.DONE} = ?",
                    arrayOf(doneVal.toString())

            )
        }
        fun updateTask(db: SQLiteDatabase, task: Todo) {
            if (task.id == null) {
                Log.e("TASK", "No ID found to update")
                return
            }
            val updatedTask = ContentValues()
            updatedTask.put(Columns.TASK, task.taskName)
            updatedTask.put(Columns.TASK_DATE, task.taskDate)
            updatedTask.put(Columns.TASK_TIME, task.taskTime)
            updatedTask.put(Columns.DONE, task.done)
            val updatedRows = db.update(
                    TABLE_NAME,
                    updatedTask,
                    "${Columns.ID} = ?",
                    arrayOf(task.id.toString())
            )
            Log.d("TASK", updatedRows.toString())
        }
        fun getAllTasks(db: SQLiteDatabase): ArrayList<Todo> {
            val tasks = ArrayList<Todo>()
            val cursor = db.query(
                    TABLE_NAME,
                    arrayOf(Columns.ID, Columns.TASK, Columns.TASK_DATE, Columns.TASK_TIME, Columns.DONE),
                    null, null,
                    null, null,
                    Columns.TASK_DATE+", "+Columns.TASK_TIME
            )
            val idCol = cursor.getColumnIndex(Columns.ID)
            val taskCol = cursor.getColumnIndex(Columns.TASK)
            val dateCol = cursor.getColumnIndex(Columns.TASK_DATE)
            val timeCol = cursor.getColumnIndex(Columns.TASK_TIME)
            val donCol = cursor.getColumnIndex(Columns.DONE)

            while (cursor.moveToNext()) {
                val rowTask = Todo(
                        cursor.getInt(idCol),
                        cursor.getString(taskCol),
                        cursor.getString(dateCol),
                        cursor.getString(timeCol),
                        cursor.getInt(donCol) == 1

                )
                tasks.add(rowTask)
            }

            return tasks
        }
    }

    object Columns {
        val ID = "id"
        val TASK = "task"
        val TASK_DATE = "todo_date"
        val TASK_TIME = "todo_time"
        val DONE = "done"
    }
}