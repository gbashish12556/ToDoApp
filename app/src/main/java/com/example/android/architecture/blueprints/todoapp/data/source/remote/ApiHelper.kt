package com.example.navigithubpr.data.source.remote

import com.example.android.architecture.blueprints.todoapp.data.response.ApiResponse
import com.example.android.architecture.blueprints.todoapp.data.response.TaskRemote
import retrofit2.Response

interface ApiHelper {

    suspend fun getAllResponse(): Response<ApiResponse>

    suspend fun updateTask(taskRemote: TaskRemote): Response<ApiResponse>

    suspend fun addTask(taskRemote:TaskRemote): Response<ApiResponse>

}