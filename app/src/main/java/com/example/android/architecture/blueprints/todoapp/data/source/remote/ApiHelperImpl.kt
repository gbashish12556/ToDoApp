package com.example.navigithubpr.data.source.remote

import android.util.Log
import com.example.android.architecture.blueprints.todoapp.data.response.ApiCommonResponse
import com.example.android.architecture.blueprints.todoapp.data.response.TaskListResponse
import com.example.android.architecture.blueprints.todoapp.data.response.TaskRemote
import com.example.android.architecture.blueprints.todoapp.data.response.TaskResponse
import retrofit2.Response
import javax.inject.Inject

class ApiHelperImpl @Inject constructor(private val apiService: ApiService) : ApiHelper {

    override suspend fun getAllResponse(): Response<TaskListResponse> {
        var res = apiService.getAllResponse()
        Log.d("AshishGuptaNew1", "${res}")
        return res
    }

    override suspend fun updateTask(taskRemote: TaskRemote): Response<TaskResponse> {
        return apiService.editTask(
            id = taskRemote.id!!,
            content = taskRemote.content,
            title = taskRemote.title,
            status = taskRemote.status
        )
    }

    override suspend fun addTask(taskRemote: TaskRemote): Response<TaskResponse> {
        return apiService.addTask(
            content = taskRemote.content,
            title = taskRemote.title,
            status = taskRemote.status
        )
    }

    override suspend fun deleteTask(taskId: Int): Response<ApiCommonResponse> {
        var res = apiService.deleteTask(id = taskId)
        Log.d("AshishGuptaNew", "${res}")
        return res
    }


}