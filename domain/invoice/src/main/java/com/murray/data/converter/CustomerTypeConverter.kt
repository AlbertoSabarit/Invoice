package com.murray.data.converter

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.murray.data.customers.Customer
import com.murray.repositories.CustomerRepository

@ProvidedTypeConverter
class CustomerTypeConverter {

    @TypeConverter
    fun toCustomer(value: Int): Customer {
        val customer = CustomerRepository.getCustomers().firstOrNull { it.id == value }
        return customer ?: throw NoSuchElementException("Cliente no encontrado: $value")
    }

    @TypeConverter
    fun fromCustomer(customer: Customer): Int {
        return customer.id
    }
}
