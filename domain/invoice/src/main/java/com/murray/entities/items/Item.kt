package com.murray.entities.items

import android.net.Uri
import java.io.Serializable

data class Item(
    var id: Int,
    var name: String,
    var type: ItemType,
    var rate: Double,
    var isTaxable: Boolean,
    var description: String = "",
    var imageUri: Uri? = null,
    ): Serializable, Comparable<Item> {

    override fun compareTo(other: Item): Int {
        return name.compareTo(other.name)
    }
}

