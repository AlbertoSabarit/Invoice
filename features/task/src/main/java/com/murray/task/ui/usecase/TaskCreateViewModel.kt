package com.murray.task.ui.usecase

import android.icu.text.SimpleDateFormat
import android.net.ParseException
import android.text.TextUtils
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.murray.data.customers.Customer
import com.murray.data.tasks.Task
import com.murray.database.repository.CustomerRepositoryDB
import com.murray.database.repository.TaskRepositoryDB
import com.murray.networkstate.Resource
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
    var customerRepository = CustomerRepositoryDB()

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
                viewModelScope.launch(Dispatchers.IO) {
                    val result = taskRepository.insert(task)
                    withContext(Dispatchers.Main) {
                        if (task.id == -1) {
                            when (result) {
                                is Resource.Error -> {
                                    state.value =
                                        TaskCreateState.TaskError(result.exception.message!!)
                                }

                                is Resource.Success<*> -> {
                                    state.value = TaskCreateState.Success
                                }
                            }

                        } else {
                            taskRepository.update(task)

                            state.value = TaskCreateState.Success

                        }
                    }
                }
            }
        }
    }

    fun getCustomerList(): LiveData<List<Customer>> {
        var allCustomers: LiveData<List<Customer>> =
            customerRepository.getCustomerList().asLiveData()
        return allCustomers
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