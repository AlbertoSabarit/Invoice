package com.murray.customer.ui

sealed class CustomerCreationState {
    data object EmailEmptyError: CustomerCreationState()
    data object NameEmptyError: CustomerCreationState()
    data object Success: CustomerCreationState()
}