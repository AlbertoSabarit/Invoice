package com.murray.item.ui.itemcreation.usecase

import android.net.Uri
import android.text.TextUtils
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.murray.entities.items.Item
import com.murray.entities.items.ItemType
import com.murray.repositories.ItemRepository

class ItemCreationViewModel : ViewModel() {
    var id = MutableLiveData<Int>()
    var name = MutableLiveData<String>()
    var typeSpinnerPosition = MutableLiveData<Int>()
    var rate = MutableLiveData<String>() //es string porque se introduce el valor en un edittext
    var isTaxable = MutableLiveData<Boolean>()
    var description = MutableLiveData<String>()

    private var state = MutableLiveData<ItemCreationState>()

    fun getState(): LiveData<ItemCreationState> {
        return state
    }

    fun validateItemCreation() {
        when {
            TextUtils.isEmpty(name.value) -> state.value = ItemCreationState.NameEmptyError
            rate.value?.toDoubleOrNull() == null -> state.value =
                ItemCreationState.InvalidFormatRateError

            rate.value!!.toDouble() < 0 -> state.value = ItemCreationState.InvalidFormatRateError
            typeSpinnerPosition.value != 0 && typeSpinnerPosition.value != 1 -> state.value =
                ItemCreationState.TypeIsMandatoryError

            else -> state.value = ItemCreationState.Success
        }
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

    fun addItem(name:String, type:ItemType, rate:Double, isTaxable:Boolean, description:String, imageUri: Uri?){
        ItemRepository.addItem(name, type, rate, isTaxable, description, imageUri)
    }

}
