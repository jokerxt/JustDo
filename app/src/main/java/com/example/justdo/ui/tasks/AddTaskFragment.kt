package com.example.justdo.ui.tasks

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import androidx.lifecycle.ViewModelProviders
import com.example.justdo.R
import com.example.justdo.domain.entities.Priority
import com.example.justdo.extension.expandTouchArea
import com.example.justdo.model.data.tasks.SelectedPriority
import com.example.justdo.presentation.tasks.TasksViewModel
import com.example.justdo.ui.common.BaseFragment
import com.example.justdo.ui.dialogs.ChoiceDialogFragment
import com.example.justdo.ui.dialogs.OnDialogClickListener
import com.example.justdo.ui.tasks.add.PriorityAdapter
import kotlinx.android.synthetic.main.fragment_add_task.*
import org.threeten.bp.LocalDate
import org.threeten.bp.LocalDateTime
import org.threeten.bp.LocalTime
import org.threeten.bp.format.DateTimeFormatter


class AddTaskFragment : BaseFragment(), OnDialogClickListener {

    override val layoutRes = R.layout.fragment_add_task

    private val viewModel: TasksViewModel? by lazy {
        parentFragment?.let { ViewModelProviders.of(it).get(TasksViewModel::class.java) }
    }

    private var todoTaskDateTime = LocalDateTime.now()

    override fun onBackPressed() {
        viewModel?.onBackPressed()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        selectPriorityRecyclerView.apply {
            setHasFixedSize(true)
            adapter = PriorityAdapter(Priority.values().map { SelectedPriority(it) }.toMutableList())
        }

        addTaskArrowBack.apply {
            expandTouchArea(15f)
            setOnClickListener { onBackPressed() }
        }

        cancelAddTask.setOnClickListener {
            ChoiceDialogFragment.create(getString(R.string.q_save_changes))
                .show(childFragmentManager, null)
        }

        okAddTask.setOnClickListener { dialogPositiveClicked(null) }

        date.apply {
            val formatter = DateTimeFormatter.ofPattern(TODO_TASK_DATE_PATTERN)
            text = todoTaskDateTime.format(formatter)

            setOnClickListener {
                val currentDate = LocalDate.now()
                DatePickerDialog(context, { _, year, month, dayOfMonth ->
                    todoTaskDateTime = LocalDateTime.of(
                        year,
                        month,
                        dayOfMonth,
                        todoTaskDateTime.hour,
                        todoTaskDateTime.minute
                    )
                    text = todoTaskDateTime.format(formatter)
                }, currentDate.year, currentDate.monthValue, currentDate.dayOfMonth).show()
            }
        }

        time.apply {
            val formatter = DateTimeFormatter.ofPattern(TODO_TASK_TIME_PATTERN)
            text = todoTaskDateTime.format(formatter)

            setOnClickListener {
                val currentTime = LocalTime.now()
                TimePickerDialog(context, { _, hour, minute ->
                    todoTaskDateTime = LocalDateTime.of(
                        todoTaskDateTime.year,
                        todoTaskDateTime.month,
                        todoTaskDateTime.dayOfMonth,
                        hour,
                        minute
                    )
                    text = todoTaskDateTime.format(formatter)
                }, currentTime.hour, currentTime.minute, false).show()
            }
        }
    }

    override fun dialogPositiveClicked(tag: String?) {
        val adapter = (selectPriorityRecyclerView.adapter as PriorityAdapter)

        viewModel?.createNewTask(
            titleTask.text.toString(),
            descriptionTask.text.toString(),
            adapter.getSelectedItem(),
            todoTaskDateTime
        )
        viewModel?.onBackPressed()
    }

    override fun dialogNegativeClicked(tag: String?) {
        viewModel?.onBackPressed()
    }

    companion object {
        private const val TODO_TASK_TIME_PATTERN = "h:mm a"
        private const val TODO_TASK_DATE_PATTERN = "MMMM d, y"
    }

}