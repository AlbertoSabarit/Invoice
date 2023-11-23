package com.murray.entities.items

data class Item(
    var name: String,
    var type: String,
    var rate: String,
    var isTaxable: Boolean,
    var description: String,
    var image: Int,
    )
