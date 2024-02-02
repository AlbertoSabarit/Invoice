package com.murray.customer.ui.list.usecase

import com.murray.data.customers.Customer

sealed class CustomerListState {
    data object ReferencedCustomer: CustomerListState()
    data object NoDataError: CustomerListState()
    data object Success: CustomerListState()
    data class  Loading (val value :  Boolean): CustomerListState()
}