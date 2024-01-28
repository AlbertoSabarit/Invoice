package com.murray.data.converter

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.murray.data.items.ItemId

@ProvidedTypeConverter
class ItemIdTypeConverter {
    @TypeConverter
    fun toItemId(value: Int): ItemId {
        return ItemId(value)
    }

    @TypeConverter
    fun fromItemId(itemId: ItemId): Int {
        return itemId.value
    }
}
