package com.murray.account.ui.signin.usecase

import com.murray.entities.accounts.Account

sealed class SignInState {
    object EmailEmptyError : SignInState()
    data object PasswordEmptyError: SignInState()

    data object Completed: SignInState()
    data class AuthenticationError(var message: String): SignInState()
    data class Success (var account: Account?) : SignInState()


    data class Loading(var value: Boolean) : SignInState()
}