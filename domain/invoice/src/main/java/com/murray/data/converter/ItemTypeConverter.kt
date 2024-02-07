package com.murray.data.converter

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.murray.data.items.ItemType

@ProvidedTypeConverter
class ItemTypeConverter {

    @TypeConverter
    fun fromItemType(itemType: ItemType): String {
        return itemType.name
    }

    @TypeConverter
    fun toItemType(itemType: String): ItemType {
        return enumValueOf(itemType)
    }
}