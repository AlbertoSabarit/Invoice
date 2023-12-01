package com.murray.account.ui.sigin.usecase

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

    //liveData que tendrá su Observador en el Fragment y controla las excepciones/casos de uso
    // de la operación Login

    var state = MutableLiveData<SignInState>()

    //Crear la clase sellada que permitirá gestionar las excepciones de la vista

    /**
     * Esta función se ejecuta directamente desde el fichero XML al usar
     * DataBinding. android:onClick="@{() -> viewmodel.validateCredentials()}"
     */
    fun validateCredentials() {

        //Ejemplos de prueba
        //Log.i(TAG, "El email es ${email.value} y el password es ${password.value}")
        //email.value = "nuevo valor"

        when {
            TextUtils.isEmpty(email.value) -> state.value = SignInState.EmailEmptyError
            TextUtils.isEmpty(password.value) -> state.value = SignInState.PasswordEmptyError
            //EmailFormat
            //PasswordFormat
            else -> {
                //Se crea una corrutina que suspende el hilo principal hasta que el e
                //bloquee withContent del repositorio termine de ejecutarse

                viewModelScope.launch {
                    //Vamos a ejecutar el Login del repositorio -> que pregunta a la capa de la infraestructura
                    state.value = SignInState.Loading(true)
                    //La respuesta del Repositorio es asíncrona

                    //val result = UserRepository.login(email.value!!, password.value!!)
                    val authFirebase = AuthFirebase()
                    val result = authFirebase.login(email.value!!, password.value!!)

                    //ES OBLIGATORIO: pausar/quitar el FragmentDialog antes de mostrar el error. Ya que el Fragment SignIn está pausado.
                    state.value = SignInState.Loading(false)


                    when (result) {
                        //"is" cuando sea un dataclass
                        is Resource.Success<*> -> {
                                // Manejo de error si el tipo de dato no es el esperado
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

    /**
     * Se crea solo la funcion de obtención de la variable State. No se puede modificar su
     * valor fuera de ViewModel
     */

    fun getState(): LiveData<SignInState> {
        return state
    }
}