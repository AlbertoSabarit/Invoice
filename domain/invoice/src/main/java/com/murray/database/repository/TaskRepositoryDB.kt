package com.murray.database.repository

import android.database.sqlite.SQLiteException
import com.murray.data.accounts.User
import com.murray.data.tasks.Task
import com.murray.database.InvoiceDatabase
import com.murray.networkstate.Resource
import kotlinx.coroutines.flow.Flow

class TaskRepositoryDB {

    fun getTaskList(): Flow<List<Task>> {
        return InvoiceDatabase.getInstance().taskDao().selectAll()
    }

    fun insert(task: Task): Resource {
        return try {
            val taskId = InvoiceDatabase.getInstance().taskDao().insert(task)
            Resource.Success(taskId)
        } catch (e: SQLiteException) {
            Resource.Error(e)
        }
    }

    fun delete(task: Task) {
        InvoiceDatabase.getInstance().taskDao().delete(task)
    }
}