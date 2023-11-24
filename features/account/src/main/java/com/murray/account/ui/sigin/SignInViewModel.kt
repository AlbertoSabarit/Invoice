package com.murray.account.ui.sigin

import android.text.TextUtils
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.murray.network.Resource
import com.murray.repositories.UserRepository
import kotlinx.coroutines.launch

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
            //EmailFormat
            //PasswordFormat
            else -> {
                //Se crea una corrutina que suspende el hilo principal hasta que el e
                //bloquee withContent del repositorio termine de ejecutarse

                viewModelScope.launch {
                    //Vamos a ejecutar el Login del repositorio -> que pregunta a la capa de la infraestructura

                    //"is" cuando sea un dataclass
                    when(val result = UserRepository.login(email.value!!, password.value!!)){
                        is Resource.Success<*> ->{
                           //Aqui tenemos que hacer un casting Seguro porque el tipo de dato es genérico T

                        }
                        is Resource.Error ->{
                            Log.i(TAG, "Informacion del dato ${result.exception.message}")
                            state.value = SignInState.AuthenticationError(result.exception.message!!)
                        }
                    }
                }
            }
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