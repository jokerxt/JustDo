package com.example.justdo.domain.interactors.tasks

import com.example.justdo.domain.entities.tasks.TodoTask
import com.example.justdo.model.data.tasks.TasksExpandableGroup
import com.example.justdo.model.repository.tasks.TasksRepository
import org.threeten.bp.ZoneOffset
import org.threeten.bp.format.DateTimeFormatter
import java.util.*
import javax.inject.Inject

class TasksInteractor @Inject constructor(
    private val tasksRepository: TasksRepository
) {

    fun getTasksList() = tasksRepository
        .getTodoTasksList()
        .map { transformToExpandableGroup(it) }

    fun addNewTask(todoTask: TodoTask) = tasksRepository
        .addNewTodoTask(todoTask)
        .map { transformToExpandableGroup(it.toTypedArray()) }

    fun signOut() = tasksRepository.signOut()

    private fun transformToExpandableGroup(todoTasksArray: Array<TodoTask>) = todoTasksArray.run {
        val todoTasksGroup = mutableListOf<TasksExpandableGroup>()
        val map = HashMap<String, MutableList<TodoTask>>()
        val dateFormatter = DateTimeFormatter.ofPattern(HEADER_DATE_PATTERN, Locale.US)

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

        todoTasksGroup.sortBy {
            it.items.first().dueDate.toEpochSecond(ZoneOffset.UTC)
        }

        todoTasksGroup
    }

    companion object {
        const val HEADER_DATE_PATTERN = "MMMM d, y"
    }

}