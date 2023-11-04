package com.example.truecreditslist.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.android.architecture.blueprints.todoapp.data.source.local.TaskLocal


@Database(entities = [TaskLocal::class], version = 1, exportSchema = false)
abstract class PrLocalDb : RoomDatabase() {

    abstract fun prDao(): PrDao

}