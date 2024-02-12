package com.murray.invoicemodule.ui.usecase

import com.murray.data.invoices.Invoice

sealed class InvoiceListState {
    data object NoDataError: InvoiceListState()
    data class Success (val dataset: ArrayList<Invoice>) : InvoiceListState()
    data class  Loading (val value :  Boolean): InvoiceListState()
}