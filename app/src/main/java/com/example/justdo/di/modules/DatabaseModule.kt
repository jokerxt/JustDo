package com.example.justdo.di.modules

import android.content.Context
import androidx.room.Room
import com.example.justdo.model.data.db.TodoTasksDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DatabaseModule {

    @Singleton
    @Provides
    fun provideTodoTasksDatabase(context: Context) =
        Room.databaseBuilder(context, TodoTasksDatabase::class.java, "todo_db").build()

}