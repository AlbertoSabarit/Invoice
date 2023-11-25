package com.murray.entities.items

import com.murray.repositories.ImagesItem

data class Item(
    var name: String,
    var type: String,
    var rate: String,
    var isTaxable: Boolean,
    var description: String,
    var image: ImagesItem,
    )
