package com.murray.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.Companion.REPLACE
import androidx.room.Query
import com.murray.data.items.Item
import kotlinx.coroutines.flow.Flow

@Dao
interface ItemDao {

    @Insert(onConflict = REPLACE)
    fun insert(item: Item) : Long

    /*
    @Query("SELECT * FROM item where id = item.id")
    fun selectItem(id: Int):Item*/

    @Query ("SELECT * FROM item")
    fun selectAll(): Flow<List<Item>>

    @Delete
    fun delete(item: Item)
}