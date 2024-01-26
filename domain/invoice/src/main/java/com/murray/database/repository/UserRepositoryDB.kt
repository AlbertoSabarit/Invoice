package com.murray.database.repository

import android.database.sqlite.SQLiteConstraintException
import com.murray.data.accounts.User
import com.murray.database.InvoiceDatabase
import kotlinx.coroutines.flow.Flow

class UserRepositoryDB {
    fun getUserList(): Flow<List<User>> {
        return InvoiceDatabase.getInstance().userDao().selectAll()
    }

    fun delete(user: User){
        InvoiceDatabase.getInstance().userDao().delete(user)
    }

    companion object {
        fun insert(user: User) : UserState{

            try{
                InvoiceDatabase.getInstance().userDao().insert(user)
                return UserState.Success
            }catch(e:SQLiteConstraintException){
                return UserState.InsertError("Error")
            }
        }
    }

}