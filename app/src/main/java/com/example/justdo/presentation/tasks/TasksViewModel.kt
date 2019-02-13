package com.example.justdo.presentation.tasks

import android.app.Application
import android.content.pm.ApplicationInfo
import android.os.Handler
import com.example.justdo.App
import com.example.justdo.Screens
import com.example.justdo.domain.entities.Priority
import com.example.justdo.domain.entities.tasks.TodoTask
import com.example.justdo.domain.interactors.tasks.TasksInteractor
import com.example.justdo.model.data.server.error.ServerError
import com.example.justdo.model.data.tasks.TasksExpandableGroup
import com.example.justdo.presentation.BaseViewModel
import com.example.justdo.system.FlowRouter
import com.example.justdo.system.SingleLiveEvent
import org.threeten.bp.LocalDateTime
import timber.log.Timber
import javax.inject.Inject
import kotlin.random.Random

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
        val isDebug = ((getApplication<App>().applicationInfo.flags and ApplicationInfo.FLAG_DEBUGGABLE) != 0)

        if (isDebug) {
            tasksInteractor.getTasksList()
                .subscribe(
                    {
                        taskListLiveData.postValue(it)
                    },
                    {
                        val code = if (it is ServerError) it.errorCode.toString() else ""
                        Timber.e("$code LOAD_TASKS_LIST error: $it")
                        responseError.postValue(it.message)
                    }
                )
                .connect()
        } else {
            val handler = Handler()
            Thread(Runnable {
                Thread.sleep(1000)
                handler.post {
                    if (Random.nextBoolean()) {
                        taskListLiveData.postValue(mutableListOf())
                    } else {
                        if (Random.nextBoolean()) {
                            responseError.postValue("Timeout!")
                        } else {
                            responseError.postValue("Server error - try again later")
                        }
                    }
                }
            }).start()
        }
    }

    fun createNewTask(name: String, desc: String, priority: Priority, dueDate: LocalDateTime) {
        val task = TodoTask(0, name, desc, priority, dueDate)

        tasksInteractor.addNewTask(task).subscribe(
            {
                taskListLiveData.postValue(it)
            },
            { }
        )
            .connect()
    }

    fun onChangePasswordClick() {

    }

    fun onSignOutClick() {
        tasksInteractor.signOut()
        router.newRootFlow(Screens.AuthFlow)
    }

}