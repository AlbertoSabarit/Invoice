package com.murray.customer.ui.creation.usecase

import android.text.TextUtils
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.murray.data.accounts.Email
import com.murray.data.customers.Customer
import com.murray.database.repository.CustomerRepositoryDB
import com.murray.networkstate.Resource
import com.murray.repositories.CustomerRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.regex.Pattern

class CustomerCreationViewModel : ViewModel() {

    var name = MutableLiveData<String>()
    var email = MutableLiveData<String>()
    var phone = MutableLiveData<String>()
    var city = MutableLiveData<String>()
    var address = MutableLiveData<String>()


    private var state = MutableLiveData<CustomerCreationState>()
    var customerRepository = CustomerRepositoryDB()

    fun getState(): LiveData<CustomerCreationState> {
        return state
    }

    fun validateCustomer(customer: Customer) {

        when {
            TextUtils.isEmpty(name.value) -> state.value = CustomerCreationState.NameIsMandatory
            TextUtils.isEmpty(email.value) -> state.value = CustomerCreationState.NonExistentContact
            !validateEmail(email.value) -> state.value = CustomerCreationState.EmailFormatError
            !validatePhone(phone.value) -> state.value = CustomerCreationState.PhoneFormatError
            else -> {
                //state.postValue(TaskCreateState.Loading(true))
                viewModelScope.launch(Dispatchers.IO) {
                    val result = customerRepository.insert(customer)
                    withContext(Dispatchers.Main) {
                        //state.postValue(TaskCreateState.Loading(false))
                    }

                    when (result) {
                        is Resource.Error -> {
                            withContext(Dispatchers.Main) {
                                state.value = CustomerCreationState.NonExistentContact
                            }
                        }

                        is Resource.Success<*> -> {
                            withContext(Dispatchers.Main) {
                                state.value = CustomerCreationState.Success
                            }
                        }
                    }

                }
            }
        }
    }
    fun validateEmail(value: String?): Boolean{
        val pattern: Pattern = Pattern.compile("^\\S+@\\S+\\.\\S+")
        return pattern.matcher(value).matches()
    }


    fun validatePhone(value: String?): Boolean{
        val pattern: Pattern = Pattern.compile("\\d+")
        return when (value) {
            null -> return true
            else -> pattern.matcher(value).matches()
        }
    }
}