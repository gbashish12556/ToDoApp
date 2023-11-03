package com.example.android.architecture.blueprints.todoapp.data.source.local

import com.example.android.architecture.blueprints.todoapp.data.response.Task
import kotlinx.coroutines.flow.Flow

interface TaskLocalDataSource {
    fun getPrs(): Flow<List<Task>>
    suspend fun deleteAllPrs()
    suspend fun insertPrs(prList:List<Task>)
}