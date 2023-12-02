package com.murray.item.ui.itemlist.usecase

import com.murray.entities.items.Item

sealed class ItemListState {
    data object NoDataError: ItemListState()
    data class Success(val dataset: ArrayList<Item>): ItemListState()
    data class Loading(val value: Boolean):ItemListState()
}