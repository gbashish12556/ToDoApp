package com.example.android.architecture.blueprints.todoapp.data.source

import com.example.android.architecture.blueprints.todoapp.TaskScereen.FilterType
import com.example.android.architecture.blueprints.todoapp.common.Resource
import com.example.android.architecture.blueprints.todoapp.data.source.local.TaskLocalDataSource
import com.example.android.architecture.blueprints.todoapp.data.source.remote.TaskRemoteDataSource
import com.example.android.architecture.blueprints.todoapp.data.toLocal
import com.example.android.architecture.blueprints.todoapp.data.toRemote
import com.example.android.architecture.blueprints.todoapp.data.toTask
import com.example.android.architecture.blueprints.todoapp.utils.NetworkHelper
import com.example.navigithubpr.data.source.TaskRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
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
                emit(Resource.Success(it.toTask()))
            }
        }catch (e:Exception){
            emit(Resource.Error(message = "Exception"))
        }
    }

    override suspend fun updateTask(task: Task) {
        withContext(coroutineDispatcher){
            taskLocalDataSource.addTask(task.toLocal())
            taskRemoteDataSource.updateTask(task.toRemote())
        }
    }

    override suspend fun addTask(task: Task) {
        withContext(coroutineDispatcher) {
            taskLocalDataSource.addTask(task.toLocal())
            taskRemoteDataSource.updateTask(task.toRemote())
        }
    }

    private suspend fun updateTasksFromRemoteDataSource() {
        withContext(coroutineDispatcher) {
            if(networkHelper.isNetworkConnected()) {
                val remoteTasks = taskRemoteDataSource.getTasks()
                if (remoteTasks.body()!!.size > 0) {
                    taskLocalDataSource.deleteAllTasks()
                    taskLocalDataSource.insertTasks(remoteTasks.body()!!.toLocal())
                }
            }
        }
    }

    override suspend fun refreshTask() {
        updateTasksFromRemoteDataSource()
    }
}