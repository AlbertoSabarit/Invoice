package com.murray.account.ui.sigin

sealed class SignInState {
    object EmailEmptyError : SignInState()
    data object EmailFormatError: SignInState()
    data object PasswordEmptyError: SignInState()
    data object PasswordFormatError: SignInState()
    data object Success : SignInState()
}