package com.example.android.architecture.blueprints.todoapp.usecases

abstract class BaseCase<out Result,in Params> {

    abstract suspend operator fun invoke(parameters: Params? = null): Result
}