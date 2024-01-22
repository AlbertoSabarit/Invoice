package com.murray.invoicemodule.ui.usecase

sealed class InvoiceCreateState {
    object IncorrectDateRangeError: InvoiceCreateState()
    data object DataIniEmptyError: InvoiceCreateState()
    data object DataFinEmptyError: InvoiceCreateState()
    data class InvoiceCreateError(var message:String): InvoiceCreateState()
    data object Success : InvoiceCreateState()
    data class InvoiceExist(var message: String): InvoiceCreateState()
    data class Loading(var value:Boolean): InvoiceCreateState()
}