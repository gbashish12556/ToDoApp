package com.example.navigithubpr.data.source.remote

import com.example.android.architecture.blueprints.todoapp.data.response.Task
import retrofit2.Response

interface ApiHelper {

    suspend fun getAllResponse(): Response<List<Task>>

}