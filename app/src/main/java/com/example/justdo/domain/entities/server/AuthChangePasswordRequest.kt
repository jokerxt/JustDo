package com.example.justdo.domain.entities.server

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class AuthChangePasswordRequest(
    @Expose @SerializedName("email")
    var email: String,

    @Expose @SerializedName("password")
    var password: String,

    @Expose @SerializedName("new_password")
    var newPassword: String
)