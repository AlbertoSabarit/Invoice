package com.murray.database.repository

import android.database.sqlite.SQLiteException
import com.murray.data.invoices.Invoice
import com.murray.database.InvoiceDatabase
import com.murray.networkstate.Resource
import kotlinx.coroutines.flow.Flow

class InvoiceRepositoryDB {
    fun getInvoiceList(): Flow<List<Invoice>> {
        return InvoiceDatabase.getInstance().invoiceDao().selectAll()
    }

    fun insert(invoice: Invoice): Resource {
        return try {
            val invoiceId = InvoiceDatabase.getInstance().invoiceDao().insert(invoice)
            Resource.Success(invoiceId)
        } catch (e: SQLiteException) {
            Resource.Error(e)
        }
    }

    fun update(invoice: Invoice) {
        InvoiceDatabase.getInstance().invoiceDao().update(invoice)
    }

    fun delete(invoice: Invoice) {
        InvoiceDatabase.getInstance().invoiceDao().delete(invoice)
    }
}