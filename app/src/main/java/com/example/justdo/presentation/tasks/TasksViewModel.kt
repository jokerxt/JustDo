package com.example.justdo.presentation.tasks

import androidx.lifecycle.ViewModel
import com.example.justdo.App
import com.example.justdo.system.FlowRouter
import javax.inject.Inject

class TasksViewModel : ViewModel() {

    @Inject
    lateinit var router: FlowRouter

    init {
        App.componentsManager.getFlowComponent().inject(this)
    }

    fun onBackPressed() = router.exit()




}