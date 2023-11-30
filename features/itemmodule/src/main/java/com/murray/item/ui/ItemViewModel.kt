package com.murray.item.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.murray.entities.items.ItemType

class ItemViewModel:ViewModel() {
    var name = MutableLiveData<String>()
    var type = MutableLiveData<ItemType>()
    var rate = MutableLiveData<String>()
    var isTaxable = MutableLiveData<Boolean>()
    var description = MutableLiveData<String>()
}