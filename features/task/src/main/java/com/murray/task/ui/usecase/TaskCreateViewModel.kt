package com.murray.task.ui.usecase

import android.icu.text.SimpleDateFormat
import android.net.ParseException
import android.text.TextUtils
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.murray.data.customers.Customer
import com.murray.data.tasks.Task
import com.murray.database.repository.TaskRepositoryDB
import com.murray.networkstate.Resource
import com.murray.repositories.CustomerRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.Locale

const val TAG = "ViewModel"

class TaskCreateViewModel : ViewModel() {

    var title = MutableLiveData<String>()
    var description = MutableLiveData<String>()
    var fini = MutableLiveData<String>()
    var ffin = MutableLiveData<String>()

    lateinit var task: Task
    private var state = MutableLiveData<TaskCreateState>()

    var taskRepository = TaskRepositoryDB()

    fun getState(): LiveData<TaskCreateState> {
        return state
    }

    fun validateCredentials(task: Task) {

        when {
            TextUtils.isEmpty(title.value) -> state.value = TaskCreateState.TitleEmptyError
            TextUtils.isEmpty(description.value) -> state.value =
                TaskCreateState.DescriptionEmptyError

            TextUtils.isEmpty(fini.value) -> state.value = TaskCreateState.DataIniEmptyError
            TextUtils.isEmpty(ffin.value) -> state.value = TaskCreateState.DataFinEmptyError
            !isValidDateRange(fini.value!!, ffin.value!!) -> state.value =
                TaskCreateState.IncorrectDateRangeError

            else -> {
                //state.postValue(TaskCreateState.Loading(true))
                viewModelScope.launch(Dispatchers.IO) {
                    val result = taskRepository.insert(task)
                    withContext(Dispatchers.Main) {
                        //state.postValue(TaskCreateState.Loading(false))
                    }

                    when (result) {
                        is Resource.Error -> {
                            withContext(Dispatchers.Main) {
                                state.value = TaskCreateState.TaskExist(result.exception.message!!)
                            }
                        }

                        is Resource.Success<*> -> {
                            withContext(Dispatchers.Main) {
                                state.value = TaskCreateState.Success
                            }
                        }
                    }

                }
            }
        }
    }

    fun editTask(taskTmp: Task) {
     /*   for (t in TaskRepository.dataSet) {
            if (t.id == task.id) {

                t.titulo = taskTmp.titulo
                t.cliente.name = taskTmp.cliente.name
                t.tipoTarea = taskTmp.tipoTarea
                t.fechaCreacion = taskTmp.fechaCreacion
                t.fechaFin = taskTmp.fechaFin
                t.estado = taskTmp.estado
                t.descripcion = taskTmp.descripcion
            }
        }*/
    }

    fun getCustomerList(): MutableList<Customer> {
        return CustomerRepository.getCustomers()
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


}