package com.murray.database.dao

import androidx.room.Dao
import androidx.room.ForeignKey.Companion.RESTRICT
import androidx.room.Insert
import androidx.room.Query
import com.murray.data.accounts.User


@Dao
interface UserDao {

    @Insert (onConflict = RESTRICT)
    fun insert (user: User)


    @Query ("SELECT * FROM user")
    fun selectAll(): List<User>
}