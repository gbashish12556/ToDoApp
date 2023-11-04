package com.example.android.architecture.blueprints.todoapp.data.source

import androidx.compose.runtime.collectAsState
import com.example.android.architecture.blueprints.todoapp.TaskScereen.FilterType
import com.example.android.architecture.blueprints.todoapp.common.Resource
import com.example.android.architecture.blueprints.todoapp.data.response.Task
import com.example.android.architecture.blueprints.todoapp.data.source.local.TaskLocalDataSource
import com.example.android.architecture.blueprints.todoapp.data.source.remote.TaskRemoteDataSource
import com.example.android.architecture.blueprints.todoapp.utils.NetworkHelper
import com.example.navigithubpr.data.source.TaskRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext

class DefaultTaskRepository(
    val taskRemoteDataSource: TaskRemoteDataSource,
    val taskLocalDataSource: TaskLocalDataSource,
    val networkHelper: NetworkHelper,
    val coroutineDispatcher: CoroutineDispatcher = Dispatchers.IO
):TaskRepository {

    override suspend fun getTasks(filterType: FilterType) = flow {
        refreshTask()
        try {
            taskLocalDataSource.getTasks(filterType).collect{
                emit(Resource.Success(it))
            }
        }catch (e:Exception){
            emit(Resource.Error(message = "Exception"))
        }
    }

    private suspend fun updateTasksFromRemoteDataSource() {
        withContext(coroutineDispatcher) {
            if(networkHelper.isNetworkConnected()) {
                val remoteTasks = taskRemoteDataSource.getTasks()
                if (remoteTasks.body()!!.size > 0) {
                    taskLocalDataSource.deleteAllTasks()
                    taskLocalDataSource.insertTasks(remoteTasks.body()!!)
                }
            }
        }
    }

    override suspend fun refreshTask() {
        updateTasksFromRemoteDataSource()
    }
}