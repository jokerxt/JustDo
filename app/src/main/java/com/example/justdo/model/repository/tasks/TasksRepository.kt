package com.example.justdo.model.repository.tasks

import com.example.justdo.domain.entities.server.RefreshTokenRequest
import com.example.justdo.model.data.repository.TokenInvalidError
import com.example.justdo.model.data.server.ServerApi
import com.example.justdo.model.data.server.error.AuthorizationError
import com.example.justdo.model.data.storage.GlobalPreference
import com.example.justdo.system.SchedulersProvider
import javax.inject.Inject

class TasksRepository @Inject constructor(
    private val api: ServerApi,
    private val globalPreference: GlobalPreference,
    private val schedulers: SchedulersProvider
) {

    fun refreshToken() = api
        .refreshToken(
            RefreshTokenRequest(
                globalPreference.refreshToken ?: throw TokenInvalidError(),
                "android",
                GRANT_TYPE_REFRESH_TOKEN
            )
        )
        .subscribeOn(schedulers.io())
        .observeOn(schedulers.ui())
        .doOnSuccess {
            globalPreference.tokenInfo = it
        }

    fun getTodoTasksList() = api
        .getTodoTasksList()
        .subscribeOn(schedulers.io())
        .retryWhen {
            it.flatMap { error ->
                if (error is AuthorizationError) {
                    return@flatMap refreshToken().toFlowable()
                }
                throw error
            }
        }
        .observeOn(schedulers.computation())

    fun signOut() {
        globalPreference.token = null
        globalPreference.refreshToken = null
    }

    companion object {
        private const val GRANT_TYPE_REFRESH_TOKEN = "refresh_token"
    }
}