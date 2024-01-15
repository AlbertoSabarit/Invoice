package com.murray.item.ui.itemlist.usecase

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.murray.entities.invoices.Invoice
import com.murray.entities.items.Item
import com.murray.item.adapter.ItemListAdapter
import com.murray.network.ResourceList
import com.murray.repositories.InvoiceRepository
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
                is ResourceList.Success<*> -> {
                    val dataset = result.data as ArrayList<Item>
                    dataset.sort()
                    state.value = ItemListState.Success(result.data as ArrayList<Item>)
                }
                is ResourceList.NoData -> state.value = ItemListState.NoDataError
            }
        }
    }

    fun getInvoiceItemName(cadena: String): String? {
        val regex = Regex("\\d+ x (.+)")
        val matchResult = regex.find(cadena)
        return matchResult?.groupValues?.get(1)
    }

    fun deleteItem(item: Item, itemListAdapter:ItemListAdapter) {
        ItemRepository.getDataSetItem().remove(item)
        updateItemList(itemListAdapter)
        checkItemListEmpty()
    }

    fun updateItemList(itemListAdapter:ItemListAdapter){
        itemListAdapter.update(ItemRepository.getDataSetItem() as ArrayList<Item>)
    }

    fun checkItemListEmpty(){
        if (ItemRepository.getDataSetItem().isEmpty()){
            state.value = ItemListState.NoDataError
        }
    }

    fun getInvoiceRepository(): MutableList<Invoice>{
        return InvoiceRepository.dataSet
    }


}