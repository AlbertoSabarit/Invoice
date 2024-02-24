package com.murray.invoicemodule.ui.usecase


sealed class InvoiceListState {
    data object NoDataError: InvoiceListState()
    data object Success : InvoiceListState()
    data class  Loading (val value :  Boolean): InvoiceListState()
}