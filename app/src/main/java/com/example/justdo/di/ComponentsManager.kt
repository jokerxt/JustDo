package com.example.justdo.di

import android.content.Context
import com.example.justdo.di.components.AppComponent
import com.example.justdo.di.components.DaggerAppComponent
import com.example.justdo.di.components.FlowComponent
import com.example.justdo.di.modules.AppModule
import com.example.justdo.di.modules.FlowModule


class ComponentsManager(private val context: Context) {

    private var appComponent: AppComponent? = null
    private var flowComponent: FlowComponent? = null

    fun getAppComponent(): AppComponent {
        if (appComponent == null) {
            appComponent = DaggerAppComponent.builder()
                .appModule(AppModule(context))
                .build()
        }
        return appComponent!!
    }

    fun getFlowComponent(): FlowComponent {
        if (flowComponent == null) {
            flowComponent = appComponent?.plusAuthComponent(FlowModule())
        }
        return flowComponent!!
    }

    fun clearFlowComponent() {
        flowComponent = null
    }
}