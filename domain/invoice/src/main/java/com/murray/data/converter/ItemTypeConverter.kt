package com.murray.data.converter

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.murray.data.items.Item
import com.murray.database.InvoiceDatabase

@ProvidedTypeConverter
class ItemTypeConverter {
    @TypeConverter
    fun toItem(value: Int): Item {
        val item = InvoiceDatabase.getInstance().itemDao().selectById(value)
        return item
    }

    @TypeConverter
    fun fromItem(item: Item): Int {
        return item.id
    }
}