package com.example.justdo.ui.tasks.list

import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.justdo.R
import com.example.justdo.domain.entities.Priority
import com.example.justdo.domain.entities.tasks.TasksExpandableGroup
import com.example.justdo.domain.entities.tasks.TodoTask
import com.example.justdo.presentation.tasks.TasksViewModel
import com.example.justdo.ui.common.BaseFragment
import kotlinx.android.synthetic.main.fragment_tasks_list.*
import org.threeten.bp.LocalDateTime
import org.threeten.bp.format.DateTimeFormatter


class TasksListFragment : BaseFragment() {

    override val layoutRes = R.layout.fragment_tasks_list

    private val viewModel: TasksViewModel? by lazy {
        parentFragment?.let { ViewModelProviders.of(it).get(TasksViewModel::class.java) }
    }

    override fun onBackPressed() {
        viewModel?.onBackPressed()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val items = mutableListOf<TasksExpandableGroup>()

        val task = TodoTask(
            0, "Купить жвачку",
            "Тут много текста про жизнь и вообще", Priority.MEDIUM, LocalDateTime.parse("2019-12-03T12:40:00")
        )

        val task2 = TodoTask(
            1, "Сходить в магазин",
            "Пошли в магазин а?????", Priority.HIGH, LocalDateTime.parse("2019-12-03T08:25:00")
        )

        items.add(
            TasksExpandableGroup(
                task.dueDate.format(DateTimeFormatter.ofPattern("MMMM d, y")),
                listOf(task, task2)
            )
        )

        val task3 = TodoTask(
            2, "Завершить макет",
            "Доделать футер, исправить текст во втором", Priority.LOW, LocalDateTime.parse("2019-03-05T11:55:00")
        )

        items.add(
            TasksExpandableGroup(
                task3.dueDate.format(DateTimeFormatter.ofPattern("MMMM d, y")),
                listOf(task3)
            )
        )

        recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)

            addItemDecoration(
                DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
            )

            adapter = TasksAdapter(items).apply {
                itemClickListener = object : OnItemClickListener {
                    override fun onItemClick(view: View, globalPos: Int) {
                        //viewModel?.
                    }
                }


                val callback = SwipeController()
                val itemTouchHelper = ItemTouchHelper(callback)
                itemTouchHelper.attachToRecyclerView(recyclerView)

            }
        }
    }

}