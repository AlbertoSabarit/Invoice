package com.murray.customer.ui.creation.usecase

import android.text.TextUtils
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.util.regex.Pattern

class CustomerCreationViewModel : ViewModel() {

    var name = MutableLiveData<String>()
    var email = MutableLiveData<String>()

    private var state = MutableLiveData<CustomerCreationState>()

    fun validateCustomer() {

        when {
            TextUtils.isEmpty(name.value) -> state.value = CustomerCreationState.NameIsMandatory
            TextUtils.isEmpty(email.value) -> state.value = CustomerCreationState.NonExistentContact
            !validateEmail(email.value) -> state.value = CustomerCreationState.EmailFormatError
            else -> state.value = CustomerCreationState.Success
        }
    }

    fun validateEmail(value: String?): Boolean{
        val pattern: Pattern = Pattern.compile("^\\S+@\\S+\\.\\S+")
        return pattern.matcher(value).matches()
    }

    fun getState(): LiveData<CustomerCreationState> {
        return state
    }
}