package com.example.android.architecture.blueprints.todoapp.data.source

import android.util.Log
import com.example.android.architecture.blueprints.todoapp.TaskList.FilterType
import com.example.android.architecture.blueprints.todoapp.common.Resource
import com.example.android.architecture.blueprints.todoapp.data.remoteToLocal
import com.example.android.architecture.blueprints.todoapp.data.source.local.TaskLocalDataSource
import com.example.android.architecture.blueprints.todoapp.data.source.remote.TaskRemoteDataSource
import com.example.android.architecture.blueprints.todoapp.data.taskToLocal
import com.example.android.architecture.blueprints.todoapp.data.taskToRemote
import com.example.android.architecture.blueprints.todoapp.data.toLocalToTask
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
            taskLocalDataSource.getTasks(filterType).collect{data->
                emit(Resource.Success(data.toLocalToTask()))
            }
        }catch (e:Exception){
            emit(Resource.Error(message = "Exception"))
        }
    }

    override suspend fun updateTask(task: Task) {
        withContext(coroutineDispatcher){
            taskLocalDataSource.addTask(task.taskToLocal())
            taskRemoteDataSource.updateTask(task.taskToRemote(taskLocalDataSource.getRemoteId(task.id)))
        }
    }

    override suspend fun addTask(task: Task) {
        withContext(coroutineDispatcher) {
            taskLocalDataSource.addTask(task.taskToLocal())
            taskRemoteDataSource.updateTask(task.taskToRemote(taskLocalDataSource.getRemoteId(task.id)))
        }
    }

    private suspend fun updateTasksFromRemoteDataSource() {
        withContext(coroutineDispatcher) {
            if(networkHelper.isNetworkConnected()) {
                val remoteTasks = taskRemoteDataSource.getTasks()
                if (remoteTasks.isSuccessful) {
                    remoteTasks.body()?.data?.let {list->
                        if(list.size!! > 0){
                            taskLocalDataSource.deleteAllTasks()
                            Log.d("AshishGupta",list.remoteToLocal().toString())
                            taskLocalDataSource.insertTasks(list.remoteToLocal())
                        }
                    }
                }
            }
        }
    }

    override suspend fun refreshTask() {
        updateTasksFromRemoteDataSource()
    }
}