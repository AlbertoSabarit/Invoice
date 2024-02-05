package com.murray.item.ui.itemlist.usecase

import com.murray.data.items.Item

sealed class ItemListState {
    data object NoDataError: ItemListState()
    data object Success: ItemListState()
    data class Loading(val value: Boolean):ItemListState()
}