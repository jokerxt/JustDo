package com.example.justdo.ui.tasks.list

import android.view.View
import android.view.animation.Animation
import android.view.animation.RotateAnimation
import android.widget.ImageView
import android.widget.TextView
import com.example.justdo.R
import com.example.justdo.domain.interactors.tasks.TasksInteractor
import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup
import com.thoughtbot.expandablerecyclerview.viewholders.GroupViewHolder
import org.threeten.bp.LocalDate
import org.threeten.bp.format.DateTimeFormatter
import java.util.*

class DateGroupViewHolder(private val view: View) : GroupViewHolder(view) {

    private val nowDate = LocalDate.now().toEpochDay()
    private val dateFormatter = DateTimeFormatter.ofPattern(TasksInteractor.HEADER_DATE_PATTERN, Locale.US)
    private val title = view.findViewById(R.id.date) as TextView
    private val arrow = view.findViewById(R.id.arrow) as ImageView

    fun setDate(group: ExpandableGroup<*>) {
        val tasksDate = LocalDate.parse(group.title, dateFormatter).toEpochDay()
        title.text = (if(tasksDate == nowDate) "Today, " else "") + group.title
    }

    override fun expand() {
        animateExpand()
    }

    override fun collapse() {
        animateCollapse()
    }

    private fun animateExpand() {
        val rotate = RotateAnimation(
            360f, 180f,
            Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f
        )
        rotate.duration = 300
        rotate.fillAfter = true
        arrow.animation = rotate
    }

    private fun animateCollapse() {
        val rotate = RotateAnimation(
            180f, 360f,
            Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f
        )
        rotate.duration = 300
        rotate.fillAfter = true
        arrow.animation = rotate
    }
}

