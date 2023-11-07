package com.example.android.architecture.blueprints.todoapp.data.response

data class ApiResponse(val status:Int, val message:String, val data:List<TaskRemote> = emptyList())
data class TaskRemote(val id:Int, val title:String, val content:String, val status:Status)

enum class Status{
    ACTIVE, COMPLETED
}