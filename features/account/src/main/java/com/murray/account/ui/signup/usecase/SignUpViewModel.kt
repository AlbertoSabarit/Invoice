package com.murray.account.ui.signup.usecase

import android.icu.text.SimpleDateFormat
import android.net.ParseException
import android.text.TextUtils
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.util.Locale

class SignUpViewModel : ViewModel() {

    var email = MutableLiveData<String>()
    var password = MutableLiveData<String>()
    var password2 = MutableLiveData<String>()

    var state = MutableLiveData<SignUpState>()

    fun validateCredentials() {

        when {
            TextUtils.isEmpty(email.value) -> state.value = SignUpState.EmailEmptyError
            TextUtils.isEmpty(password.value) -> state.value = SignUpState.PasswordEmptyError
            TextUtils.isEmpty(password2.value) -> state.value = SignUpState.PasswordEmptyError2
            !isEqualPasswords(password.value!!, password2.value!!) -> state.value = SignUpState.PasswordsNotEquals
            else -> state.value = SignUpState.Success
        }

    }

    private fun isEqualPasswords(pass1: String, pass2: String): Boolean {
        return pass1 == pass2
    }

    fun getState(): LiveData<SignUpState> {
        return state
    }
}