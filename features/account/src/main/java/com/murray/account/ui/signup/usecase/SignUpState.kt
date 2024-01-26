package com.murray.account.ui.signup.usecase

sealed class SignUpState {
    data object NombreEmpty: SignUpState()
    data object ApellidoEmpty: SignUpState()
    object EmailEmptyError : SignUpState()
    data object PasswordEmptyError: SignUpState()
    data object PasswordEmptyError2: SignUpState()
    data object PasswordsNotEquals: SignUpState()
    data object InvalidFormat : SignUpState()
    data object Completed: SignUpState()
    data object Success: SignUpState()
    data class UserExist(var message: String): SignUpState()
    data class Loading(var value: Boolean) : SignUpState()
}