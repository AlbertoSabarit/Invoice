package com.murray.item.ui.itemcreation.usecase

import android.net.Uri
import android.text.TextUtils
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.murray.data.items.Item
import com.murray.database.repository.ItemRepositoryDB
import com.murray.networkstate.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ItemCreationViewModel : ViewModel() {
    var id = MutableLiveData<Int>()
    var name = MutableLiveData<String>()
    var typeSpinnerPosition = MutableLiveData<Int>()
    var rate = MutableLiveData<String>()
    var isTaxable = MutableLiveData<Boolean>()
    var description = MutableLiveData<String>()
    var itemTemp = MutableLiveData<Item>()

    private var state = MutableLiveData<ItemCreationState>()

    var itemRepository = ItemRepositoryDB()

    fun getState(): LiveData<ItemCreationState> {
        return state
    }

    fun validateItemCreation(item: Item) {
        when {
            TextUtils.isEmpty(name.value) -> state.value = ItemCreationState.NameEmptyError
            rate.value?.toDoubleOrNull() == null -> state.value =
                ItemCreationState.InvalidFormatRateError

            rate.value!!.toDouble() < 0 -> state.value = ItemCreationState.InvalidFormatRateError
            typeSpinnerPosition.value != 0 && typeSpinnerPosition.value != 1 -> state.value =
                ItemCreationState.TypeIsMandatoryError

            else -> {
                viewModelScope.launch(Dispatchers.IO) {
                    if (itemTemp.value!!.id == -1){
                        addItem(item)
                    } else{
                        item.id = itemTemp.value!!.id
                        editItem(item)
                    }
                }
            }
        }
    }
    private suspend fun addItem(item: Item){
        val result = itemRepository.insert(item)

        withContext(Dispatchers.Main) {
            handleResult(result)
        }
    }

    private suspend fun editItem(item: Item) {
        val result = itemRepository.update(item)

        withContext(Dispatchers.Main) {
            state.value = ItemCreationState.Success
        }
    }

    private fun handleResult(result: Resource) {
        when (result) {
            is Resource.Error -> {
                state.value = ItemCreationState.Error(result.exception)
            }
            is Resource.Success<*> -> {
                state.value = ItemCreationState.Success
            }
        }
    }


}
