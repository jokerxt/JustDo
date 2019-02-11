package com.example.justdo.domain.interactors.auth

import com.example.justdo.model.repository.AuthRepository
import javax.inject.Inject

class AuthInteractor @Inject constructor(
    private val authRepository: AuthRepository
) {
    fun login(email: String, password: String) = authRepository.login(email, password)

    fun signup(email: String, password: String) = authRepository.signup(email, password)
}