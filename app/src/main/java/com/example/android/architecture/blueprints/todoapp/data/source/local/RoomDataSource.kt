package com.example.kutumbreadsms.data.source.db

import com.example.android.architecture.blueprints.todoapp.data.source.local.TaskLocal
import com.example.android.architecture.blueprints.todoapp.data.source.local.TaskLocalDataSource
import com.example.android.architecture.blueprints.todoapp.screens.tasklist.FilterType
import com.example.truecreditslist.db.PrDao
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class RoomDataSource internal constructor(
    private val prDao: PrDao,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : TaskLocalDataSource {

    override fun getTasks(filterType: FilterType): Flow<List<TaskLocal>> {
        return prDao.allTasks(filterType.queryParams)
    }

    override fun getTask(taskId: Int): Flow<TaskLocal> {
        return prDao.getTask(taskId)
    }

    override suspend fun deleteTask(taskId: Int) {
        prDao.deleteTask(taskId)
    }

    override suspend fun updateTask(taskLocal: TaskLocal) {
        prDao.insert(taskLocal)
    }

    override suspend fun addTask(taskLocal: TaskLocal) {
        prDao.insert(taskLocal)
    }

    override suspend fun getRemoteId(localId: Int): Int {
        return prDao.getRemoteId(localId)
    }

    override suspend fun deleteAllTasks() = withContext(ioDispatcher) {
        prDao.deletePrs()
    }

    override suspend fun insertTasks(prList: List<TaskLocal>) = withContext(ioDispatcher) {
        prDao.insertAll(prList)
    }

}