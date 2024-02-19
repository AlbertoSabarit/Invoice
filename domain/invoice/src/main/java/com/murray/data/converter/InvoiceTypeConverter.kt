package com.murray.data.converter

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.murray.data.invoices.Invoice
import com.murray.database.InvoiceDatabase

@ProvidedTypeConverter
class InvoiceTypeConverter {
    @TypeConverter
    fun toInvoice(value: Int): Invoice {
        val item = InvoiceDatabase.getInstance().invoiceDao().selectById(value)
        return item
    }
    @TypeConverter
    fun fromInvoice(invoice: Invoice): Int {
        return invoice.id
    }
}