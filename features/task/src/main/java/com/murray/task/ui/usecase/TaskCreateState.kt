package com.murray.task.ui.usecase

sealed class TaskCreateState {
    object TitleEmptyError : TaskCreateState()
    object IncorrectDateRangeError: TaskCreateState()
    data object DescriptionEmptyError: TaskCreateState()
    data class TaskCreateError(var message: String): TaskCreateState()
    data object DataIniEmptyError: TaskCreateState()
    data object DataFinEmptyError: TaskCreateState()
    data object Success: TaskCreateState()

    data class Loading(var value: Boolean) : TaskCreateState()
}