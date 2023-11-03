package com.example.navigithubpr.data.source.remote

import com.example.android.architecture.blueprints.todoapp.data.response.Task
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {
    @GET("exec")
    suspend fun getAllResponse(): Response<List<Task>>
}