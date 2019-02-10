package com.example.justdo.domain.entities

import android.os.Parcel
import com.example.justdo.helpers.KParcelable
import com.example.justdo.helpers.parcelableCreator
import com.google.gson.annotations.SerializedName

enum class Priority(private val jsonName: String) : KParcelable {

    @SerializedName("high")
    HIGH("high"),

    @SerializedName("medium")
    MEDIUM("medium"),

    @SerializedName("low")
    LOW("low"),

    @SerializedName("no")
    NO("no");

    companion object {
        @JvmField val CREATOR = parcelableCreator(::Priority)
    }

    constructor(parcel: Parcel) : this(parcel.readString() ?: Priority.NO.jsonName)

    override fun writeToParcel(dest: Parcel, flags: Int) {
        with(dest) {
            writeString(jsonName)
        }
    }

    override fun toString() = jsonName

}

