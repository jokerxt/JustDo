package com.example.justdo.domain.entities.server

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class RegUserRequest(
    @Expose @SerializedName("email")
    val email: String,

    @Expose @SerializedName("password")
    val password: String
)