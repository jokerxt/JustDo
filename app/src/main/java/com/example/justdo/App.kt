package com.example.justdo

import android.annotation.SuppressLint
import android.app.Application
import com.example.justdo.di.ComponentsManager
import com.jakewharton.threetenabp.AndroidThreeTen
import timber.log.Timber


class App : Application() {

    companion object {

        @SuppressLint("StaticFieldLeak")
        lateinit var componentsManager: ComponentsManager
            private set

    }

    override fun onCreate() {
        super.onCreate()

        initComponentsTree()
        initAppComponent()

        initFabric()
        initTimber()
        initThreeTenABP()
    }

    private fun initComponentsTree() {
        componentsManager = ComponentsManager(this.applicationContext)
    }

    private fun initAppComponent() {
        componentsManager.getAppComponent()
    }

    private fun initTimber() {
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }

    private fun initThreeTenABP() {
        AndroidThreeTen.init(this)
    }

    private fun initFabric() {
        if(!BuildConfig.DEBUG) {
            //
        }
    }
}