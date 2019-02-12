package com.example.justdo.model.mapper

import com.example.justdo.domain.entities.tasks.TodoTask
import com.example.justdo.model.data.db.DbTodoTask
import javax.inject.Inject

class TodoTaskMapper @Inject constructor() {

    fun dbToEntity(dbTask: DbTodoTask) = TodoTask(
        dbTask.id,
        dbTask.name,
        dbTask.desc,
        dbTask.priority,
        dbTask.dueDate
    )

    fun entityToDb(task: TodoTask) = DbTodoTask(
        task.id,
        task.name,
        task.desc,
        task.priority,
        task.dueDate
    )

}