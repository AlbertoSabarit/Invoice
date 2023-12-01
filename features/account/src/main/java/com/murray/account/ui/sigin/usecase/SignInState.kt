package com.murray.account.ui.sigin.usecase

import com.murray.entities.accounts.Account

sealed class SignInState {
    object EmailEmptyError : SignInState()
    data object EmailFormatError: SignInState()
    data object PasswordEmptyError: SignInState()

    data object Completed: SignInState()
    data class AuthenticationError(var message: String): SignInState()
    data class Success (var account: Account?) : SignInState()

    //Se debe crear una clase que contiene un valor booleano que indica si se muestra el ProgressBar

    data class Loading(var value: Boolean) : SignInState()
}