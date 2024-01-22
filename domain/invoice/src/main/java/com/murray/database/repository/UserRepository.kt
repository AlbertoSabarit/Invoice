package com.murray.database.repository

import com.murray.data.accounts.User
import com.murray.database.InvoiceDatabase

class UserRepository {

    fun insert(user: User){
        InvoiceDatabase.getInstance()?.userDao()?.insert(user)
    }
}