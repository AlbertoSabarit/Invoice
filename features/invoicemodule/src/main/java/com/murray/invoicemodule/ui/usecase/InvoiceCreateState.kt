package com.murray.invoicemodule.ui.usecase

import com.murray.entities.invoices.Invoice

sealed class InvoiceCreateState {
    object IncorrectDateRangeError: InvoiceCreateState()
    data object DataIniEmptyError: InvoiceCreateState()
    data object DataFinEmptyError: InvoiceCreateState()
    data class InvoiceCreateError(var message:String): InvoiceCreateState()
    data class Success(var invoice: Invoice) : InvoiceCreateState()
    data class Loading(var value:Boolean): InvoiceCreateState()
}