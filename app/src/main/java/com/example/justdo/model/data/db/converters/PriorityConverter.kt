package com.example.justdo.model.data.db.converters

import androidx.room.TypeConverter
import com.example.justdo.domain.entities.Priority

class PriorityConverter {

    @TypeConverter
    fun priorityFromString(value: String?) = value?.let { Priority.valueOf(it) }

    @TypeConverter
    fun priorityToString(priority: Priority?) = priority?.toString()

}