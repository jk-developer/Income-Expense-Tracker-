package com.example.jitendrakumar.incometracker.adapters

import android.app.AlertDialog
import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.jitendrakumar.incometracker.R
import com.example.jitendrakumar.incometracker.models.Todo
import kotlinx.android.synthetic.main.single_income_item.view.*
import kotlinx.android.synthetic.main.single_task_item.view.*

class MyTodoAdapter (
        val tasks: ArrayList<Todo>,
        val onTaskUpdate: (todo: Todo) -> Unit,
        val onTaskDelete: (todo: Todo) -> Unit
): RecyclerView.Adapter<MyTodoAdapter.TodoViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder {
        val li = parent.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val itemView = li.inflate(R.layout.single_task_item, parent, false)
        return TodoViewHolder(itemView)
    }

    override fun getItemCount(): Int{
        if(tasks==null)
            return 0
        else
            return tasks.size
    }

    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
        holder.itemView.todocheckBox.setOnCheckedChangeListener(null)
        holder.itemView.todocheckBox.isChecked = tasks[position].done
        holder.itemView.tvTaskName.text = tasks[position].taskName
        holder.itemView.tvTaskDate.text = tasks[position].taskDate
        holder.itemView.tvTaskTime.text = tasks[position].taskTime

        holder.itemView.todocheckBox.setOnCheckedChangeListener {
            _, isChecked ->
            tasks[position].done = isChecked
            onTaskUpdate(tasks[position])
        }
        holder.itemView.setOnLongClickListener {
            AlertDialog.Builder(holder.itemView.context)
                    .setTitle("Delete Task")
                    .setMessage("Do you really want to delete this task ? ")
                    .setPositiveButton(
                            "YES",
                            { _, _ -> onTaskDelete(tasks[position]) }
                    )
                    .setNegativeButton("NO", {_, _ -> Unit})
                    .show()
            true
        }
    }

    class TodoViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView) {

    }
}