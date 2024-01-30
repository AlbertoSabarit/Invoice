package com.murray.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.ForeignKey
import androidx.room.Insert
import androidx.room.Query
import com.murray.data.customers.Customer
import kotlinx.coroutines.flow.Flow

@Dao
interface CustomerDao {
    @Insert(onConflict = ForeignKey.RESTRICT)   //@Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(customer: Customer): Long

    @Query("SELECT * FROM customer ORDER BY name")
    fun selectAll(): Flow<List<Customer>>

    @Delete
    fun delete(customer: Customer)
}