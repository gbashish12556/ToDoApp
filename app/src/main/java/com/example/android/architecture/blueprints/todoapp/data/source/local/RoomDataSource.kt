package com.example.kutumbreadsms.data.source.db

import androidx.lifecycle.LiveData
import com.example.android.architecture.blueprints.todoapp.data.response.Task
import com.example.android.architecture.blueprints.todoapp.data.source.local.TaskLocalDataSource
import com.example.truecreditslist.db.PrDao
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RoomDataSource internal constructor(
    private val prDao: PrDao,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO ) : TaskLocalDataSource{

    override fun getPrs(): Flow<List<Task>> {
        return prDao.allPrs()
    }

    override suspend fun deleteAllPrs() = withContext(ioDispatcher){
        prDao.deletePrs()
    }

    override suspend fun insertPrs(prList: List<Task>) = withContext(ioDispatcher){
        prDao.insertAll(prList)
    }

}