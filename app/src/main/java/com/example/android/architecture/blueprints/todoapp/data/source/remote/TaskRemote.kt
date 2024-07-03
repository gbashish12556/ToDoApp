package com.example.android.architecture.blueprints.todoapp.data.response

data class TaskListResponse(
    val status: Int,
    val message: String,
    val data: List<TaskRemote> = emptyList()
)

data class TaskResponse(val status: Int, val message: String, val data: TaskRemote? = null)
data class ApiCommonResponse(val status: Int, val message: String)

data class TaskRemote(
    val id: Int? = null,
    val title: String,
    val content: String,
    val status: Status
)

enum class Status {
    ACTIVE, COMPLETED
}