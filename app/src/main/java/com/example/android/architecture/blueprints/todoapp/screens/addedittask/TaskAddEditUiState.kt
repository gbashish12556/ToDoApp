package com.example.android.architecture.blueprints.todoapp.screens.addedittask

import com.example.android.architecture.blueprints.todoapp.data.source.Task

data class TaskAddEditUiState(
    var task: Task = Task(),
    var isLoading: Boolean = false,
    var isTaskUpdated: Boolean = false,
    var isLoadingFailed: Boolean = false,
    var errorMessage: String = ""
)