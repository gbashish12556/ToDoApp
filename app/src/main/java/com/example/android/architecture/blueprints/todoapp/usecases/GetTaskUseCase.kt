package com.example.android.architecture.blueprints.todoapp.usecases

import com.example.android.architecture.blueprints.todoapp.common.Resource
import com.example.android.architecture.blueprints.todoapp.data.source.Task
import com.example.navigithubpr.data.source.TaskRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetTaskUseCase @Inject constructor(val taskRepository: TaskRepository):BaseCase<Flow<Resource<Task>>, Int>() {
    override suspend fun invoke(taskId: Int?): Flow<Resource<Task>> {
        return taskRepository.getTask(taskId!!)
    }

}