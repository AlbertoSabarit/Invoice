package com.murray.item.ui.itemcreation.usecase

import android.net.Uri
import android.text.TextUtils
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.murray.data.items.Item
import com.murray.data.items.ItemType
import com.murray.database.repository.ItemRepositoryDB
import com.murray.networkstate.Resource
import com.murray.repositories.ItemRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ItemCreationViewModel : ViewModel() {
    var id = MutableLiveData<Int>()
    var name = MutableLiveData<String>()
    var typeSpinnerPosition = MutableLiveData<Int>()
    var rate = MutableLiveData<String>() //es string porque se introduce el valor en un edittext
    var isTaxable = MutableLiveData<Boolean>()
    var description = MutableLiveData<String>()

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
                //state.postValue(ItemCreateState.Loading(true))
                viewModelScope.launch(Dispatchers.IO) {
                    val result = itemRepository.insert(item)
                    withContext(Dispatchers.Main) {
                        //state.postValue(ItemCreateState.Loading(false))
                    }

                    when (result) {
                        is Resource.Error -> {
                            withContext(Dispatchers.Main) {
                                state.value = ItemCreationState.ItemExistsError(result.exception.message!!)
                            }
                        }

                        is Resource.Success<*> -> {
                            withContext(Dispatchers.Main) {
                                state.value = ItemCreationState.Success
                            }
                        }
                    }

                }
            }
        }
    }
    fun addItem(name:String, type:ItemType, rate:Double, isTaxable:Boolean, description:String, imageUri: Uri?){
        itemRepository.insert(Item(name, type, rate, isTaxable, description, imageUri))
    }

    fun editItem(itemArgs: Item) {
        for (itemDataset in ItemRepository.getDataSetItem()) {
            if (itemDataset.id == itemArgs.id) {
                itemDataset.name = itemArgs.name
                itemDataset.type = itemArgs.type
                itemDataset.rate = itemArgs.rate
                itemDataset.isTaxable = itemArgs.isTaxable
                itemDataset.description = itemArgs.description
                itemDataset.imageUri = itemArgs.imageUri
            }
        }
    }
}
