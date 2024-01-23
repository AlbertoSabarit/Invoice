package com.murray.database.repository

import com.murray.data.accounts.User
import com.murray.database.InvoiceDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.concurrent.Flow

class UserRepositoryDB {

    companion object{
        fun insert(user: User){
            InvoiceDatabase.getInstance()?.userDao()?.insert(user)
        }

        /*fun getUserList(): Flow<List<User>> {
            InvoiceDatabase.getInstance()?.userDao()?.selectAll()
        }*/
    }

}