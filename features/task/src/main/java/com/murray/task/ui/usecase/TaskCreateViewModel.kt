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
                    state.value = TaskCreateState.Loading(false)
                    state.value = TaskCreateState.Success

                }
            }
        }
    }

    fun editTask(taskTmp: Task){
        for (t in TaskRepository.dataSet) {
            if (t.id == task.id) {

                t.titulo = taskTmp.titulo
                t.nombre = taskTmp.nombre
                t.tarea = taskTmp.tarea
                t.fechaCreacion = taskTmp.fechaCreacion
                t.fechaFin = taskTmp.fechaFin
                t.estado = taskTmp.estado
                t.descripcion = taskTmp.descripcion
            }
        }
    }
    fun addToList(task: Task){
        TaskRepository.addTask(task)
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