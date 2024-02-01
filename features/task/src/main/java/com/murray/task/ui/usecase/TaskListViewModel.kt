package com.murray.task.ui.usecase

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.murray.data.tasks.Task
import com.murray.database.repository.TaskRepositoryDB
import kotlinx.coroutines.launch
import androidx.lifecycle.asLiveData
import kotlinx.coroutines.Dispatchers

class TaskListViewModel : ViewModel() {

    private var state = MutableLiveData<TaskListState>()
    var taskRepository = TaskRepositoryDB()
    var allTask: LiveData<List<Task>> = taskRepository.getTaskList().asLiveData()

    fun getState(): LiveData<TaskListState> {
        return state
    }

    fun getTaskList() {
        when {
            allTask.value?.isEmpty() == true -> state.value = TaskListState.NoDataError
            else -> state.value = TaskListState.Success
        }

    }

    fun delete(task: Task) {
        viewModelScope.launch(Dispatchers.IO) {
            taskRepository.delete(task)
        }
    }
}