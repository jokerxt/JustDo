package com.example.justdo.ui.tasks.add

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.justdo.R
import com.example.justdo.domain.entities.Priority
import com.example.justdo.extension.inflate
import com.example.justdo.model.data.tasks.SelectedPriority
import com.example.justdo.ui.tasks.common.OnItemClickListener

class PriorityAdapter(val items: MutableList<SelectedPriority>) :
    RecyclerView.Adapter<PriorityViewHolder>(), OnItemClickListener {

    var itemClickListener: OnItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        PriorityViewHolder(parent.inflate(R.layout.item_add_task_priority))
            .apply { itemClickListener = this@PriorityAdapter }

    override fun getItemCount() = items.size + 1

    override fun onBindViewHolder(holder: PriorityViewHolder, position: Int) {
        holder.bind(if (position < (itemCount - 1)) items[position] else null)
    }

    fun getSelectedItem(): Priority {
        items.forEach {
            if (it.isSelected)
                return it.priority
        }
        return Priority.NO
    }

    override fun onItemClick(view: View, globalPos: Int) {
        notifyDataSetChanged()
        items.forEachIndexed { index, priority ->
            priority.isSelected = index == globalPos
        }
        itemClickListener?.onItemClick(view, globalPos)
    }

}