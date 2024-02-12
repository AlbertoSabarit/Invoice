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

    fun getItemByName(name:String): Item {
        return InvoiceDatabase.getInstance().itemDao().selectByName(name)
    }

    fun insert(item: Item): Resource {
        try {
            InvoiceDatabase.getInstance().itemDao().insert(item)
        } catch (e: SQLiteException) {
            return Resource.Error(e)
        }
        return Resource.Success(item)
    }

    fun delete(item: Item) {
        InvoiceDatabase.getInstance().itemDao().delete(item)
    }


}