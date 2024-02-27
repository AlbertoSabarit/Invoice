package com.murray.database.repository

import android.database.sqlite.SQLiteException
import com.murray.data.invoices.Invoice
import com.murray.data.invoices.LineItems
import com.murray.database.InvoiceDatabase
import com.murray.networkstate.Resource
import kotlinx.coroutines.flow.Flow

class InvoiceRepositoryDB {
    fun getInvoiceList(): Flow<List<Invoice>> {
        return InvoiceDatabase.getInstance().invoiceDao().selectAll()
    }
    fun insert(invoice: Invoice): Long {
        return try {
            val invoiceId = InvoiceDatabase.getInstance().invoiceDao().insert(invoice)
            invoiceId
        } catch (e: SQLiteException) {
            -1
        }
    }
    fun getLineItemsInvoice(invoiceId: Int): Flow<List<LineItems>> {
        return InvoiceDatabase.getInstance().invoiceDao().getLineItems(invoiceId)
    }

    fun update(invoice: Invoice) {
        InvoiceDatabase.getInstance().invoiceDao().update(invoice)
    }
    fun delete(invoice: Invoice) {
        InvoiceDatabase.getInstance().invoiceDao().delete(invoice)
    }
}