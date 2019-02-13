package com.example.justdo.ui.tasks.list

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.justdo.R
import com.example.justdo.extension.hide
import com.example.justdo.extension.show
import com.example.justdo.model.data.tasks.TasksExpandableGroup
import com.example.justdo.model.data.tasks.TasksListMenu
import com.example.justdo.presentation.tasks.TasksViewModel
import com.example.justdo.ui.AppActivity
import com.example.justdo.ui.common.BaseFragment
import com.example.justdo.ui.dialogs.MenuDialogFragment
import com.example.justdo.ui.dialogs.OnDialogClickListener
import com.example.justdo.ui.tasks.common.OnItemClickListener
import kotlinx.android.synthetic.main.fragment_tasks_list.*


class TasksListFragment : BaseFragment(), OnDialogClickListener {

    override val layoutRes = R.layout.fragment_tasks_list

    private val viewModel: TasksViewModel? by lazy {
        parentFragment?.let { ViewModelProviders.of(it).get(TasksViewModel::class.java) }
    }

    private var isFirstStart = false

    override fun onBackPressed() {
        viewModel?.onBackPressed()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupActionHandlers()
        viewModel?.loadTasks()
        isFirstStart = true
    }

    private fun setupActionHandlers() {
        viewModel?.taskListLiveData?.observe(this, Observer { todoTasksList ->
            handleViewState(todoTasksList)
        })

        viewModel?.responseError?.observe(this, Observer {
            loadTodoTasksProgress.hide(true)
            refreshListButton.show()
            (activity as? AppActivity?)?.showErrorMessage(it)
        })
    }

    private fun handleViewState(todoTasksList: List<TasksExpandableGroup>?) {
        loadTodoTasksProgress.hide(true)
        refreshListButton.hide(true)
        emptyListLabel.hide(true)

        if (todoTasksList.isNullOrEmpty()) {
            emptyListLabel.show()
        } else {
            recyclerView.apply {
                setHasFixedSize(true)
                adapter = TasksAdapter(todoTasksList).apply {
                    itemClickListener = object : OnItemClickListener {
                        override fun onItemClick(view: View, globalPos: Int) {
                            //open task
                            //viewModel?.
                        }
                    }
                }
                show()
            }
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        if(!isFirstStart) {
            if(viewModel?.taskListLiveData?.value != null) {
                handleViewState(viewModel?.taskListLiveData?.value)
            }
            else {
                if (viewModel?.responseError?.value == null) {
                    handleViewState(null)
                } else {
                    loadTodoTasksProgress.hide(true)
                    refreshListButton.show()
                }
            }
        }

        isFirstStart = false

        refreshListButton.setOnClickListener {
            loadTodoTasksProgress.show()
            refreshListButton.hide(true)
            viewModel?.loadTasks()
        }

        toDoMenuIcon.setOnClickListener {
            MenuDialogFragment.create(TasksListMenu.values().map { it.textResId })
                .show(childFragmentManager, null)
        }

        addTaskButton.setOnClickListener { viewModel?.onAddTaskClick() }

        toDoFilterIcon.setOnClickListener { }
    }

    override fun dialogItemClicked(tag: String?, position: Int) {
        when (position) {
            TasksListMenu.ITEM_CHANGE_PASSWORD.ordinal -> viewModel?.onChangePasswordClick()
            TasksListMenu.ITEM_SIGN_OUT.ordinal -> viewModel?.onSignOutClick()
        }
    }

}