package com.murray.data.converter

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.murray.data.customers.Customer
import com.murray.database.InvoiceDatabase
import com.murray.repositories.CustomerRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull

@ProvidedTypeConverter
class CustomerTypeConverter {

    @TypeConverter
    fun toCustomer(value: Int): Customer {
        val customer = InvoiceDatabase.getInstance().customerDao().selectById(value)
        return customer
    }

    @TypeConverter
    fun fromCustomer(customer: Customer): Int {
        return customer.id
    }
}
