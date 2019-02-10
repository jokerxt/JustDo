package com.example.justdo.di.components

import com.example.justdo.di.modules.FlowModule
import com.example.justdo.di.modules.FlowNavigationModule
import com.example.justdo.di.scopes.FlowScope
import com.example.justdo.presentation.auth.AuthViewModel
import com.example.justdo.presentation.tasks.TasksViewModel
import com.example.justdo.ui.common.FlowFragment
import dagger.Subcomponent

@FlowScope
@Subcomponent(modules = [FlowModule::class, FlowNavigationModule::class])
interface FlowComponent {

    fun inject(flowFragment: FlowFragment)
    fun inject(authViewModel: AuthViewModel)
    fun inject(tasksViewModel: TasksViewModel)

}