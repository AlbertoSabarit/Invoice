package com.murray.customer.ui.edit.usecase

import android.text.TextUtils
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.murray.customer.ui.adapter.CustomAdapter
import com.murray.customer.ui.creation.usecase.CustomerCreationState
import com.murray.entities.customers.Customer
import com.murray.entities.email.Email
import com.murray.repositories.CustomerRepository
import java.util.regex.Pattern

class CustomerEditViewModel : ViewModel() {
    var name = MutableLiveData<String>()
    var email = MutableLiveData<String>()
    var phone = MutableLiveData<String>()
    var city = MutableLiveData<String>()
    var address = MutableLiveData<String>()
    var id = 0

    private var state = MutableLiveData<CustomerEditState>()

    fun validateCustomer() {

        when {
            TextUtils.isEmpty(name.value) -> state.value = CustomerEditState.NameIsMandatory
            TextUtils.isEmpty(email.value) -> state.value = CustomerEditState.NonExistentContact
            !validateEmail(email.value) -> state.value = CustomerEditState.EmailFormatError
            !validatePhone(phone.value) -> state.value = CustomerEditState.PhoneFormatError
            else -> success()
        }
    }
    fun success(){
        if(phone.value =="")
            phone.value = null
        CustomerRepository.updateCustomer(id, name.value!!, email.value!!, phone.value?.toInt() ?: null, city.value, address.value)
        state.value = CustomerEditState.Success
    }

    fun validateEmail(value: String?): Boolean{
        val pattern: Pattern = Pattern.compile("^\\S+@\\S+\\.\\S+")
        return pattern.matcher(value).matches()
    }

    fun getState(): LiveData<CustomerEditState> {
        return state
    }

    fun validatePhone(value: String?): Boolean{
        val pattern: Pattern = Pattern.compile("\\d+")
        return when (value) {
            null -> true
            "" -> true
            else -> pattern.matcher(value).matches()
        }
    }
}