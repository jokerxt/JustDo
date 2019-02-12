package com.example.justdo.di.components

import com.example.justdo.di.modules.*
import com.example.justdo.ui.AppActivity
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AppModule::class,
        NavigationModule::class,
        NetworkModule::class,
        SchedulersModule::class,
        DatabaseModule::class
    ]
)
interface AppComponent {

    fun plusAuthComponent(module: FlowModule): FlowComponent

    fun inject(activity: AppActivity)

}