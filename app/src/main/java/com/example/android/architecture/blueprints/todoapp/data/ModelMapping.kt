package com.example.android.architecture.blueprints.todoapp.data

import com.example.android.architecture.blueprints.todoapp.data.response.Status
import com.example.android.architecture.blueprints.todoapp.data.response.TaskRemote
import com.example.android.architecture.blueprints.todoapp.data.source.Task
import com.example.android.architecture.blueprints.todoapp.data.source.local.TaskLocal

fun TaskRemote.toLocal() = TaskLocal(
    id = id,
    title = title,
    content = content,
    isCompleted = status == Status.COMPLETED
)

fun TaskLocal.toTask() = Task(
    id = id,
    title = title,
    content = content,
    isCompleted  = isCompleted
)

fun Task.toLocal() = TaskLocal(
    id = id,
    title = title,
    content = content,
    isCompleted  = isCompleted
)

fun Task.toRemote() = TaskRemote(
    id = id,
    title = title,
    content = content,
    status  = if(isCompleted == true) Status.COMPLETED else Status.ACTIVE
)

fun List<Task>.toLocal() = map { task: Task ->  task.toLocal()}

fun List<TaskLocal>.toTask() = map { taskLocal: TaskLocal ->  taskLocal.toTask()}

fun List<TaskRemote>.toLocal() = map { taskremote: TaskRemote ->  taskremote.toLocal()}