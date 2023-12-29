package com.murray.task.ui.usecase

import com.murray.entities.tasks.Task


sealed class TaskListState {
    data object NoDataError: TaskListState()
    data class  Loading (val value :  Boolean): TaskListState()
    data class Success (val dataset: ArrayList<Task>) : TaskListState()
}