package com.murray.account.ui.signin.usecase

import android.text.TextUtils
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.murray.data.accounts.Account
import com.murray.data.accounts.Email
import com.murray.data.accounts.User
import com.murray.database.repository.UserRepositoryDB
import com.murray.firebase.AuthFirebase
import com.murray.invoice.Locator
import com.murray.networkstate.Resource
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
                            val account = result.data as Account
                            Log.e(TAG, "Login correcto del usuario")
                            state.value = SignInState.Success(result.data as? Account)
                            //Guardar la informacion del usuario en el almacen de datos user_preferences
                            Locator.userPreferencesRepository.saveUser(account.email.value, account.password.toString(), account.id.value)
                        }

                        is Resource.Error -> {
                            Log.i(TAG, "Informacion del dato ${result.exception.message}")
                            state.value =
                                SignInState.AuthenticationError(result.exception.message!!)
                        }

                        else ->{}
                    }
                }
            }
        }
    }

    fun getState(): LiveData<SignInState> {
        return state
    }
}