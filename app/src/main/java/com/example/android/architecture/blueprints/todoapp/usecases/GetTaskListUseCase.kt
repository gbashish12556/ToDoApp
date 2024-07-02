package com.example.android.architecture.blueprints.todoapp.usecases

import android.util.Log
import com.example.android.architecture.blueprints.todoapp.tasklist.FilterType
import com.example.android.architecture.blueprints.todoapp.common.Resource
import com.example.android.architecture.blueprints.todoapp.data.source.Task
import com.example.navigithubpr.data.source.TaskRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetTaskListUseCase @Inject constructor(val taskRepository: TaskRepository):BaseCase<Flow<Resource<List<Task>>>, FilterType>() {
    override suspend fun invoke(parameters: FilterType?): Flow<Resource<List<Task>>> {
        return taskRepository.getTasks(parameters!!)
    }
}