package com.example.android.architecture.blueprints.todoapp.data.source.remote


import com.example.android.architecture.blueprints.todoapp.data.response.TaskRemote
import retrofit2.Response

interface TaskRemoteDataSource {

    suspend fun getTasks(): Response<List<TaskRemote>>

    suspend fun updateTask(task:TaskRemote)

    suspend fun addTask(task:TaskRemote)
}