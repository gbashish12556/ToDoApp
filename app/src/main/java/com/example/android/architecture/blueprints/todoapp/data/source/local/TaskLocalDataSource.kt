package com.example.android.architecture.blueprints.todoapp.data.source.local

import com.example.android.architecture.blueprints.todoapp.TaskScereen.FilterType
import com.example.android.architecture.blueprints.todoapp.data.response.Task
import kotlinx.coroutines.flow.Flow

interface TaskLocalDataSource {
    fun getTasks(filterType: FilterType): Flow<List<Task>>
    suspend fun deleteAllTasks()
    suspend fun insertTasks(prList:List<Task>)
}