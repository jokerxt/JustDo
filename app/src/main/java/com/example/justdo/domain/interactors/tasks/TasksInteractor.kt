package com.example.justdo.domain.interactors.tasks

import com.example.justdo.model.repository.tasks.TasksRepository
import javax.inject.Inject

class TasksInteractor @Inject constructor(
    private val tasksRepository: TasksRepository
) {

    fun getTasksList() = tasksRepository.getTodoTasksList()

    fun signOut() = tasksRepository.signOut()

}