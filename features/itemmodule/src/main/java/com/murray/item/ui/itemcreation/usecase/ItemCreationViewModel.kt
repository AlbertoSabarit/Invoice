package com.murray.item.ui.itemcreation.usecase

import android.text.TextUtils
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ItemCreationViewModel:ViewModel() {
    var name = MutableLiveData<String>()
    var typeSpinnerPosition = MutableLiveData<Int>()
    var rate = MutableLiveData<String>() //es string porque se introduce el valor en un edittext
    var isTaxable = MutableLiveData<Boolean>()
    var description = MutableLiveData<String>()

    private var state = MutableLiveData<ItemCreationState>()

    fun getState() : LiveData<ItemCreationState> {
        return state
    }

    fun validateItemCreation(){
        when{
            TextUtils.isEmpty(name.value) -> state.value = ItemCreationState.NameEmptyError
            rate.value?.toDoubleOrNull() == null -> state.value = ItemCreationState.InvalidFormatRateError
            rate.value!!.toDouble() < 0 -> state.value = ItemCreationState.InvalidFormatRateError
            typeSpinnerPosition.value != 0 && typeSpinnerPosition.value != 1 -> ItemCreationState.TypeIsMandatoryError
            //todo NotEnoughDataError
            else -> state.value = ItemCreationState.Success
        }
    }
}
