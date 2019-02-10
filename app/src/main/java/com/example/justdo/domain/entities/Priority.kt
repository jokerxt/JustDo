package com.example.justdo.domain.entities

import android.os.Parcel
import com.example.justdo.helpers.KParcelable
import com.example.justdo.helpers.parcelableCreator
import com.google.gson.annotations.SerializedName
import java.io.Serializable

enum class Priority(private val jsonName: String) : Serializable {

    @SerializedName("high")
    HIGH("high"),

    @SerializedName("medium")
    MEDIUM("medium"),

    @SerializedName("low")
    LOW("low"),

    @SerializedName("no")
    NO("no");

}

