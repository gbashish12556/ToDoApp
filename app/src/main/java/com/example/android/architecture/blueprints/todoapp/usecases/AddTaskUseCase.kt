package com.example.android.architecture.blueprints.todoapp.usecases

import com.example.android.architecture.blueprints.todoapp.common.Resource
import com.example.android.architecture.blueprints.todoapp.data.source.Task
import com.example.navigithubpr.data.source.TaskRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AddTaskUseCase @Inject constructor(val taskRepository: TaskRepository) :
    BaseCase<Flow<Resource<Task>>, Task>() {
    override suspend fun invoke(task: Task?): Flow<Resource<Task>> {
        return taskRepository.addTask(task!!)
    }
}