package com.example.justdo.model.data.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.justdo.domain.entities.Priority
import org.threeten.bp.LocalDateTime

@Entity(tableName = "tasks")
data class DbTodoTask(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Long,

    @ColumnInfo(name = "name")
    val name: String,

    @ColumnInfo(name = "desc")
    val desc: String?,

    @ColumnInfo(name = "priority")
    val priority: Priority,

    @ColumnInfo(name = "due_date")
    val dueDate: LocalDateTime

)