package com.murray.database.repository

import android.database.sqlite.SQLiteConstraintException
import com.murray.data.accounts.User
import com.murray.database.InvoiceDatabase
import com.murray.networkstate.Resource
import kotlinx.coroutines.flow.Flow

class UserRepositoryDB {
    fun getUserList(): Flow<List<User>> {
        return InvoiceDatabase.getInstance().userDao().selectAll()
    }

    fun insert(user: User): Resource {
        try {
            InvoiceDatabase.getInstance().userDao().insert(user)
        } catch (e: SQLiteConstraintException) {
            return Resource.Error(e)
        }
        return Resource.Success(user)
    }

    fun delete(user: User) {
        InvoiceDatabase.getInstance().userDao().delete(user)
    }

}