package com.example.navigithubpr.data.source.remote


import com.example.android.architecture.blueprints.todoapp.data.response.ApiResponse
import com.example.android.architecture.blueprints.todoapp.data.response.TaskRemote
import com.example.android.architecture.blueprints.todoapp.data.source.remote.TaskRemoteDataSource
import retrofit2.Response

class RemoteDataSource(private val apiHeler: ApiHelper): TaskRemoteDataSource {

    override suspend fun getTasks():Response<ApiResponse> {
      return apiHeler.getAllResponse()
    }

    override suspend fun updateTask(taskRemote: TaskRemote) {
        apiHeler.updateTask(taskRemote)
    }

    override suspend fun addTask(task: TaskRemote) {
        apiHeler.addTask(task)
    }

}