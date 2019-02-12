package com.example.justdo.model.data.db.converters

import androidx.room.TypeConverter
import org.threeten.bp.LocalDateTime
import org.threeten.bp.ZoneOffset

class LocalDateTimeConverter {

    @TypeConverter
    fun localDateTimeFromTimestamp(value: Long?) = value?.let { LocalDateTime.ofEpochSecond(it, 0, ZoneOffset.UTC) }

    @TypeConverter
    fun localDateTimeToTimestamp(date: LocalDateTime?) = date?.toEpochSecond(ZoneOffset.UTC)

}