package com.example.navigithubpr.data.source.remote

import com.example.android.architecture.blueprints.todoapp.data.response.Task
import com.example.android.architecture.blueprints.todoapp.data.source.remote.TaskRemoteDataSource
import retrofit2.Response

class RemoteDataSource(private val apiHeler: ApiHelper): TaskRemoteDataSource {

    override suspend fun getTasks():Response<List<Task>> {
      return apiHeler.getAllResponse()
    }

}