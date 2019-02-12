package com.example.justdo.presentation

import com.example.justdo.Screens
import com.example.justdo.domain.interactors.AppStartInteractor
import ru.terrakok.cicerone.Router
import javax.inject.Inject

class AppStarter @Inject constructor(
    private val appStartInteractor: AppStartInteractor,
    private val router: Router
) {
    private val isSignedUp = appStartInteractor.signUpToAccount()

    fun start() {
        val rootScreen =
            if (isSignedUp) Screens.TasksFlow
            else Screens.AuthFlow

        router.newRootScreen(rootScreen)
    }
}