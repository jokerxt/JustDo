package com.example.justdo.domain.entities

import android.graphics.Color
import com.google.gson.annotations.SerializedName
import java.io.Serializable

enum class Priority(val type: String, val color: Int) : Serializable {

    @SerializedName("high")
    HIGH("High", Color.parseColor("#DD4A52")),

    @SerializedName("medium")
    MEDIUM("Medium", Color.parseColor("#FCD3A0")),

    @SerializedName("low")
    LOW("Low", Color.parseColor("#BFC5FD")),

    @SerializedName("no")
    NO("No", Color.parseColor("#DADADE"));

}

