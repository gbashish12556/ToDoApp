package com.example.truecreditslist.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.android.architecture.blueprints.todoapp.data.source.local.TaskLocal
import kotlinx.coroutines.flow.Flow

@Dao
interface PrDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(posts: List<TaskLocal>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(taskLocal: TaskLocal)

    @Query("SELECT * FROM task_table WHERE isCompleted in (:filters)")
    fun allTasks(filters:List<String>): Flow<List<TaskLocal>>

    @Query("SELECT remoteId FROM task_table WHERE id = :localId")
    suspend fun getRemoteId(localId:Int):Int

    @Query("DELETE FROM task_table")
    suspend fun deletePrs()

}
