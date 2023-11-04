package com.example.navigithubpr.data.source.remote

import com.example.android.architecture.blueprints.todoapp.data.response.TaskRemote
import retrofit2.Response

interface ApiHelper {

    suspend fun getAllResponse(): Response<List<TaskRemote>>

    suspend fun updateTask(taskRemote: TaskRemote)

    suspend fun addTask(taskRemote:TaskRemote)

}