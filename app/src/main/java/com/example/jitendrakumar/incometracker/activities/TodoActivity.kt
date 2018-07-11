package com.example.jitendrakumar.incometracker.activities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import com.example.jitendrakumar.incometracker.R
import com.example.jitendrakumar.incometracker.adapters.MyTodoAdapter
import com.example.jitendrakumar.incometracker.database.db.TaskTable
import com.example.jitendrakumar.incometracker.database.db.TodoDbHelper
import com.example.jitendrakumar.incometracker.models.Todo
import kotlinx.android.synthetic.main.activity_todo.*

class TodoActivity : AppCompatActivity() {
    val tasks = ArrayList<Todo>()
    lateinit var taskAdapter: MyTodoAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_todo)
        val db = TodoDbHelper(this).writableDatabase

        rvTasks.layoutManager = LinearLayoutManager(this)
        fun refreshTodos () {
            tasks.clear()
            tasks.addAll(TaskTable.getAllTasks(db))
            taskAdapter.notifyDataSetChanged()
        }

        val onTaskUpdate = {
            task: Todo ->
            TaskTable.updateTask(db, task)
            refreshTodos()
        }
        val onTaskDelete = {
            task: Todo ->
            TaskTable.deleteTask(db, task)
            refreshTodos()
        }

        taskAdapter = MyTodoAdapter(tasks, onTaskUpdate, onTaskDelete)
        rvTasks.adapter = taskAdapter

        refreshTodos()


        btnAddTask.setOnClickListener {
            val newTask = Todo(
                    null,
                    etTaskName.text.toString(),
                    etTaskDate.text.toString(),
                    false
            )
            val id = TaskTable.addTask(db, newTask)
            refreshTodos()
            Log.d("TASK", "INSERTED AT ${id}")
            taskAdapter.notifyDataSetChanged()
        }

        btnClearTask.setOnClickListener {
            TaskTable.deleteTask(db, true)
            refreshTodos()
        }

    }

}
