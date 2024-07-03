package com.example.navigithubpr.data.source.remote


import com.example.android.architecture.blueprints.todoapp.data.response.ApiCommonResponse
import com.example.android.architecture.blueprints.todoapp.data.response.TaskListResponse
import com.example.android.architecture.blueprints.todoapp.data.response.TaskRemote
import com.example.android.architecture.blueprints.todoapp.data.response.TaskResponse
import com.example.android.architecture.blueprints.todoapp.data.source.remote.TaskRemoteDataSource
import retrofit2.Response

class RemoteDataSource(private val apiHeler: ApiHelper) : TaskRemoteDataSource {

    override suspend fun getTasks(): Response<TaskListResponse> {
        return apiHeler.getAllResponse()
    }

    override suspend fun updateTask(taskRemote: TaskRemote): Response<TaskResponse> {
        return apiHeler.updateTask(taskRemote)
    }

    override suspend fun deleteTask(taskId: Int): Response<ApiCommonResponse> {
        return apiHeler.deleteTask(taskId)
    }

    override suspend fun addTask(task: TaskRemote): Response<TaskResponse> {
        return apiHeler.addTask(task)
    }

}