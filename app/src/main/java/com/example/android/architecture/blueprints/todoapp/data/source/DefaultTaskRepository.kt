package com.example.android.architecture.blueprints.todoapp.data.source

import com.example.android.architecture.blueprints.todoapp.common.Resource
import com.example.android.architecture.blueprints.todoapp.data.*
import com.example.android.architecture.blueprints.todoapp.data.source.local.TaskLocalDataSource
import com.example.android.architecture.blueprints.todoapp.data.source.remote.TaskRemoteDataSource
import com.example.android.architecture.blueprints.todoapp.screens.tasklist.FilterType
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
        emit(Resource.Loading())
        refreshTask()
        try {
            taskLocalDataSource.getTasks(filterType).collect { data ->
                emit(Resource.Success(data.toLocalToTask()))
            }
        } catch (e: Exception) {
            emit(Resource.Error(message = "Failed"))
        }
    }

    override suspend fun updateTask(task: Task) = flow {
        emit(Resource.Loading())
        task.id?.let {
            var remote = taskRemoteDataSource.updateTask(
                task.taskToRemote(
                    taskLocalDataSource.getRemoteId(it)
                )
            )
            if (remote.isSuccessful) {
                taskLocalDataSource.updateTask(task.taskToLocal())
                emit(Resource.Success(task))
            } else {
                emit(Resource.Error(task, message = "Exception"))
            }
        } ?: run {
            emit(Resource.Error(task, message = "Exception"))
        }

    }

    override suspend fun deleteTask(taskId: Int) = flow {
        emit(Resource.Loading())
        var remote = taskRemoteDataSource.deleteTask(taskLocalDataSource.getRemoteId(taskId))
        if (remote.isSuccessful) {
            taskLocalDataSource.deleteTask(taskId)
            emit(Resource.Success(Unit))
        } else {
            emit(Resource.Error(message = "Failed"))
        }
    }

    override suspend fun getTask(taskId: Int) = flow {
        emit(Resource.Loading())
        try {
            taskLocalDataSource.getTask(taskId).collect { data ->
                emit(Resource.Success(data.localToTask()))
            }
        } catch (e: java.lang.Exception) {
            emit(Resource.Error(message = "Failed"))
        }
    }

    override suspend fun addTask(task: Task) = flow {
        emit(Resource.Loading())
        val remote = taskRemoteDataSource.addTask(
            task.taskToRemote()
        )
        if (remote.isSuccessful) {
            remote.body()?.data?.remoteToLocal()?.let { data ->
                taskLocalDataSource.addTask(data)
                emit(Resource.Success(data.localToTask()))
            } ?: run {
                emit(Resource.Error(task, message = "Data conversion failed"))
            }
        } else {
            emit(Resource.Error(message = "Failed"))
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