package com.murray.database.repository

sealed class UserState {

    data class InsertError(var mensaje : String) : UserState()
    data object Success: UserState()
}