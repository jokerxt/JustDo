package com.example.justdo.domain.entities.server

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class BaseServerInfo(
    @Expose @SerializedName("result")
    var result: Any? = null,

    @Expose @SerializedName("success")
    var success: Boolean? = null,

    @Expose @SerializedName("error")
    var error: String? = null,

    @Expose @SerializedName("errorCode")
    var errorCode: Int? = null
)