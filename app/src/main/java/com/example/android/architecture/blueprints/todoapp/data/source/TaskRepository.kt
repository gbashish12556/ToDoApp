package com.example.navigithubpr.data.source

import com.example.android.architecture.blueprints.todoapp.TaskScereen.FilterType
import com.example.android.architecture.blueprints.todoapp.common.Resource
import com.example.android.architecture.blueprints.todoapp.data.response.Task
import kotlinx.coroutines.flow.Flow

interface TaskRepository {

    suspend fun getTasks(filterType: FilterType): Flow<Resource<List<Task>>>
    suspend fun refreshTask()

}
