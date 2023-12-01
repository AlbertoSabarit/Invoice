package com.murray.item.ui.itemcreation.usecase

import android.text.TextUtils
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.murray.entities.items.ItemType

class ItemCreationViewModel:ViewModel() {
    var name = MutableLiveData<String>()
    var spinnerSelectedPosition = MutableLiveData<Int>()
    var type = MutableLiveData<ItemType>()
    var rate = MutableLiveData<String>()
    var isTaxable = MutableLiveData<Boolean>()
    var description = MutableLiveData<String>()

    private var state = MutableLiveData<ItemCreationState>()

    //se especifica el tipo a partir de la posicion elegida en el spinner
    init {
        spinnerSelectedPosition.observeForever { position ->
            when(position){
                0 -> type.value = ItemType.PRODUCT
                1 -> type.value = ItemType.SERVICE
            }
        }
    }

    fun getState() : LiveData<ItemCreationState> {
        return state
    }

    fun validateItemCreation(){
        when{
            TextUtils.isEmpty(name.value) -> state.value = ItemCreationState.NameEmptyError
            rate.value?.toDoubleOrNull() == null -> state.value = ItemCreationState.InvalidFormatRateError
            //todo TypeIsMandatoryError
            //todo NotEnoughDataError
            else -> state.value = ItemCreationState.Success
        }
    }

    //switch
    /*
    fun onSwitchChanged(value: Boolean){
        isTaxable.value = value
    }*/
}
