package com.example.justdo.domain.entities.server

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class ForgotPasswordRequest(
    @Expose @SerializedName("email")
    var email: String
)