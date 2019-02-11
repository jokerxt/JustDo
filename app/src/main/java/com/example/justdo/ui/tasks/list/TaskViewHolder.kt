package com.example.justdo.ui.tasks.list

import android.view.View
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import com.example.justdo.R
import com.example.justdo.domain.entities.tasks.TodoTask
import com.example.justdo.ui.tasks.common.OnItemClickListener
import com.thoughtbot.expandablerecyclerview.viewholders.ChildViewHolder
import org.threeten.bp.format.DateTimeFormatter

class TaskViewHolder(private val view: View) : ChildViewHolder(view), View.OnClickListener {

    var itemClickListener: OnItemClickListener? = null

    private val name = view.findViewById(R.id.name) as TextView
    private val cardView = view.findViewById(R.id.cardView) as CardView
    private val desc = view.findViewById(R.id.desc) as TextView
    private val time = view.findViewById(R.id.time) as TextView
    private val checkBox = view.findViewById(R.id.checkBox) as CheckBox
    private val priorityIcon = view.findViewById(R.id.priorityIcon) as ImageView

    fun bind(todoTask: TodoTask) {
        name.text = todoTask.name
        desc.text = todoTask.desc
        time.text = todoTask.dueDate.format(DateTimeFormatter.ofPattern("HH:mm"))
        priorityIcon.setColorFilter(todoTask.priority.color)
        cardView.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        checkBox.toggle()
        itemClickListener?.onItemClick(v, adapterPosition)
    }

}