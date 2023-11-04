package com.example.navigithubpr.data.source

import com.example.android.architecture.blueprints.todoapp.TaskScereen.FilterType
import com.example.android.architecture.blueprints.todoapp.common.Resource
import com.example.android.architecture.blueprints.todoapp.data.source.Task
import kotlinx.coroutines.flow.Flow

interface TaskRepository {

    suspend fun getTasks(filterType: FilterType): Flow<Resource<List<Task>>>

    suspend fun updateTask(task:Task)

    suspend fun addTask(task:Task)

    suspend fun refreshTask()

}
