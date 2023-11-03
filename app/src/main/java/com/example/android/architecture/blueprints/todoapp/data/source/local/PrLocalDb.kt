package com.example.truecreditslist.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.android.architecture.blueprints.todoapp.data.response.Task


@Database(entities = [Task::class], version = 1, exportSchema = false)
abstract class PrLocalDb : RoomDatabase() {

    abstract fun prDao(): PrDao

}