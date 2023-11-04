package com.example.android.architecture.blueprints.todoapp.data.source.local

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "task_table")
data class TaskLocal(@PrimaryKey val id:Int, val remoteId:Int? = null,  val title:String, val content:String, val isCompleted:Boolean)