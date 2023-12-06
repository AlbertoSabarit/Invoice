package com.murray.customer.ui.list.usecase

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.murray.entities.customers.Customer
import com.murray.network.ResourceList
import com.murray.repositories.CustomerRepository
import kotlinx.coroutines.launch

class CustomerListViewModel: ViewModel() {
    private var state = MutableLiveData<CustomerListState>()

    fun getState(): LiveData<CustomerListState> {
        return state
    }

    fun getCustomerList(){
        viewModelScope.launch {
            val result = CustomerRepository.getDataSetCustomer()
            when(result){
                is ResourceList.Success<*> -> state.value = CustomerListState.Success(result.data as ArrayList<Customer>)
                is ResourceList.Error -> state.value = CustomerListState.NoDataError
            }
        }
    }
}