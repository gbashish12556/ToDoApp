package com.example.navigithubpr.data.source.remote

import com.example.android.architecture.blueprints.todoapp.data.response.ApiCommonResponse
import com.example.android.architecture.blueprints.todoapp.data.response.TaskListResponse
import com.example.android.architecture.blueprints.todoapp.data.response.TaskRemote
import com.example.android.architecture.blueprints.todoapp.data.response.TaskResponse
import retrofit2.Response

interface ApiHelper {

    suspend fun getAllResponse(): Response<TaskListResponse>

    suspend fun updateTask(taskRemote: TaskRemote): Response<TaskResponse>

    suspend fun addTask(taskRemote: TaskRemote): Response<TaskResponse>

    suspend fun deleteTask(taskId: Int): Response<ApiCommonResponse>

}