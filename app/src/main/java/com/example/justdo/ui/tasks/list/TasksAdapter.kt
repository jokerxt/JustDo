package com.example.justdo.ui.tasks.list

import android.view.View
import android.view.ViewGroup
import com.example.justdo.R
import com.example.justdo.domain.entities.tasks.TasksExpandableGroup
import com.example.justdo.domain.entities.tasks.TodoTask
import com.example.justdo.extension.inflate
import com.example.justdo.ui.tasks.common.OnItemClickListener
import com.thoughtbot.expandablerecyclerview.ExpandableRecyclerViewAdapter
import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup


class TasksAdapter(groups: List<TasksExpandableGroup>) :
    ExpandableRecyclerViewAdapter<DateGroupViewHolder, TaskViewHolder>(groups),
    OnItemClickListener {

    var itemClickListener: OnItemClickListener? = null

    override fun onCreateGroupViewHolder(parent: ViewGroup, viewType: Int) =
        DateGroupViewHolder(parent.inflate(R.layout.list_item_date_group))

    override fun onCreateChildViewHolder(parent: ViewGroup, viewType: Int) =
        TaskViewHolder(parent.inflate(R.layout.list_item_task)).apply { itemClickListener = this@TasksAdapter }

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
        holder.setDate(group)
    }

    override fun onItemClick(view: View, globalPos: Int) {
        itemClickListener?.onItemClick(view, globalPos)
    }

}