package com.murray.account.ui.signin.usecase

import android.text.TextUtils
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.murray.entities.accounts.Account
import com.murray.firebase.AuthFirebase
import com.murray.network.Resource
import kotlinx.coroutines.launch

const val TAG = "ViewModel"

class SignInViewModel : ViewModel() {

    var email = MutableLiveData<String>()
    var password = MutableLiveData<String>()


    var state = MutableLiveData<SignInState>()

    fun validateCredentials() {


        when {
            TextUtils.isEmpty(email.value) -> state.value = SignInState.EmailEmptyError
            TextUtils.isEmpty(password.value) -> state.value = SignInState.PasswordEmptyError
            else -> {

                viewModelScope.launch {

                    val authFirebase = AuthFirebase()
                    val result = authFirebase.login(email.value!!, password.value!!)

                    when (result) {

                        is Resource.Success<*> -> {

                                Log.e(TAG, "Login correcto del usuario")
                            state.value = SignInState.Success(result.data as? Account)

                        }
                        is Resource.Error -> {
                            Log.i(TAG, "Informacion del dato ${result.exception.message}")
                            state.value =
                                SignInState.AuthenticationError(result.exception.message!!)
                        }
                    }
                }
            }
        }
    }

    fun getState(): LiveData<SignInState> {
        return state
    }
}