package com.murray.customer.ui.list.usecase

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.murray.data.customers.Customer
import com.murray.database.repository.CustomerRepositoryDB

class CustomerListViewModel: ViewModel() {

    private var state = MutableLiveData<CustomerListState>()
    private var customerRepository = CustomerRepositoryDB()
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

    fun delete(customer: Customer):Boolean {
        return try{
            customerRepository.delete(customer)
            state.value = CustomerListState.Success
            true
        } catch (e: Exception){
            state.value = CustomerListState.ReferencedCustomer
            false
        }
    }
    fun setState(s: CustomerListState){
        state.value = s
    }
}