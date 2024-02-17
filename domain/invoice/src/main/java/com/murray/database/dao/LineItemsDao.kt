package com.murray.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.murray.data.invoices.Invoice
import com.murray.data.invoices.LineItems
import kotlinx.coroutines.flow.Flow

@Dao
interface LineItemsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE) //(onConflict = ForeignKey.RESTRICT)
    fun insert(linesItem: LineItems): Long

    @Query("SELECT * FROM line_items")
    fun selectAll(): Flow<List<LineItems>>

    @Query("SELECT * FROM line_items WHERE id = :linesItemId")
    fun selectById(linesItemId: Int): LineItems

    @Update
    fun update(linesItem: LineItems)

    @Delete
    fun delete(linesItem: LineItems)
}