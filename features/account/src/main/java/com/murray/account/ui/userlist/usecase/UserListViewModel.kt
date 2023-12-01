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

    /**
     * Funcion que pide el listado de usuarios al repositorio
     */
    fun getUserList() {
        //Iniciar la corrutina
        viewModelScope.launch {

            //Opcion 1: me devuelve diferentes estados
            state.value = UserListState.Loading(true)
            val result = UserRepository.getUserList()
            state.value = UserListState.Loading(false)


            when (result) {
                is ResourceList.Success<*> -> state.value = UserListState.Success(result.data as ArrayList<User>)

                is ResourceList.Error -> state.value = UserListState.NoDataError
            }

            //Opcion 2: me devuelve la lista, porque solo tenemos 2 posibles estados, el de error no datos y el de exito
            /* val data = UserRepository.getUserList()
             when{
                 data.isEmpty() -> state.value = UserListState.NoDataError
                 else -> state.value = UserListState.Success(data)*/
        }
    }
}
