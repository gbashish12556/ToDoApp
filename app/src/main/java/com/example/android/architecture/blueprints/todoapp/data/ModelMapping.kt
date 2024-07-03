package com.example.android.architecture.blueprints.todoapp.data

import com.example.android.architecture.blueprints.todoapp.data.response.Status
import com.example.android.architecture.blueprints.todoapp.data.response.TaskRemote
import com.example.android.architecture.blueprints.todoapp.data.source.Task
import com.example.android.architecture.blueprints.todoapp.data.source.local.TaskLocal

fun TaskRemote.remoteToLocal() = TaskLocal(
    remoteId = id,
    title = title,
    content = content,
    isCompleted = status == Status.COMPLETED
)

fun TaskLocal.localToTask() = Task(
    id = id,
    title = title,
    content = content,
    isCompleted = isCompleted
)

fun Task.taskToLocal() = TaskLocal(
    id = id,
    title = title,
    content = content,
    isCompleted = isCompleted
)

fun Task.taskToRemote(remoteId: Int? = null) = TaskRemote(
    id = remoteId,
    title = title,
    content = content,
    status = if (isCompleted == true) Status.COMPLETED else Status.ACTIVE
)

fun List<Task>.toTaskToLocal() = map { task: Task -> task.taskToLocal() }

fun List<TaskLocal>.toLocalToTask() = map { taskLocal: TaskLocal -> taskLocal.localToTask() }

fun List<TaskRemote>.remoteToLocal() = map { taskremote: TaskRemote -> taskremote.remoteToLocal() }