package com.murray.task.ui.usecase

import android.text.TextUtils
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.murray.network.Resource
import com.murray.repositories.TaskRepository
import kotlinx.coroutines.launch

const val TAG = "ViewModel"
class TaskCreateViewModel : ViewModel() {

    var title = MutableLiveData<String>()
    var description = MutableLiveData<String>()

    //liveData que tendrá su Observador en el Fragment y controla las excepciones/casos de uso
    private var state = MutableLiveData<TaskCreateState>()


    fun validateCredentials() {

        when {
            TextUtils.isEmpty(title.value) -> state.value = TaskCreateState.TitleEmptyError
            TextUtils.isEmpty(description.value) -> state.value = TaskCreateState.DescriptionEmptyError
            else -> {
                viewModelScope.launch {

                    state.value = TaskCreateState.Loading(true)
                    //La respuesta del Repositorio es asíncrona

                    val result = TaskRepository.createTask(title.value!!, description.value!!)

                    //ES OBLIGATORIO: pausar/quitar el FragmentDialog antes de mostrar el error. Ya que el Fragment SignIn está pausado.
                    state.value = TaskCreateState.Loading(false)
                    when (result) {
                        //"is" cuando sea un dataclass
                        is Resource.Success<*> -> {
                            // Manejo de error si el tipo de dato no es el esperado
                            Log.e(TAG, "Login correcto del usuario")
                            state.value = TaskCreateState.Success
                        }
                        is Resource.Error -> {
                            Log.i(TAG, "Informacion del dato ${result.exception.message}")
                            state.value = TaskCreateState.TaskCreateError(result.exception.message!!)
                        }
                    }
                }
            }
        }

    }

    fun getState(): LiveData<TaskCreateState> {
        return state
    }
}