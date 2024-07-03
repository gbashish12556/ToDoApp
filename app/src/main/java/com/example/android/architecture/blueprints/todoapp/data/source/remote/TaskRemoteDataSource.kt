package com.example.android.architecture.blueprints.todoapp.data.source.remote


import com.example.android.architecture.blueprints.todoapp.data.response.ApiCommonResponse
import com.example.android.architecture.blueprints.todoapp.data.response.TaskListResponse
import com.example.android.architecture.blueprints.todoapp.data.response.TaskRemote
import com.example.android.architecture.blueprints.todoapp.data.response.TaskResponse
import retrofit2.Response

interface TaskRemoteDataSource {

    suspend fun getTasks(): Response<TaskListResponse>

    suspend fun updateTask(task: TaskRemote): Response<TaskResponse>

    suspend fun deleteTask(taskId: Int): Response<ApiCommonResponse>

    suspend fun addTask(task: TaskRemote): Response<TaskResponse>
}