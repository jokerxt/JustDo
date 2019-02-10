package com.example.justdo.di.modules

import com.example.justdo.di.scopes.FlowScope
import com.example.justdo.system.FlowRouter
import dagger.Module
import dagger.Provides
import ru.terrakok.cicerone.Cicerone
import ru.terrakok.cicerone.NavigatorHolder
import ru.terrakok.cicerone.Router
import javax.inject.Named
import javax.inject.Qualifier

@Module
class FlowNavigationModule {

    @FlowScope
    @Provides
    fun provideFlowCicerone(globalRouter: Router): Cicerone<FlowRouter> = Cicerone.create(FlowRouter(globalRouter))

    @FlowScope
    @Provides
    fun provideFlowCiceroneRouter(cicerone: Cicerone<FlowRouter>): FlowRouter = cicerone.router
}