package com.example.justdo.model.data

import com.example.justdo.domain.entities.tasks.TodoTask
import timber.log.Timber
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.TimeUnit

class TodoMapCache {

    private data class TodoTaskCacheItem(val time: Long, val data: TodoTask)

    private val cache = ConcurrentHashMap<Long, TodoTaskCacheItem>()
    private val lifetime = TimeUnit.HOURS.toMillis(1)
    private var insertTime = 0L

    fun get(id: Long): TodoTask? {
        val item = cache[id]
        return if (item == null || System.currentTimeMillis() - item.time > lifetime) {
            Timber.d("Get NULL TodoTask($id)")
            null
        } else {
            Timber.d("Get CACHED TodoTask($id)")
            item.data
        }
    }

    fun getList(): List<TodoTask>? {
        val items = cache.values
        return if (items.isEmpty() || System.currentTimeMillis() - insertTime > lifetime) {
            Timber.d("Get NULL list of TodoTask")
            null
        } else {
            Timber.d("Get CACHED list of TodoTask")
            items.asSequence().map { it.data }.toList()
        }
    }

    fun put(data: List<TodoTask>) {
        Timber.d("Put todo tasks list")
        insertTime = System.currentTimeMillis()
        cache.putAll(
            data
                .asSequence()
                .map { TodoTaskCacheItem(System.currentTimeMillis(), it) }
                .associateBy { it.data.id }
        )
    }

    fun clear() {
        Timber.d("Clear cache")
        cache.clear()
    }
}