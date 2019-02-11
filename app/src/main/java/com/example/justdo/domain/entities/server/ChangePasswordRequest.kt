package com.example.justdo.domain.entities.server

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class ChangePasswordRequest(
    @Expose @SerializedName("code")
    var code: String,

    @Expose @SerializedName("password")
    var password: String
)