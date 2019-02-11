package com.example.justdo.ui.tasks

import android.os.Bundle
import androidx.lifecycle.ViewModelProviders
import com.example.justdo.R
import com.example.justdo.domain.entities.Priority
import com.example.justdo.domain.entities.SelectedPriority
import com.example.justdo.extension.expandTouchArea
import com.example.justdo.presentation.tasks.TasksViewModel
import com.example.justdo.ui.common.BaseFragment
import com.example.justdo.ui.common.dialogs.ChoiceDialogFragment
import com.example.justdo.ui.common.dialogs.OnDialogClickListener
import com.example.justdo.ui.tasks.add.PriorityAdapter
import kotlinx.android.synthetic.main.fragment_add_task.*

class AddTaskFragment : BaseFragment(), OnDialogClickListener {

    override val layoutRes = R.layout.fragment_add_task

    private val viewModel: TasksViewModel? by lazy {
        parentFragment?.let { ViewModelProviders.of(it).get(TasksViewModel::class.java) }
    }

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
            ChoiceDialogFragment.create("Do you want to save changes?")
                .show(childFragmentManager, null)
        }

        okAddTask.setOnClickListener { dialogPositiveClicked(null) }
    }

    override fun dialogPositiveClicked(tag: String?) {
        viewModel?.createNewTask()
        viewModel?.onBackPressed()
    }

    override fun dialogNegativeClicked(tag: String?) {
        viewModel?.onBackPressed()
    }

}