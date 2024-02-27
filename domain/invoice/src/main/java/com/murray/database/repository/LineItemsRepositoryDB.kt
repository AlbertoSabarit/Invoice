package com.murray.database.repository

import android.database.sqlite.SQLiteException
import com.murray.data.invoices.Invoice
import com.murray.data.invoices.LineItems
import com.murray.data.tasks.Task
import com.murray.database.InvoiceDatabase
import com.murray.networkstate.Resource
import kotlinx.coroutines.flow.Flow

class LineItemsRepositoryDB {
    fun getLineItemsList(): Flow<List<LineItems>> {
        return InvoiceDatabase.getInstance().lineitemsDao().selectAll()
    }
    fun insert(lineItems: LineItems, invoiceId: Int): Resource {
        return try {
            lineItems.invoiceId = invoiceId
            val invoiceId = InvoiceDatabase.getInstance().lineitemsDao().insert(lineItems)
            Resource.Success(invoiceId)
        } catch (e: SQLiteException) {
            Resource.Error(e)
        }
    }

    fun insert2(lineItems: LineItems): Resource {
        return try {
            val lineItemsId = InvoiceDatabase.getInstance().lineitemsDao().insert(lineItems)
            Resource.Success(lineItemsId)
        } catch (e: SQLiteException) {
            Resource.Error(e)
        }
    }
    fun update(lineItems: LineItems) {
        InvoiceDatabase.getInstance().lineitemsDao().update(lineItems)
    }
    fun delete(lineItems: LineItems) {
        InvoiceDatabase.getInstance().lineitemsDao().delete(lineItems)
    }

    fun deleteLineItemsForInvoice(invoiceId: Int): Resource {
        return try {
            InvoiceDatabase.getInstance().lineitemsDao().deleteLineItemsForInvoice(invoiceId)
            Resource.Success(Unit)
        } catch (e: SQLiteException) {
            Resource.Error(e)
        }
    }
}