package com.murray.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.ForeignKey
import androidx.room.ForeignKey.Companion.RESTRICT
import androidx.room.Insert
import androidx.room.OnConflictStrategy.Companion.REPLACE
import androidx.room.Query
import com.murray.data.customers.Customer
import com.murray.data.items.Item
import kotlinx.coroutines.flow.Flow

@Dao
interface ItemDao {

    @Insert(onConflict = RESTRICT)
    fun insert(item: Item) : Long


    @Query ("SELECT * FROM item ORDER BY name")
    fun selectAll(): Flow<List<Item>>

    @Query("SELECT * FROM item WHERE id = :itemId")
    fun selectById(itemId: Int): Item

    @Delete
    fun delete(item: Item)


}