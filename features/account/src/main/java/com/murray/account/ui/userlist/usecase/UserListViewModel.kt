package com.murray.account.ui.userlist.usecase

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.murray.data.accounts.User
import com.murray.database.repository.UserRepositoryDB
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch

class UserListViewModel : ViewModel() {

    private var state = MutableLiveData<UserListState>()
    var userRepository = UserRepositoryDB()
    var allUser: LiveData<List<User>> = userRepository.getUserList().asLiveData()


    fun getState(): LiveData<UserListState> {
        return state
    }

    fun getUserList() {
        when {
            allUser.value?.isEmpty() == true -> state.value = UserListState.NoDataError
            else -> state.value = UserListState.Success
        }
    }

    fun delete(user: User) {
        viewModelScope.launch(Dispatchers.IO) {
            userRepository.delete(user)
        }
    }
}
