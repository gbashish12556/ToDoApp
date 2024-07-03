package com.example.android.architecture.blueprints.todoapp.screens.tasklist

abstract class FilterType {
    abstract val text: String
    abstract val queryParams: List<Boolean>
}

data class All(
    override val text: String = "All",
    override val queryParams: List<Boolean> = mutableListOf(true, false)
) :
    FilterType()

data class Complete(
    override val text: String = "Complete",
    override val queryParams: List<Boolean> = mutableListOf(true)
) :
    FilterType()

data class InComplete(
    override val text: String = "InComplete",
    override val queryParams: List<Boolean> = mutableListOf(false)
) :
    FilterType()