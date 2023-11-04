package com.example.android.architecture.blueprints.todoapp.TaskScereen

import com.example.android.architecture.blueprints.todoapp.data.source.Task

data class TasksUiState(
    var tasks:List<Task> = emptyList(),
    var isLoading:Boolean = false,
    var isLoadingFailed:Boolean = false,
    var errorMessage:String = ""
)