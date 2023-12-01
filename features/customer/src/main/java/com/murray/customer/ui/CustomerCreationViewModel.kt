package com.murray.customer.ui

import android.text.TextUtils
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class CustomerCreationViewModel : ViewModel() {

    var name = MutableLiveData<String>()
    var email = MutableLiveData<String>()

    private var state = MutableLiveData<CustomerCreationState>()

    fun validateCustomer() {

        when {
            TextUtils.isEmpty(name.value) -> state.value = CustomerCreationState.NameEmptyError
            TextUtils.isEmpty(email.value) -> state.value = CustomerCreationState.EmailEmptyError
            else -> state.value = CustomerCreationState.Success
        }

    }
    fun getState(): LiveData<CustomerCreationState> {
        return state
    }
}