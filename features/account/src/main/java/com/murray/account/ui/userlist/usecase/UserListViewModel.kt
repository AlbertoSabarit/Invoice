package com.murray.account.ui.userlist.usecase

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.murray.entities.accounts.User
import com.murray.network.ResourceList
import com.murray.repositories.UserRepository
import kotlinx.coroutines.launch

class UserListViewModel : ViewModel() {

    private var state = MutableLiveData<UserListState>()

    fun getState(): LiveData<UserListState> {
        return state
    }


    fun getUserList() {

        viewModelScope.launch {

            state.value = UserListState.Loading(true)
            val result = UserRepository.getUserList()
            state.value = UserListState.Loading(false)

            when (result) {
                is ResourceList.Success<*> -> {
                    (result.data as ArrayList<User>).sort()
                    state.value = UserListState.Success(result.data as ArrayList<User>)
                }

                is ResourceList.NoData -> state.value = UserListState.NoDataError
            }
        }
    }

}
