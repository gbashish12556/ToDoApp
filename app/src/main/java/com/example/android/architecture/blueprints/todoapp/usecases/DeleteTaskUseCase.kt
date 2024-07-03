package com.example.android.architecture.blueprints.todoapp.usecases

import com.example.android.architecture.blueprints.todoapp.common.Resource
import com.example.navigithubpr.data.source.TaskRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class DeleteTaskUseCase @Inject constructor(val taskRepository: TaskRepository) :
    BaseCase<Flow<Resource<Unit>>, Int>() {
    override suspend fun invoke(taskId: Int?): Flow<Resource<Unit>> {
        return taskRepository.deleteTask(taskId!!)
    }

}