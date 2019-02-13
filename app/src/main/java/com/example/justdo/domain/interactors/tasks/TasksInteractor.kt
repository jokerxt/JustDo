package com.example.justdo.domain.interactors.tasks

import com.example.justdo.domain.entities.tasks.TasksExpandableGroup
import com.example.justdo.domain.entities.tasks.TodoTask
import com.example.justdo.model.repository.tasks.TasksRepository
import org.threeten.bp.format.DateTimeFormatter
import javax.inject.Inject

class TasksInteractor @Inject constructor(
    private val tasksRepository: TasksRepository
) {

    fun getTasksList() = tasksRepository.getTodoTasksList().map { todoTasksArray ->
        val todoTasksGroup = mutableListOf<TasksExpandableGroup>()
        val map = HashMap<String, MutableList<TodoTask>>()
        val dateFormatter = DateTimeFormatter.ofPattern("MMMM d, y")

        todoTasksArray.forEach { todoTask ->
            val key = todoTask.dueDate.format(dateFormatter)
            if (!map.containsKey(key)) {
                map[key] = mutableListOf()
            }
            map[key]?.add(todoTask)
        }

        map.forEach {
            todoTasksGroup.add(TasksExpandableGroup(it.key, it.value))
        }

        todoTasksGroup
    }

    fun signOut() = tasksRepository.signOut()

}