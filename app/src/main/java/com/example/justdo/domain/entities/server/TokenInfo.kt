package com.example.justdo.domain.entities.server

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class TokenInfo(
    @Expose @SerializedName("client_id")
    var clientId: String? = null,

    @Expose @SerializedName("access_token")
    var accessToken: String?= null,

    @Expose @SerializedName("refresh_token")
    var refreshToken: String? = null
)