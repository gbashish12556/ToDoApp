package com.example.android.architecture.blueprints.todoapp.data.response

import androidx.room.Entity


data class Response(val status:Int, val message:String, val data:List<Task>)
@Entity(tableName = "task_table")
data class Task(val id:Int, val title:String, val content:String, val isCompleted:Boolean)