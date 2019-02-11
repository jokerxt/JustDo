package com.example.justdo.di.modules

import com.example.justdo.system.AppSchedulers
import com.example.justdo.system.SchedulersProvider
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class SchedulersModule {

    @Singleton
    @Provides
    fun provideSchedulers(): SchedulersProvider = AppSchedulers()

}