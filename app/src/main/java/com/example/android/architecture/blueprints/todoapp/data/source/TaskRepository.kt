package com.example.navigithubpr.data.source

import com.example.android.architecture.blueprints.todoapp.common.Resource
import com.example.android.architecture.blueprints.todoapp.data.source.Task
import com.example.android.architecture.blueprints.todoapp.tasklist.FilterType
import kotlinx.coroutines.flow.Flow

interface TaskRepository {

    suspend fun getTasks(filterType: FilterType): Flow<Resource<List<Task>>>

    suspend fun updateTask(task: Task)

    suspend fun deleteTask(taskId: Int)

    suspend fun getTask(taskId: Int): Flow<Resource<Task>>

    suspend fun addTask(task: Task)

    suspend fun refreshTask()

}
