package com.example.navigithubpr.data.source.remote

import com.example.android.architecture.blueprints.todoapp.data.response.ApiResponse
import com.example.android.architecture.blueprints.todoapp.data.response.Status
import com.example.android.architecture.blueprints.todoapp.data.response.TaskRemote
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiService {
    @GET("exec")
    suspend fun getAllResponse(): Response<ApiResponse>

    @POST("exec")
    suspend fun addTask(
        @Query("type") type: String = "add",
        @Query("title") title: String,
        @Query("content") content: String,
        @Query("status") status: Status
    ): Response<ApiResponse>

    @POST("exec")
    suspend fun editTask(
        @Query("type") type: String = "edit",
        @Query("id") id: Int,
        @Query("title") title: String,
        @Query("content") content: String,
        @Query("status") status: Status
    ): Response<ApiResponse>
}