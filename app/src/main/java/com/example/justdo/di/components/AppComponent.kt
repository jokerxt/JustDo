package com.example.justdo.di.components

import com.example.justdo.di.modules.AppModule
import com.example.justdo.di.modules.FlowModule
import com.example.justdo.di.modules.NavigationModule
import com.example.justdo.ui.AppActivity
import dagger.Component
import javax.inject.Named
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, NavigationModule::class])
interface AppComponent {

    fun plusAuthComponent(module: FlowModule): FlowComponent

    fun inject(activity: AppActivity)

}