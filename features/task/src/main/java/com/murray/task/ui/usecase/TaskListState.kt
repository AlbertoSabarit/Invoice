package com.murray.task.ui.usecase

sealed class TaskListState {
    data object NoDataError: TaskListState()
    data class  Loading (val value :  Boolean): TaskListState()
    data object Success : TaskListState()
}