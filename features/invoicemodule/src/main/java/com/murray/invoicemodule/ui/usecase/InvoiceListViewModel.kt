package com.murray.invoicemodule.ui.usecase

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.murray.entities.invoices.Invoice
import com.murray.entities.tasks.Task
import com.murray.network.ResourceList
import com.murray.repositories.InvoiceRepository
import com.murray.repositories.TaskRepository
import com.murray.task.ui.usecase.TaskListState
import kotlinx.coroutines.launch

class InvoiceListViewModel :ViewModel(){
    private var state = MutableLiveData<InvoiceListState>()

    fun getState(): LiveData<InvoiceListState> {
        return state
    }

    fun getInvocieList() {
        viewModelScope.launch {

            state.value = InvoiceListState.Loading(true)
            val result = InvoiceRepository.getInvoiceList() // val result = TaskRepository.dataSet
            state.value = InvoiceListState.Loading(false)


            when (result) {
                is ResourceList.Success<*> -> state.value = InvoiceListState.Success(result.data as ArrayList<Invoice>)

                is ResourceList.Error -> state.value = InvoiceListState.NoDataError
            }
        }
    }
}