package com.murray.customer.ui.list.usecase

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.murray.customer.ui.adapter.CustomAdapter
import com.murray.entities.customers.Customer
import com.murray.network.ResourceList
import com.murray.repositories.CustomerRepository
import com.murray.repositories.InvoiceRepository
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
    fun deleteItem(customer: Customer, customerAdapter: CustomAdapter): Boolean {
        var encontrado: Boolean = false
        if (InvoiceRepository.dataSet.isNotEmpty()) {
            for (i in InvoiceRepository.dataSet) {
                /* Debería implementarse este método, pero invoice no guarda estancias de customer
            if(i.cliente.id == customer.id)
                encontrado = true
             */
                if (i.cliente.equals(customer.name))
                    encontrado = true
            }
        }
        if(!encontrado){
            CustomerRepository.getCustomers().remove(customer)
            customerAdapter.update(CustomerRepository.getCustomers() as ArrayList<Customer>)
        }
        else
            state.value = CustomerListState.IdReferenciado

        return CustomerRepository.getCustomers().isEmpty()
    }

}