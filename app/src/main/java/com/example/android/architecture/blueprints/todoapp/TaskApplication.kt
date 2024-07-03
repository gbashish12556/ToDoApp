package com.example.android.architecture.blueprints.todoapp

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import java.io.File


@HiltAndroidApp
class TaskApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        val dexOutputDir: File = codeCacheDir
        dexOutputDir.setReadOnly()
    }

}