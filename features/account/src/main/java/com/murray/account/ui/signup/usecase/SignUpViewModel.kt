package com.murray.account.ui.signup.usecase

import android.text.TextUtils
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.murray.data.accounts.User
import com.murray.database.repository.UserRepositoryDB
import com.murray.networkstate.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.regex.Pattern

class SignUpViewModel : ViewModel() {

    var nombre = MutableLiveData<String>()
    var apellido = MutableLiveData<String>()
    var email = MutableLiveData<String>()
    var password = MutableLiveData<String>()
    var password2 = MutableLiveData<String>()

    var state = MutableLiveData<SignUpState>()

    var userRepository = UserRepositoryDB()

    fun getState(): LiveData<SignUpState> {
        return state
    }


    fun validateCredentials(user: User) {
        when {
            TextUtils.isEmpty(nombre.value) -> state.value = SignUpState.NombreEmpty
            TextUtils.isEmpty(apellido.value) -> state.value = SignUpState.ApellidoEmpty
            TextUtils.isEmpty(email.value) -> state.value = SignUpState.EmailEmptyError
            !validateEmail(email.value!!) -> state.value = SignUpState.InvalidFormat
            TextUtils.isEmpty(password.value) -> state.value = SignUpState.PasswordEmptyError
            TextUtils.isEmpty(password2.value) -> state.value = SignUpState.PasswordEmptyError2
            !isEqualPasswords(password.value!!, password2.value!!) -> state.value =
                SignUpState.PasswordsNotEquals


            else -> {
                viewModelScope.launch(Dispatchers.IO) {
                    state.postValue(SignUpState.Loading(true))
                    val result = userRepository.insert(user)
                    state.postValue(SignUpState.Loading(false))

                    when (result) {
                        is Resource.Error -> state.postValue(SignUpState.UserExist(result.exception.toString()))
                        is Resource.Success<*> -> {
                            state.postValue(SignUpState.Success)
                        }
                    }
                }
            }
        }
    }

    private fun isEqualPasswords(pass1: String, pass2: String): Boolean {
        return pass1 == pass2
    }

    fun validateEmail(value: String): Boolean {
        val pattern = Pattern.compile("^\\S+@\\S+\\.\\S+$")

        return pattern.matcher(value).matches()
    }

}