package com.example.justdo.domain.entities.tasks

import android.os.Parcel
import android.os.Parcelable
import com.example.justdo.domain.entities.Priority
import com.example.justdo.helpers.KParcelable
import com.example.justdo.helpers.parcelableCreator
import org.threeten.bp.LocalDateTime

data class TodoTask(
    val id: Long,
    val name: String,
    val desc: String?,
    val priority: Priority,
    val dueDate: LocalDateTime
) : KParcelable {

    companion object {
        @JvmField val CREATOR = parcelableCreator(::TodoTask)
    }

    constructor(parcel: Parcel) : this(
        parcel.readLong(),
        parcel.readString() ?: "",
        parcel.readString(),
        parcel.readParcelable(Priority::class.java.classLoader) ?: Priority.NO,
        parcel.readSerializable() as? LocalDateTime? ?: LocalDateTime.now()
    )

    override fun writeToParcel(dest: Parcel, flags: Int) {
        with(dest) {
            writeLong(id)
            writeString(name)
            writeString(desc)
            writeParcelable(priority, Parcelable.PARCELABLE_WRITE_RETURN_VALUE)
            writeSerializable(dueDate)
        }
    }
}