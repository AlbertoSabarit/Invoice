package com.murray.task.ui.usecase

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.murray.entities.tasks.Task
import com.murray.network.ResourceList
import com.murray.repositories.TaskRepository
import com.murray.repositories.UserRepository
import kotlinx.coroutines.launch

class TaskListViewModel : ViewModel() {

    private var state = MutableLiveData<TaskListState>()

    fun getState(): LiveData<TaskListState> {
        return state
    }

    fun getTaskList() {
        //Iniciar la corrutina
        viewModelScope.launch {

            //Opcion 1: me devuelve diferentes estados
            state.value = TaskListState.Loading(true)
            val result = TaskRepository.getTaskList() // val result = TaskRepository.dataSet
            state.value = TaskListState.Loading(false)


            when (result) {
                is ResourceList.Success<*> -> state.value = TaskListState.Success(result.data as ArrayList<Task>)

                is ResourceList.Error -> state.value = TaskListState.NoDataError
            }
        }
    }
}