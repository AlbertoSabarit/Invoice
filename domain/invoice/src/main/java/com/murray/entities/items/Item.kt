package com.murray.entities.items

import com.murray.repositories.ImagesItem

enum class ItemType() {
    PRODUCT,
    SERVICE
}
data class Item(
    var name: String,
    var type: ItemType,
    var rate: Double,
    var isTaxable: Boolean,
    var description: String="",
    var image: ImagesItem,
    )

