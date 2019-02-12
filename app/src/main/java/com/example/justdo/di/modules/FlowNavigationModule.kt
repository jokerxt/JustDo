package com.example.justdo.di.modules

import com.example.justdo.di.scopes.FlowScope
import com.example.justdo.system.FlowRouter
import dagger.Module
import dagger.Provides
import ru.terrakok.cicerone.Cicerone
import ru.terrakok.cicerone.Router

@Module
class FlowNavigationModule {

    @FlowScope
    @Provides
    fun provideFlowCicerone(globalRouter: Router): Cicerone<FlowRouter> = Cicerone.create(FlowRouter(globalRouter))

    @FlowScope
    @Provides
    fun provideFlowCiceroneRouter(cicerone: Cicerone<FlowRouter>): FlowRouter = cicerone.router
}