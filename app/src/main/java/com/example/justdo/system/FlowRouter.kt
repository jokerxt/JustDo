package com.example.justdo.system

import com.example.justdo.App
import ru.terrakok.cicerone.Router
import ru.terrakok.cicerone.android.support.SupportAppScreen

class FlowRouter(private val appRouter: Router) : Router() {

    fun startFlow(screen: SupportAppScreen) {
        appRouter.navigateTo(screen)
    }

    fun newRootFlow(screen: SupportAppScreen) {
        App.componentsManager.clearFlowComponent()
        appRouter.newRootScreen(screen)
    }

    fun finishFlow() {
        appRouter.exit()
    }
}