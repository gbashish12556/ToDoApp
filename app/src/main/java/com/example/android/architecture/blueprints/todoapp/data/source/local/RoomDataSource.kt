package com.example.kutumbreadsms.data.source.db

import com.example.android.architecture.blueprints.todoapp.TaskScereen.FilterType
import com.example.android.architecture.blueprints.todoapp.data.source.local.TaskLocal
import com.example.android.architecture.blueprints.todoapp.data.source.local.TaskLocalDataSource
import com.example.truecreditslist.db.PrDao
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class RoomDataSource internal constructor(
    private val prDao: PrDao,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO ) : TaskLocalDataSource{

    override fun getTasks(filterType: FilterType): Flow<List<TaskLocal>> {
        return prDao.allTasks(filterType.queryParams)
    }

    override suspend fun updateTask(taskLocal: TaskLocal) {
        prDao.insert(taskLocal)
    }

    override suspend fun addTask(taskLocal: TaskLocal) {
        prDao.insert(taskLocal)
    }

    override suspend fun deleteAllTasks() = withContext(ioDispatcher){
        prDao.deletePrs()
    }

    override suspend fun insertTasks(prList: List<TaskLocal>) = withContext(ioDispatcher){
        prDao.insertAll(prList)
    }

}