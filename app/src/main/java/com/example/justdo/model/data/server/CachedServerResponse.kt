package com.example.justdo.model.data.server

import com.example.justdo.domain.entities.tasks.TodoTask
import com.example.justdo.model.data.db.TodoTasksDatabase
import com.example.justdo.model.mapper.TodoTaskMapper
import io.reactivex.Single

class CachedServerResponse(
    private val serverApi: ServerApi,
    private val database: TodoTasksDatabase,
    private val todoTaskMapper: TodoTaskMapper
) : ServerApi by serverApi {

    override fun getTodoTasksList(): Single<Array<TodoTask>> =
        Single.defer {
            val cachedTodoTaskList = database.todoTasks().allTodoTasks
            if (cachedTodoTaskList.isNullOrEmpty()) {
                serverApi.getTodoTasksList()
                    .doOnSuccess {
                        database.todoTasks().putTodoTasks(it.map { todoTaskMapper.entityToDb(it) })
                    }
            } else {
                Single.just(cachedTodoTaskList.map { todoTaskMapper.dbToEntity(it) }.toTypedArray())
            }
        }

}