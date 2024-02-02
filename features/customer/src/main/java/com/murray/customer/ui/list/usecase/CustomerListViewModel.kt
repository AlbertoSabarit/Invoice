package com.murray.customer.ui.list.usecase

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.murray.customer.ui.adapter.CustomAdapter
import com.murray.data.customers.Customer
import com.murray.database.repository.CustomerRepositoryDB
import com.murray.entities.invoices.Invoice
import com.murray.networkstate.ResourceList
import com.murray.repositories.CustomerRepository
import com.murray.repositories.InvoiceRepository
import kotlinx.coroutines.launch
import androidx.lifecycle.asLiveData
import kotlinx.coroutines.Dispatchers

class CustomerListViewModel: ViewModel() {
    private var state = MutableLiveData<CustomerListState>()
    var customerRepository = CustomerRepositoryDB()
    var allCustomer: LiveData<List<Customer>> = customerRepository.getCustomerList().asLiveData()

    fun getState(): LiveData<CustomerListState> {
        return state
    }

    fun getCustomerList(){
        when {
            allCustomer.value?.isEmpty() == true -> state.value = CustomerListState.NoDataError
            else -> state.value = CustomerListState.Success
        }
    }
    fun delete(customer: Customer) {
        viewModelScope.launch(Dispatchers.IO) {
            customerRepository.delete(customer)
        }
    }
    fun getCustomerListOrderByName() {
        viewModelScope.launch {

            state.value = CustomerListState.Loading(true)
            val result = CustomerRepository.getDataSetCustomer()
            state.value = CustomerListState.Loading(false)

            when (result) {
                is ResourceList.NoData -> state.value = CustomerListState.NoDataError
                is ResourceList.Success<*> -> {
                    (result.data as ArrayList<Customer>).sortBy { it.name }
                    state.value = CustomerListState.Success
                }
            }
        }
    }
    fun getCustomerListOrderByEmail() {
        viewModelScope.launch {

            state.value = CustomerListState.Loading(true)
            val result = CustomerRepository.getDataSetCustomer()
            state.value = CustomerListState.Loading(false)

            when (result) {
                is ResourceList.NoData -> state.value = CustomerListState.NoDataError
                is ResourceList.Success<*> -> {
                    (result.data as ArrayList<Customer>).sortBy { it.email.value }
                    state.value = CustomerListState.Success
                }
            }
        }
    }

}