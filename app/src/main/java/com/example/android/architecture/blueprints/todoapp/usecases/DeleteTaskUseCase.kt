package com.example.android.architecture.blueprints.todoapp.usecases

import com.example.navigithubpr.data.source.TaskRepository
import javax.inject.Inject


class DeleteTaskUseCase @Inject constructor(val taskRepository: TaskRepository) :
    BaseCase<Unit, Int>() {
    override suspend fun invoke(taskId: Int?) {
        taskRepository.deleteTask(taskId!!)
    }

}