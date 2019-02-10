package com.example.justdo.domain.interactors

import android.content.Context
import javax.inject.Inject

class AppStartInteractor @Inject constructor(
    private val sessionRepository: Context
) {
    fun signInToAccount(): Boolean {
        val account = sessionRepository.applicationContext
//        Toothpick.closeScope(DI.SERVER_SCOPE)
//        Toothpick
//            .openScopes(DI.APP_SCOPE, DI.SERVER_SCOPE)
//            .installModules(ServerModule(account))
        return account == null
    }

}