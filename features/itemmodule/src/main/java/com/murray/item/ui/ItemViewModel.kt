package com.murray.item.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.murray.entities.items.ItemType

class ItemViewModel:ViewModel() {
    var name = MutableLiveData<String>()
    var spinnerSelectedPosition = MutableLiveData<Int>()
    var type = MutableLiveData<ItemType>()
    var rate = MutableLiveData<String>()
    var isTaxable = MutableLiveData<Boolean>()
    var description = MutableLiveData<String>()

    //se especifica el tipo a partir de la posicion elegida en el spinner
    init {
        spinnerSelectedPosition.observeForever { position ->
            when(position){
                0 -> type.value = ItemType.PRODUCT
                1 -> type.value = ItemType.SERVICE
            }
        }
    }

    /*
    fun onSwitchChanged(value: Boolean){
        isTaxable.value = value
    }*/
}
