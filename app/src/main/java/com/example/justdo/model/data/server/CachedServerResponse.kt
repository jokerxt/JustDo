package com.example.justdo.model.data.server

import com.example.justdo.domain.entities.tasks.TodoTask
import com.example.justdo.model.data.TodoMapCache
import io.reactivex.Single

class CachedServerResponse(
    private val serverApi: ServerApi,
    private val todoMapCache: TodoMapCache
) : ServerApi by serverApi {

    override fun getTodoTasksList(typedToken: String): Single<Array<TodoTask>> =
        Single.defer {
            val cachedTodoTaskList = todoMapCache.getList()
            if (cachedTodoTaskList == null) {
                serverApi.getTodoTasksList(typedToken)
                    .doOnSuccess { todoMapCache.put(it.toList()) }
            } else {
                Single.just(cachedTodoTaskList.toTypedArray())
            }
        }

}