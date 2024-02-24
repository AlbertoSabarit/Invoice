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

    fun insert2(invoice: Invoice): Long { // Cambiar el tipo de retorno a Long para representar el ID de la factura
        return try {
            val invoiceId = InvoiceDatabase.getInstance().invoiceDao().insert(invoice)
            invoiceId // Devolver el ID de la factura insertada
        } catch (e: SQLiteException) {
            -1 // Devolver un valor negativo para indicar un error
        }
    }
    fun update(invoice: Invoice) {
        InvoiceDatabase.getInstance().invoiceDao().update(invoice)
    }

    fun delete(invoice: Invoice) {
        InvoiceDatabase.getInstance().invoiceDao().delete(invoice)
    }
}