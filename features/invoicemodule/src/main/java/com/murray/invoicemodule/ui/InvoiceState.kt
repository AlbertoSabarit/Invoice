package com.murray.invoicemodule.ui

import com.murray.entities.invoices.Invoice

sealed class InvoiceState {
    object EmailEmptyError : InvoiceState()
    data object EmailFormatError: InvoiceState()
    data object PasswordEmptyError: InvoiceState()
    data object PasswordFormatError: InvoiceState()
    data class AuthencationError(var message:String): InvoiceState()
    data class Success(var account: Invoice) : InvoiceState()
    //se debe crear una clase que contiene un valor booleano que indica se muestra el
    data class Loading(var value:Boolean):InvoiceState()
}