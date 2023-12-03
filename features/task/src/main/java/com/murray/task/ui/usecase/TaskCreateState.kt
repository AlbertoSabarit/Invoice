package com.murray.task.ui.usecase

sealed class TaskCreateState {
    object TitleEmptyError : TaskCreateState()
    object IncorrectDateRangeError: TaskCreateState()
    data object DescriptionEmptyError: TaskCreateState()
    data class TaskCreateError(var message: String): TaskCreateState()
    data object DataIniEmptyError: TaskCreateState()
    data object DataFinEmptyError: TaskCreateState()
    data object Success: TaskCreateState()

    //Se debe crear una clase que contiene un valor booleano que indica si se muestra el ProgressBar

    data class Loading(var value: Boolean) : TaskCreateState()
}