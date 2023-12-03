package com.murray.account.ui.signup.usecase

sealed class SignUpState {
    object EmailEmptyError : SignUpState()
    data object PasswordEmptyError: SignUpState()
    data object PasswordEmptyError2: SignUpState()
    data object PasswordsNotEquals: SignUpState()

    data object Completed: SignUpState()
    data object Success: SignUpState()

    data class Loading(var value: Boolean) : SignUpState()
}