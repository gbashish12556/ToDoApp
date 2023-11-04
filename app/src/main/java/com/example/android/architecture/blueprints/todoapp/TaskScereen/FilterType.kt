package com.example.android.architecture.blueprints.todoapp.TaskScereen

import java.io.File

abstract class FilterType {
    abstract val text: String
    abstract val queryParams:List<String>
}

data class All(override val text: String = "All", override val queryParams: List<String> = mutableListOf("TRUE","FALSE")):FilterType()
data class Complete(override val text: String = "Complete", override val queryParams: List<String> = mutableListOf("TRUE")):FilterType()
data class InComplete(override val text: String = "InComplete", override val queryParams: List<String> = mutableListOf("FALSE")):FilterType()