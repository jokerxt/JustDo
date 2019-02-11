package com.example.justdo.domain.entities.server

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class TokenRequest(
    @Expose @SerializedName("email")
    val email: String,

    @Expose @SerializedName("password")
    val password: String,

    @Expose @SerializedName("client_id")
    val clientId: String,

    @Expose @SerializedName("grant_type")
    val grantType: String
)