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
interface InvoiceDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE) //(onConflict = ForeignKey.RESTRICT)
    fun insert(invoice: Invoice): Long

    @Query("SELECT * FROM invoice")
    fun selectAll(): Flow<List<Invoice>>

    @Query("SELECT * FROM invoice WHERE id = :invoiceId")
    fun selectById(invoiceId: Int): Invoice

    @Query("SELECT * FROM line_items WHERE invoice_id = :invoiceId")
    fun getLineItems(invoiceId: Int): Flow<List<LineItems>>

    @Update
    fun update(invoice: Invoice)

    @Delete
    fun delete(invoice: Invoice)
}