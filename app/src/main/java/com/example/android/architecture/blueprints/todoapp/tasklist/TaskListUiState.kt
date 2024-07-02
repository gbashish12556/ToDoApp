package com.example.android.architecture.blueprints.todoapp.tasklist

import com.example.android.architecture.blueprints.todoapp.data.source.Task

data class TaskListUiState(
    var tasks:List<Task> = emptyList(),
    var isLoading:Boolean = false,
    var isLoadingFailed:Boolean = false,
    var errorMessage:String = ""
)