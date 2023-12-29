package com.murray.account.ui.signup.usecase

import android.icu.text.SimpleDateFormat
import android.net.ParseException
import android.text.TextUtils
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.murray.entities.accounts.AccountException
import com.murray.entities.accounts.User
import com.murray.network.Resource
import com.murray.repositories.UserRepository
import kotlinx.coroutines.launch
import java.util.Locale
import java.util.regex.Pattern

class SignUpViewModel : ViewModel() {

    var email = MutableLiveData<String>()
    var password = MutableLiveData<String>()
    var password2 = MutableLiveData<String>()

    var state = MutableLiveData<SignUpState>()

    fun getState(): LiveData<SignUpState> {
        return state
    }


    fun validateCredentials(user: User) {

        when {
            TextUtils.isEmpty(email.value) -> state.value = SignUpState.EmailEmptyError
            !validateEmail (email.value!!) -> state.value =  SignUpState.InvalidFormat
            TextUtils.isEmpty(password.value) -> state.value = SignUpState.PasswordEmptyError
            TextUtils.isEmpty(password2.value) -> state.value = SignUpState.PasswordEmptyError2
            !isEqualPasswords(password.value!!, password2.value!!) -> state.value = SignUpState.PasswordsNotEquals

            else -> {
                viewModelScope.launch {
                    state.value = SignUpState.Loading(true)
                    val result = UserRepository.createUser(user)
                    state.value = SignUpState.Loading(false)

                    when (result) {
                        is Resource.Error -> state.value = SignUpState.UserExist(result.exception.message!!)
                        is Resource.Success<*> ->  state.value = SignUpState.Success
                    }
                }
            }
        }

    }
    private fun isEqualPasswords(pass1: String, pass2: String): Boolean {
        return pass1 == pass2
    }
    fun validateEmail(value: String) : Boolean {
        val pattern = Pattern.compile("^\\S+@\\S+\\.\\S+$")

        return pattern.matcher(value).matches()
    }

}