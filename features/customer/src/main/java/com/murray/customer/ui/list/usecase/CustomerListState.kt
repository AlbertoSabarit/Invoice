package com.murray.customer.ui.list.usecase

import com.murray.entities.customers.Customer

sealed class CustomerListState {
    data object NoDataError: CustomerListState()
    data class Success(val dataset: ArrayList<Customer>): CustomerListState()
}