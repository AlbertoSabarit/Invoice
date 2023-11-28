package com.murray.task.ui

import com.murray.entities.accounts.Account

sealed class TaskState {
    object TitleEmptyError : TaskState()
    data object DescriptionEmptyError: TaskState()
    data object Success: TaskState()
    data class TaskError(var message: String): TaskState()

    //Se debe crear una clase que contiene un valor booleano que indica si se muestra el ProgressBar

    data class Loading(var value: Boolean) : TaskState()
}