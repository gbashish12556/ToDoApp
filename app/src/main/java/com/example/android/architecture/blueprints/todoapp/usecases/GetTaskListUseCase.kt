package com.example.android.architecture.blueprints.todoapp.usecases

import com.example.android.architecture.blueprints.todoapp.TaskScereen.FilterType
import com.example.android.architecture.blueprints.todoapp.common.Resource
import com.example.android.architecture.blueprints.todoapp.data.source.Task
import com.example.navigithubpr.data.source.TaskRepository
import kotlinx.coroutines.flow.Flow

class GetTaskListUseCase(val taskRepository: TaskRepository):BaseCase<Flow<Resource<List<Task>>>, FilterType>() {
    override suspend fun invoke(parameters: FilterType?): Flow<Resource<List<Task>>> {
        return taskRepository.getTasks(parameters!!)
    }
}