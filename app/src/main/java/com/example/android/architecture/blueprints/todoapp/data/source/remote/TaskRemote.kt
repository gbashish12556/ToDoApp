package com.example.android.architecture.blueprints.todoapp.data.response

data class Response(val status:Int, val message:String, val data:List<TaskRemote>)
data class TaskRemote(val id:Int, val title:String, val content:String, val status:Status)

enum class Status{
    ACTIVE, COMPLETED
}