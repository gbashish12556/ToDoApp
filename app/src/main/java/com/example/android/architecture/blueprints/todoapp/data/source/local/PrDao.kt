package com.example.truecreditslist.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.android.architecture.blueprints.todoapp.data.response.Task
import kotlinx.coroutines.flow.Flow

@Dao
interface PrDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(posts: List<Task>)

    @Query("SELECT * FROM task_table")
    fun allPrs(): Flow<List<Task>>

    @Query("DELETE FROM task_table")
    suspend fun deletePrs()

}
