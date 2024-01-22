package com.murray.task.ui.usecase

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.murray.data.tasks.Task
import com.murray.networkstate.ResourceList
import com.murray.repositories.TaskRepository
import kotlinx.coroutines.launch

class TaskListViewModel : ViewModel() {

    private var state = MutableLiveData<TaskListState>()

    fun getState(): LiveData<TaskListState> {
        return state
    }

    fun getTaskListOrderByCustomer() {
        viewModelScope.launch {

            state.value = TaskListState.Loading(true)
            val result = TaskRepository.getTaskList()
            state.value = TaskListState.Loading(false)

            when (result) {
                is ResourceList.NoData -> state.value = TaskListState.NoDataError
                is ResourceList.Success<*> -> {
                    (result.data as ArrayList<Task>).sort()
                    state.value = TaskListState.Success(result.data as ArrayList<Task>)
                }

            }
        }
    }

    fun getTaskListOrderByTitle() {
        viewModelScope.launch {

            state.value = TaskListState.Loading(true)
            val result = TaskRepository.getTaskList()
            state.value = TaskListState.Loading(false)

            when (result) {
                is ResourceList.NoData -> state.value = TaskListState.NoDataError
                is ResourceList.Success<*> -> {
                    (result.data as ArrayList<Task>).sortBy { it.titulo }
                    state.value = TaskListState.Success(result.data as ArrayList<Task>)
                }

            }
        }
    }

    fun removeFromList(task: Task) {
        TaskRepository.removeFromList(task)
    }
}