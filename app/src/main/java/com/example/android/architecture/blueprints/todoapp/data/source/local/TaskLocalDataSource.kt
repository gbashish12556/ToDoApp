package com.example.android.architecture.blueprints.todoapp.data.source.local

import com.example.android.architecture.blueprints.todoapp.TaskScereen.FilterType
import kotlinx.coroutines.flow.Flow

interface TaskLocalDataSource {
    fun getTasks(filterType: FilterType): Flow<List<TaskLocal>>
    suspend fun updateTask(taskLocal:TaskLocal)

    suspend fun addTask(taskLocal:TaskLocal)
    suspend fun deleteAllTasks()
    suspend fun insertTasks(prList:List<TaskLocal>)
}