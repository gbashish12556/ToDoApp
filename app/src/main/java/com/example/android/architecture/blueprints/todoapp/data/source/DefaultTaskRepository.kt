package com.example.android.architecture.blueprints.todoapp.data.source

import com.example.android.architecture.blueprints.todoapp.common.Resource
import com.example.android.architecture.blueprints.todoapp.data.*
import com.example.android.architecture.blueprints.todoapp.data.source.local.TaskLocalDataSource
import com.example.android.architecture.blueprints.todoapp.data.source.remote.TaskRemoteDataSource
import com.example.android.architecture.blueprints.todoapp.tasklist.FilterType
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
) : TaskRepository {

    override suspend fun getTasks(filterType: FilterType) = flow {
        refreshTask()
        try {
            taskLocalDataSource.getTasks(filterType).collect { data ->
                emit(Resource.Success(data.toLocalToTask()))
            }
        } catch (e: Exception) {
            emit(Resource.Error(message = "Exception"))
        }
    }

    override suspend fun updateTask(task: Task) {
        withContext(coroutineDispatcher) {
            taskLocalDataSource.addTask(task.taskToLocal())
            taskRemoteDataSource.updateTask(task.taskToRemote(taskLocalDataSource.getRemoteId(task.id)))
        }
    }

    override suspend fun deleteTask(taskId: Int) {
        taskLocalDataSource.deleteTask(taskId)
    }

    override suspend fun getTask(taskId: Int) = flow {
        try {
            taskLocalDataSource.getTask(taskId).collect { data ->
                emit(Resource.Success(data.localToTask()))
            }
        } catch (e: java.lang.Exception) {
            emit(Resource.Error(message = "Failed"))
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
            if (networkHelper.isNetworkConnected()) {
                val remoteTasks = taskRemoteDataSource.getTasks()
                if (remoteTasks.isSuccessful) {
                    remoteTasks.body()?.data?.let { list ->
                        if (list.size > 0) {
                            taskLocalDataSource.deleteAllTasks()
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