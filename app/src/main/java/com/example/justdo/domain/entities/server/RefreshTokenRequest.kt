package com.example.justdo.domain.entities.server

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class RefreshTokenRequest(
    @Expose @SerializedName("refresh_token")
    val refreshToken: String,

    @Expose @SerializedName("client_id")
    val clientId: String,

    @Expose @SerializedName("grant_type")
    val grantType: String
)