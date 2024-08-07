package com.example.android.architecture.blueprints.todoapp.screens.taskdetail

import com.example.android.architecture.blueprints.todoapp.data.source.Task


data class TaskDetailUiState(
    var task: Task = Task(),
    var isLoading: Boolean = false,
    var isTaskDeleted: Boolean = false,
    var isLoadingFailed: Boolean = false,
    var errorMessage: String = ""
)