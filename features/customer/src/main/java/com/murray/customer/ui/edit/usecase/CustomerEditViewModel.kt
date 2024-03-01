package com.murray.customer.ui.edit.usecase

import android.text.TextUtils
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.murray.data.accounts.Email
import com.murray.data.customers.Customer
import com.murray.database.repository.CustomerRepositoryDB
import java.util.regex.Pattern

class CustomerEditViewModel : ViewModel() {
    var name = MutableLiveData<String>()
    var email = MutableLiveData<String>()
    var phone = MutableLiveData<String>()
    var city = MutableLiveData<String>()
    var address = MutableLiveData<String>()
    var id = 0
    var customerRepositoryDB = CustomerRepositoryDB()

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
    private fun success(){
        var phoneTmp = phone.value
        if (phone.value.equals(""))
            phoneTmp = null
        var customer = Customer(name.value!!, Email(email.value!!), phoneTmp?.toInt(), city.value, address.value)
        customer.id = id
        customerRepositoryDB.update(customer)
        state.value = CustomerEditState.Success
    }

    private fun validateEmail(value: String?): Boolean{
        val pattern: Pattern = Pattern.compile("^\\S+@\\S+\\.\\S+")
        return pattern.matcher(value).matches()
    }

    fun getState(): LiveData<CustomerEditState> {
        return state
    }

    private fun validatePhone(value: String?): Boolean{
        val pattern: Pattern = Pattern.compile("\\d+")
        return when (value) {
            null -> true
            "" -> true
            else -> pattern.matcher(value).matches()
        }
    }
}