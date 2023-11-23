package com.murray.account.ui.sigin

import android.text.TextUtils
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

const val TAG = "ViewModel"

class SignInViewModel: ViewModel() {

    var email =  MutableLiveData<String>()
    var password =  MutableLiveData<String>()

    //liveData que tendrá su Observador en el Fragment y controla las excepciones/casos de uso
    // de la operación Login

    private var state = MutableLiveData<SignInState>()

    //Crear la clase sellada que permitirá gestionar las excepciones de la vista

    /**
     * Esta función se ejecuta directamente desde el fichero XML al usar
     * DataBinding. android:onClick="@{() -> viewmodel.validateCredentials()}"
     */
    fun validateCredentials(){

        //Ejemplos de prueba
        //Log.i(TAG, "El email es ${email.value} y el password es ${password.value}")
        //email.value = "nuevo valor"

        when{
            TextUtils.isEmpty(email.value) -> state.value = SignInState.EmailEmptyError
            TextUtils.isEmpty(password.value) -> state.value = SignInState.PasswordEmptyError
            //else -> state.value = SignInState.Success
        }
    }

    /**
     * Se crea solo la funcion de obtención de la variable State. No se puede modificar su
     * valor fuera de ViewModel
     */

    fun getState(): LiveData<SignInState> {
        return state
    }
}