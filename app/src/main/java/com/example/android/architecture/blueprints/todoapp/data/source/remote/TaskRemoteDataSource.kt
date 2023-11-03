package com.example.android.architecture.blueprints.todoapp.data.source.remote


import com.example.android.architecture.blueprints.todoapp.data.response.Task
import retrofit2.Response

interface TaskRemoteDataSource {

    suspend fun getTasks(): Response<List<Task>>

}