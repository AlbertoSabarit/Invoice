package com.murray.database.repository

import android.database.sqlite.SQLiteException
import com.murray.data.customers.Customer
import com.murray.database.InvoiceDatabase
import com.murray.networkstate.Resource
import kotlinx.coroutines.flow.Flow

class CustomerRepositoryDB {

    fun getCustomerList(): Flow<List<Customer>> {
        return InvoiceDatabase.getInstance().customerDao().selectAll()
    }

    fun insert(customer: Customer): Resource {
        try {
            InvoiceDatabase.getInstance().customerDao().insert(customer)
        } catch (e: SQLiteException) {
            return Resource.Error(e)
        }
        return Resource.Success(customer)
    }

    fun delete(customer: Customer) {
        InvoiceDatabase.getInstance().customerDao().delete(customer)
    }
}