package com.example.justdo.ui.tasks

import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.example.justdo.R
import com.example.justdo.domain.entities.tasks.TasksExpandableGroup
import com.example.justdo.domain.entities.tasks.TodoTask
import com.example.justdo.extension.inflate
import com.thoughtbot.expandablerecyclerview.ExpandableRecyclerViewAdapter
import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup
import com.thoughtbot.expandablerecyclerview.viewholders.ChildViewHolder
import com.thoughtbot.expandablerecyclerview.viewholders.GroupViewHolder
import org.threeten.bp.format.DateTimeFormatter


class TasksAdapter(groups: List<TasksExpandableGroup>) :
    ExpandableRecyclerViewAdapter<TasksAdapter.DateGroupViewHolder, TasksAdapter.TaskViewHolder>(groups) {

    override fun onCreateGroupViewHolder(parent: ViewGroup, viewType: Int) =
        DateGroupViewHolder(parent.inflate(R.layout.list_item_date_group))

    override fun onCreateChildViewHolder(parent: ViewGroup, viewType: Int) =
        TaskViewHolder(parent.inflate(R.layout.list_item_task))

    override fun onBindChildViewHolder(
        holder: TaskViewHolder,
        flatPosition: Int,
        group: ExpandableGroup<*>,
        childIndex: Int
    ) {
        val todoTask = group.items[childIndex] as TodoTask
        holder.bind(todoTask)
    }

    override fun onBindGroupViewHolder(holder: DateGroupViewHolder, flatPosition: Int, group: ExpandableGroup<*>) {
        holder.setGenreTitle(group)
    }


    inner class DateGroupViewHolder(private val view: View) : GroupViewHolder(view) {

        private val title = view.findViewById(R.id.date) as TextView

        fun setGenreTitle(group: ExpandableGroup<*>) {
            title.text = group.title
        }

    }

    inner class TaskViewHolder(private val view: View) : ChildViewHolder(view) {

        private val name = view.findViewById(R.id.name) as TextView
        private val desc = view.findViewById(R.id.desc) as TextView
        private val time = view.findViewById(R.id.time) as TextView
        private val priorityIcon = view.findViewById(R.id.priorityIcon) as ImageView

        fun bind(todoTask: TodoTask) {
            name.text = todoTask.name
            name.text = todoTask.desc
            name.text = todoTask.dueDate.format(DateTimeFormatter.ofPattern("HH:mm"))
        }

    }

}