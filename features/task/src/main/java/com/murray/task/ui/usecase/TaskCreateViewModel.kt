package com.murray.task.ui.usecase

import android.icu.text.SimpleDateFormat
import android.net.ParseException
import android.text.TextUtils
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.murray.entities.tasks.Task
import com.murray.network.Resource
import com.murray.repositories.TaskRepository
import kotlinx.coroutines.launch
import java.util.Locale

const val TAG = "ViewModel"
class TaskCreateViewModel : ViewModel() {

    var title = MutableLiveData<String>()
    var description = MutableLiveData<String>()
    var fini =  MutableLiveData<String>()
    var ffin =  MutableLiveData<String>()

    lateinit var task : Task

    //liveData que tendrá su Observador en el Fragment y controla las excepciones/casos de uso
    private var state = MutableLiveData<TaskCreateState>()


    fun validateCredentials() {

        when {
            TextUtils.isEmpty(title.value) -> state.value = TaskCreateState.TitleEmptyError
            TextUtils.isEmpty(description.value) -> state.value = TaskCreateState.DescriptionEmptyError

            TextUtils.isEmpty(fini.value) -> state.value = TaskCreateState.DataIniEmptyError
            TextUtils.isEmpty(ffin.value) -> state.value = TaskCreateState.DataFinEmptyError
            !isValidDateRange(fini.value!!, ffin.value!!) -> state.value = TaskCreateState.IncorrectDateRangeError

            else -> {
                viewModelScope.launch {

                    state.value = TaskCreateState.Loading(true)
                    //La respuesta del Repositorio es asíncrona

                    //val result = TaskRepository.createTask(title.value!!, description.value!!,fini.value!!, ffin.value!!)

                    //ES OBLIGATORIO: pausar/quitar el FragmentDialog antes de mostrar el error. Ya que el Fragment SignIn está pausado.
                    state.value = TaskCreateState.Loading(false)

                    state.value = TaskCreateState.Success

                    /*
                    when (result) {
                        //"is" cuando sea un dataclass
                        is Resource.Success<*> -> {
                            // Manejo de error si el tipo de dato no es el esperado
                            Log.e(TAG, "Tarea creada")
                            state.value = TaskCreateState.Success
                        }
                        is Resource.Error -> {
                            Log.i(TAG, "Informacion del dato ${result.exception.message}")
                            state.value = TaskCreateState.TaskCreateError(result.exception.message!!)
                        }
                    }
                     */
                }
            }
        }

    }
    private fun isValidDateRange(startDate: String, endDate: String): Boolean {
        val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        try {
            val start = dateFormat.parse(startDate)
            val end = dateFormat.parse(endDate)
            return !end.before(start)
        } catch (e: ParseException) {
            e.printStackTrace()
            return false
        }
    }
    fun getState(): LiveData<TaskCreateState> {
        return state
    }
}