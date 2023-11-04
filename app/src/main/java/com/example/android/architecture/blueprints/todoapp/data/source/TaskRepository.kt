package com.example.navigithubpr.data.source

import com.example.android.architecture.blueprints.todoapp.common.Resource
import com.example.android.architecture.blueprints.todoapp.data.response.Task
import kotlinx.coroutines.flow.Flow

interface TaskRepository {

    suspend fun getTasks(): Flow<List<Task>>
    suspend fun refreshTask()

}
