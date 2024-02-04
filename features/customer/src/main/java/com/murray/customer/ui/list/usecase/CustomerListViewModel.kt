package com.murray.customer.ui.list.usecase

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.murray.customer.ui.adapter.CustomAdapter
import com.murray.data.customers.Customer
import com.murray.data.tasks.Task
import com.murray.database.repository.CustomerRepositoryDB
import com.murray.database.repository.TaskRepositoryDB
import com.murray.entities.invoices.Invoice
import com.murray.networkstate.ResourceList
import com.murray.repositories.CustomerRepository
import com.murray.repositories.InvoiceRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CustomerListViewModel: ViewModel() {

    private var state = MutableLiveData<CustomerListState>()
    var customerRepository = CustomerRepositoryDB()
    var allCustomers: LiveData<List<Customer>> = customerRepository.getCustomerList().asLiveData()

    fun getState(): LiveData<CustomerListState> {
        return state
    }

    fun getCustomerList() {
        when {
            allCustomers.value?.isEmpty() == true -> state.value = CustomerListState.NoDataError
            else -> state.value = CustomerListState.Success
        }
    }

    fun deleteItem(customer: Customer) {
        viewModelScope.launch(Dispatchers.IO) {
            customerRepository.delete(customer)
        }
    }
}