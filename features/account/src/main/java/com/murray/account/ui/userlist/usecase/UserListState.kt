package com.murray.account.ui.userlist.usecase

import com.murray.data.accounts.User

sealed class UserListState() {

    data object NoDataError: UserListState()
    data class Success (val dataset: ArrayList<User>) : UserListState()
    data class  Loading (val value :  Boolean): UserListState()
}