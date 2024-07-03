package com.example.android.architecture.blueprints.todoapp.data.source.local

import com.example.android.architecture.blueprints.todoapp.screens.tasklist.FilterType
import kotlinx.coroutines.flow.Flow

interface TaskLocalDataSource {
    fun getTasks(filterType: FilterType): Flow<List<TaskLocal>>

    fun getTask(taskId: Int): Flow<TaskLocal>

    suspend fun deleteTask(taskId: Int)
    suspend fun updateTask(taskLocal: TaskLocal)

    suspend fun addTask(taskLocal: TaskLocal)

    suspend fun getRemoteId(localId: Int): Int

    suspend fun deleteAllTasks()

    suspend fun insertTasks(prList: List<TaskLocal>)
}