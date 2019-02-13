package com.example.justdo.ui.tasks.add

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.justdo.R
import com.example.justdo.extension.hide
import com.example.justdo.extension.show
import com.example.justdo.model.data.tasks.SelectedPriority
import com.example.justdo.ui.tasks.common.OnItemClickListener

class PriorityViewHolder(val view: View) : RecyclerView.ViewHolder(view), View.OnClickListener {

    private var selectedPriority: SelectedPriority? = null
    var itemClickListener: OnItemClickListener? = null

    private val cardView = view.findViewById(R.id.cardView) as CardView
    private val priorityIcon = view.findViewById(R.id.priorityIcon) as ImageView
    private val priorityName = view.findViewById(R.id.priorityName) as TextView
    private val checkedIcon = view.findViewById(R.id.checkedIcon) as ImageView
    private val addNewIcon = view.findViewById(R.id.addNewIcon) as ImageView

    fun bind(selectedPriority: SelectedPriority?) {
        this.selectedPriority = selectedPriority
        selectedPriority?.apply {
            priorityIcon.setColorFilter(priority.color)
            priorityName.text = priority.type
            if (isSelected)
                checkedIcon.show()
            else
                checkedIcon.hide(true)
        }

        cardView.setOnClickListener(this@PriorityViewHolder)

        if (selectedPriority == null) {
            priorityIcon.hide(true)
            priorityName.hide(true)
            checkedIcon.hide(true)
            addNewIcon.show()
        }
    }

    override fun onClick(v: View) {
        selectedPriority?.apply { isSelected = true }
        itemClickListener?.onItemClick(v, adapterPosition)
    }
}