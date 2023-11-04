package com.example.navigithubpr.data.source.remote

import com.example.android.architecture.blueprints.todoapp.data.response.ApiResponse
import com.example.android.architecture.blueprints.todoapp.data.response.TaskRemote
import retrofit2.Response
import javax.inject.Inject

class ApiHelperImpl @Inject constructor(private val apiService: ApiService) : ApiHelper {

    override suspend fun getAllResponse(): Response<ApiResponse> {
        return apiService.getAllResponse();
    }

    override suspend fun updateTask(taskRemote: TaskRemote): Response<ApiResponse> {
        return apiService.editTask(id=taskRemote.id, content = taskRemote.content, title = taskRemote.title, status = taskRemote.status)
    }

    override suspend fun addTask(taskRemote: TaskRemote): Response<ApiResponse> {
       return apiService.addTask(content = taskRemote.content, title = taskRemote.title, status = taskRemote.status)
    }


}