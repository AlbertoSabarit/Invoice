package com.murray.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.ForeignKey.Companion.RESTRICT
import androidx.room.Insert
import androidx.room.Query
import com.murray.data.accounts.User
import kotlinx.coroutines.flow.Flow


@Dao
interface UserDao {

    @Insert(onConflict = RESTRICT)   //@Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(user: User): Long


    @Query("SELECT * FROM user ORDER BY name")
    fun selectAll(): Flow<List<User>>


    @Delete
    fun delete(user: User)
}