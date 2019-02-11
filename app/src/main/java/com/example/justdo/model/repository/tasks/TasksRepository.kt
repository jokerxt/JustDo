package com.example.justdo.model.repository.tasks

import com.example.justdo.model.data.server.ServerApi
import com.example.justdo.system.SchedulersProvider
import javax.inject.Inject

class TasksRepository @Inject constructor(
    private val api: ServerApi,
    private val schedulers: SchedulersProvider
) {

    fun getTodoTasksList() = api
        .getTodoTasksList("")
        .subscribeOn(schedulers.io())
        .observeOn(schedulers.ui())

}