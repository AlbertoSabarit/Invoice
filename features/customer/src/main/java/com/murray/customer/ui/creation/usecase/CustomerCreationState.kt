package com.murray.customer.ui.creation.usecase

sealed class CustomerCreationState {
    data object NonExistentContact: CustomerCreationState()
    data object EmailFormatError: CustomerCreationState()
    data object PhoneFormatError: CustomerCreationState()
    data object NameIsMandatory: CustomerCreationState()
    data object Success: CustomerCreationState()
    data object Fail: CustomerCreationState() //TODO Error
}