package com.example.jitendrakumar.incometracker.activities

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.View
import android.widget.DatePicker
import android.widget.TimePicker
import android.widget.Toast
import com.example.jitendrakumar.incometracker.R
import com.example.jitendrakumar.incometracker.adapters.MyTodoAdapter
import com.example.jitendrakumar.incometracker.database.db.TaskTable
import com.example.jitendrakumar.incometracker.database.db.TodoDbHelper
import com.example.jitendrakumar.incometracker.fragments.date_time_fragment.DatePickerFragment
import com.example.jitendrakumar.incometracker.models.Todo
import kotlinx.android.synthetic.main.activity_todo.*
import java.util.*

class TodoActivity : AppCompatActivity(), DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {

    val tasks = ArrayList<Todo>()
    lateinit var taskAdapter: MyTodoAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_todo)

        val c = Calendar.getInstance()
        val incyear = c.get(Calendar.YEAR)
        val incmonth = c.get(Calendar.MONTH)
        val incday = c.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(this, this@TodoActivity, incyear, incmonth, incday)

        val cTime = Calendar.getInstance()
        val inchour = cTime.get(Calendar.HOUR_OF_DAY)
        val incminute = cTime.get(Calendar.MINUTE)
        val timePickerDialog = TimePickerDialog(this, this@TodoActivity, inchour, incminute, android.text.format.DateFormat.is24HourFormat(this))

        tvTaskDate.setText(incday.toString()+"/"+(incmonth+1)+"/"+incyear)
        tvTaskTime.setText(inchour.toString()+":"+incminute)

        supportActionBar!!.title = "Todo Task"
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

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
                    tvTaskDate.text.toString(),
                    tvTaskTime.text.toString(),
                    false
            )
            if(etTaskName.text.toString().length==0)
            {
                etTaskName.setError("Task Name field is required!!!");
            }
            if(tvTaskDate.text.toString().length==0)
            {
                Toast.makeText(this, "Task Date field is required!!!", Toast.LENGTH_SHORT).show()
            }
            if(tvTaskTime.text.toString().length==0)
            {
                Toast.makeText(this, "Task Time field is required!!!", Toast.LENGTH_SHORT).show()
            }
            else{
                val id = TaskTable.addTask(db, newTask)
                refreshTodos()
                Log.d("TASK", "INSERTED AT ${id}")
                taskAdapter.notifyDataSetChanged()
            }

        }

        btnClearTask.setOnClickListener {
            TaskTable.deleteTask(db, true)
            refreshTodos()
        }

        tvHintTaskDate.setOnClickListener(View.OnClickListener { datePickerDialog.show() })

        tvHintTaskTime.setOnClickListener(View.OnClickListener { timePickerDialog.show() })

    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        populateSetDate(year, month + 1, dayOfMonth)
    }

    override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {
        populateSetTime(hourOfDay, minute)
    }

    fun populateSetTime(hour: Int, minute: Int) {
        if (minute <= 9 || minute == 0)
            tvTaskTime.setText(hour.toString() + ":0" + minute)
        else
            tvTaskTime.setText(hour.toString() + ":" + minute)

    }

    fun populateSetDate(year: Int, month: Int, day: Int) {
        if (day <= 9 && month <= 9) {
            tvTaskDate.setText("0$day/0$month/$year")
        } else if (day <= 9 && month > 9) {
            tvTaskDate.setText("0$day/$month/$year")
        } else if (day > 9 && month <= 9) {
            tvTaskDate.setText(day.toString() + "/" + "0" + month + "/" + year)
        } else {
            tvTaskDate.setText(day.toString() + "/" + month + "/" + year)
        }
    }


}
