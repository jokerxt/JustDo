package com.example.justdo.model.repository

import com.example.justdo.domain.entities.server.RegUserRequest
import com.example.justdo.domain.entities.server.TokenRequest
import com.example.justdo.model.data.server.ServerApi
import com.example.justdo.system.SchedulersProvider
import io.reactivex.Single
import javax.inject.Inject

class AuthRepository @Inject constructor(
    private val api: ServerApi,
    private val schedulers: SchedulersProvider
) {
    fun login(email: String, password: String) = api
        .getToken(TokenRequest(email, password, "android", GRANT_TYPE_PASSWORD))
        .subscribeOn(schedulers.io())
        .observeOn(schedulers.ui())

    fun signup(email: String, password: String) = api
        .registration(RegUserRequest(email, password))
        .subscribeOn(schedulers.io())
        .observeOn(schedulers.io())
        .flatMap { login(email, password) }
        .observeOn(schedulers.ui())

    companion object {
        private const val GRANT_TYPE_PASSWORD = "password"
        private const val GRANT_TYPE_REFRESH_TOKEN = "refresh_token"
    }

}