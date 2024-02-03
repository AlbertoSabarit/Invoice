package com.murray.database.repository

import android.database.sqlite.SQLiteException
import com.murray.data.items.Item
import com.murray.database.InvoiceDatabase
import com.murray.networkstate.Resource
import kotlinx.coroutines.flow.Flow

class ItemRepositoryDB {
    fun getItemList(): Flow<List<Item>> {
        return InvoiceDatabase.getInstance().itemDao().selectAll()
    }

    fun insert(item: Item): Resource {
        return try {
            val itemId = InvoiceDatabase.getInstance().itemDao().insert(item)
            Resource.Success(itemId)
        } catch (e: SQLiteException) {
            Resource.Error(e)
        }
    }

    fun delete(item: Item) {
        InvoiceDatabase.getInstance().itemDao().delete(item)
    }
}