package com.murray.invoicemodule.ui.usecase

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.murray.entities.invoices.Invoice
import com.murray.network.ResourceList
import com.murray.repositories.InvoiceRepository
import kotlinx.coroutines.launch

class InvoiceListViewModel :ViewModel(){
    private var state = MutableLiveData<InvoiceListState>()
    fun getState(): LiveData<InvoiceListState> {
        return state
    }
    fun getInvocieList() {
        viewModelScope.launch {

            state.value = InvoiceListState.Loading(true)
            val result = InvoiceRepository.getInvoiceList()
            state.value = InvoiceListState.Loading(false)

            when (result) {
                is ResourceList.Success<*> ->{
                    (result.data as ArrayList<Invoice>).sort()
                    state.value = InvoiceListState.Success(result.data as ArrayList<Invoice>)
                }
                is ResourceList.NoData -> state.value = InvoiceListState.NoDataError
            }
        }
    }

    fun removeFromList(invoice: Invoice){
        InvoiceRepository.dataSet.remove(invoice)
    }
}