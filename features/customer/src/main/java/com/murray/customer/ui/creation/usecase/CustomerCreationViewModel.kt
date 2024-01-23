package com.murray.customer.ui.creation.usecase

import android.text.TextUtils
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.murray.data.accounts.Email
import com.murray.data.customers.Customer
import com.murray.repositories.CustomerRepository
import java.util.regex.Pattern

class CustomerCreationViewModel : ViewModel() {

    var name = MutableLiveData<String>()
    var email = MutableLiveData<String>()
    var phone = MutableLiveData<String>()
    var city = MutableLiveData<String>()
    var address = MutableLiveData<String>()


    private var state = MutableLiveData<CustomerCreationState>()

    fun validateCustomer() {

        when {
            TextUtils.isEmpty(name.value) -> state.value = CustomerCreationState.NameIsMandatory
            TextUtils.isEmpty(email.value) -> state.value = CustomerCreationState.NonExistentContact
            !validateEmail(email.value) -> state.value = CustomerCreationState.EmailFormatError
            !validatePhone(phone.value) -> state.value = CustomerCreationState.PhoneFormatError
            else -> success()
        }
    }
    fun success(){
        CustomerRepository.addCustomer(Customer(CustomerRepository.getNextId(), name.value!!, Email(email.value!!), phone.value?.toInt() ?: null, city.value, address.value))
        state.value = CustomerCreationState.Success
    }

    fun validateEmail(value: String?): Boolean{
        val pattern: Pattern = Pattern.compile("^\\S+@\\S+\\.\\S+")
        return pattern.matcher(value).matches()
    }

    fun getState(): LiveData<CustomerCreationState> {
        return state
    }
    fun validatePhone(value: String?): Boolean{
        val pattern: Pattern = Pattern.compile("\\d+")
        return when (value) {
            null -> return true
            else -> pattern.matcher(value).matches()
        }
    }
}