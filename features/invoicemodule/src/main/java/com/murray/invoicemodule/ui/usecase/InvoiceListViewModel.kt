package com.murray.invoicemodule.ui.usecase

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.murray.data.invoices.Invoice
import com.murray.data.invoices.LineItems
import com.murray.database.repository.InvoiceRepositoryDB
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class InvoiceListViewModel :ViewModel(){
    private var state = MutableLiveData<InvoiceListState>()
    var invoiceRepository = InvoiceRepositoryDB()
    var allInvoice: LiveData<List<Invoice>> = invoiceRepository.getInvoiceList().asLiveData()
    fun getState(): LiveData<InvoiceListState> {
        return state
    }
    fun getInvoiceList() {
        when {
            allInvoice.value?.isEmpty() == true -> state.value = InvoiceListState.NoDataError
            else -> state.value = InvoiceListState.Success
        }
    }
    fun getLineItemsForInvoice(invoiceId: Int): Flow<List<LineItems>> {
        return invoiceRepository.getLineItemsInvoice(invoiceId)
    }
    fun delete(invoice: Invoice) {
        viewModelScope.launch(Dispatchers.IO) {
            invoiceRepository.delete(invoice)
        }
    }
}