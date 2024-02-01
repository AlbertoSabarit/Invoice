package com.murray.account.ui.userlist.usecase


sealed class UserListState() {

    data object NoDataError: UserListState()
    data object Success: UserListState()
    data class  Loading (val value :  Boolean): UserListState()
}