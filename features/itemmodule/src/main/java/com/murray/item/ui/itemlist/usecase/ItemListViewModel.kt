package com.murray.item.ui.itemlist.usecase

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.murray.entities.items.Item
import com.murray.network.ResourceList
import com.murray.repositories.ItemRepository
import kotlinx.coroutines.launch

class ItemListViewModel: ViewModel() {
    private var state = MutableLiveData<ItemListState>()

    fun getState(): LiveData<ItemListState> {
        return state
    }

    fun getItemList(){
        viewModelScope.launch {
            state.value = ItemListState.Loading(true)
            val result = ItemRepository.getItemList()
            state.value = ItemListState.Loading(false)
            when(result){
                is ResourceList.Success<*> -> state.value = ItemListState.Success(result.data as ArrayList<Item>)
                is ResourceList.Error -> state.value = ItemListState.NoDataError
            }
        }
    }
}