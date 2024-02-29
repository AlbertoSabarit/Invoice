package com.murray.customer.ui.list.usecase

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.murray.data.customers.Customer
import com.murray.data.invoices.Invoice
import com.murray.database.repository.CustomerRepositoryDB
import com.murray.database.repository.InvoiceRepositoryDB


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

    fun deleteItem(customer: Customer):Boolean {
        try{
            customerRepository.delete(customer)
            state.value = CustomerListState.Success
            return true
        } catch (e: Exception){
            state.value = CustomerListState.ReferencedCustomer
            return false
        }
    }
    fun setState(s: CustomerListState){
        state.value = s
    }
}