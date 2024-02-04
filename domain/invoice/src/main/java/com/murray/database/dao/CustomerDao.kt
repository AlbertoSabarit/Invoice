package com.murray.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.ForeignKey.Companion.RESTRICT
import androidx.room.Insert
import androidx.room.Query
import com.murray.data.customers.Customer
import kotlinx.coroutines.flow.Flow

@Dao
interface CustomerDao {
    @Insert(onConflict = RESTRICT)    //@Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(customer: Customer): Long

    @Query("SELECT * FROM customer ORDER BY name")
    fun selectAll(): Flow<List<Customer>>

    @Query("SELECT * FROM customer WHERE id = :customerId")
    fun selectById(customerId: Int): Customer

    @Delete
    fun delete(customer: Customer)
}