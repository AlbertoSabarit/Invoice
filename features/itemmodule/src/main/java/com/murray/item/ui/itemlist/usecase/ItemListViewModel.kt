package com.murray.item.ui.itemlist.usecase

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.murray.data.items.Item
import com.murray.database.repository.ItemRepositoryDB
import com.murray.item.adapter.ItemListAdapter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ItemListViewModel : ViewModel() {

    private var state = MutableLiveData<ItemListState>()
    var itemRepository = ItemRepositoryDB()
    var allItem: LiveData<List<Item>> = itemRepository.getItemList().asLiveData()

    fun getState(): LiveData<ItemListState> {
        return state
    }

    fun getItemList() {
        when {
            allItem.value?.isEmpty() == true -> state.value = ItemListState.NoDataError
            else -> state.value = ItemListState.Success
        }
    }

    fun delete(item: Item) {
        viewModelScope.launch(Dispatchers.IO) {
            itemRepository.delete(item)
        }
    }

}