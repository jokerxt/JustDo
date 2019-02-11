package com.example.justdo.domain.entities.tasks

import android.os.Parcel
import android.os.Parcelable
import com.example.justdo.domain.entities.Priority
import com.example.justdo.helpers.KParcelable
import com.example.justdo.helpers.parcelableCreator
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import org.threeten.bp.LocalDateTime

data class TodoTask(
    @Expose @SerializedName("id")
    val id: Long,

    @Expose @SerializedName("name")
    val name: String,

    @Expose @SerializedName("desc")
    val desc: String?,

    @Expose @SerializedName("priority")
    val priority: Priority,

    @Expose @SerializedName("due_date")
    val dueDate: LocalDateTime
) : KParcelable {

    companion object {
        @JvmField val CREATOR = parcelableCreator(::TodoTask)
    }

    constructor(parcel: Parcel) : this(
        parcel.readLong(),
        parcel.readString() ?: "",
        parcel.readString(),
        parcel.readSerializable() as? Priority?  ?: Priority.NO,
        parcel.readSerializable() as? LocalDateTime? ?: LocalDateTime.now()
    )

    override fun writeToParcel(dest: Parcel, flags: Int) {
        with(dest) {
            writeLong(id)
            writeString(name)
            writeString(desc)
            writeSerializable(priority)
            writeSerializable(dueDate)
        }
    }
}