package com.example.justdo.model.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.justdo.model.data.db.converters.LocalDateTimeConverter
import com.example.justdo.model.data.db.converters.PriorityConverter

@Database(entities = [DbTodoTask::class], version = 1)
@TypeConverters(LocalDateTimeConverter::class, PriorityConverter::class)
abstract class TodoTasksDatabase : RoomDatabase() {
    abstract fun todoTasks(): TodoTasksDAO
}