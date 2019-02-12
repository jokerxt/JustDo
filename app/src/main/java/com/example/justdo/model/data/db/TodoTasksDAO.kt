package com.example.justdo.model.data.db

import androidx.room.*
import androidx.room.OnConflictStrategy.REPLACE
import io.reactivex.Flowable

@Dao
interface TodoTasksDAO {

    @get:Query("SELECT COUNT(*) FROM tasks")
    val count: Flowable<Int>

    @get:Query("SELECT * FROM tasks")
    val allTodoTasks: List<DbTodoTask>

    @Query("SELECT * FROM tasks WHERE id = :id")
    fun getTodoTask(id: Long): Flowable<DbTodoTask>

    @Insert(onConflict = REPLACE)
    fun putTodoTasks(tasks: List<DbTodoTask>): List<Long>

    @Insert(onConflict = REPLACE)
    fun putTodoTask(task: DbTodoTask): Long

    @Update
    fun updateTodoTasks(dolls: List<DbTodoTask>): Int

    @Update
    fun updateTodoTasks(vararg doll: DbTodoTask): Int

    @Delete
    fun deleteTodoTasks(vararg doll: DbTodoTask): Int

    @Query("DELETE FROM tasks WHERE id IN (:ids)")
    fun deleteTodoTask(ids: List<Long>): Int

    @Query("DELETE FROM tasks WHERE id = :id")
    fun deleteTodoTask(vararg id: Long): Int

    @Query("DELETE FROM tasks")
    fun clearTable(): Int

}