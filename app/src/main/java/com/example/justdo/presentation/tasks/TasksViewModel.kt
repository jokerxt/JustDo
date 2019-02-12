package com.example.justdo.presentation.tasks

import android.app.Application
import com.example.justdo.App
import com.example.justdo.Screens
import com.example.justdo.domain.entities.Priority
import com.example.justdo.domain.entities.tasks.TasksExpandableGroup
import com.example.justdo.domain.entities.tasks.TodoTask
import com.example.justdo.domain.interactors.tasks.TasksInteractor
import com.example.justdo.model.data.server.error.ServerError
import com.example.justdo.presentation.BaseViewModel
import com.example.justdo.system.FlowRouter
import com.example.justdo.system.SingleLiveEvent
import org.threeten.bp.LocalDateTime
import org.threeten.bp.format.DateTimeFormatter
import timber.log.Timber
import javax.inject.Inject

class TasksViewModel(application: Application) : BaseViewModel(application) {

    @Inject
    lateinit var router: FlowRouter

    @Inject
    lateinit var tasksInteractor: TasksInteractor

    val taskListLiveData = SingleLiveEvent<List<TasksExpandableGroup>>()

    init {
        App.componentsManager.getFlowComponent().inject(this)
    }

    fun onBackPressed() = router.exit()

    fun onAddTaskClick() {
        router.navigateTo(Screens.AddTask)
    }

    fun loadTasks() {
        tasksInteractor.getTasksList()
            .subscribe(
                {
                    val todoTasksGroup = mutableListOf<TasksExpandableGroup>()
                    val map = HashMap<String, MutableList<TodoTask>>()
                    val dateFormatter = DateTimeFormatter.ofPattern("MMMM d, y")

                    it.forEach { todoTask ->
                        val key = todoTask.dueDate.format(dateFormatter)
                        if(!map.containsKey(key)) {
                            map[key] = mutableListOf()
                        }
                        map[key]?.add(todoTask)
                    }

                    map.forEach {
                        todoTasksGroup.add(TasksExpandableGroup(it.key, it.value))
                    }

                    taskListLiveData.postValue(todoTasksGroup)
                },
                {
                    val code = if (it is ServerError) it.errorCode.toString() else ""
                    Timber.e("$code LOAD_TASKS_LIST error: $it")
                    responseError.postValue(it.message)
                }
            )
            .connect()
    }

    fun createNewTask() {

    }

    fun onChangePasswordClick() {

    }

    fun onSignOutClick() {
        tasksInteractor.signOut()
        router.newRootFlow(Screens.AuthFlow)
    }

}