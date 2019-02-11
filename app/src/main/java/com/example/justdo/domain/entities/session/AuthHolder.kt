package com.example.justdo.domain.entities.session

data class AuthHolder(
    val token: String?,
    val isOAuth: Boolean
)