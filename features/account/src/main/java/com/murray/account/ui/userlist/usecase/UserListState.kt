package com.murray.account.ui.userlist.usecase

import com.murray.data.accounts.User

sealed class UserListState() {

    data object NoDataError: UserListState()
    data object Success: UserListState()
    data class  Loading (val value :  Boolean): UserListState()
}