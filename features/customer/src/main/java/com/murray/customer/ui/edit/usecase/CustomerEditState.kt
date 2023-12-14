package com.murray.customer.ui.edit.usecase

sealed class CustomerEditState {
    data object NonExistentContact: CustomerEditState()
    data object EmailFormatError: CustomerEditState()
    data object PhoneFormatError: CustomerEditState()
    data object NameIsMandatory: CustomerEditState()
    data object Success: CustomerEditState()
}