package com.example.android.architecture.blueprints.todoapp.data.source

data class Task(
    val id: Int? = null,
    var title: String = "",
    var content: String = "",
    var isCompleted: Boolean = false
)