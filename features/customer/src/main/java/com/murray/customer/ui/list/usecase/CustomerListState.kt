package com.murray.customer.ui.list.usecase

import com.murray.customer.ui.creation.usecase.CustomerCreationState
import com.murray.entities.customers.Customer

sealed class CustomerListState {
    data object IdReferenciado: CustomerListState() //TODO cambiar nombre e implementar

    data object NoDataError: CustomerListState()
    data class Success(val dataset: ArrayList<Customer>): CustomerListState()
}