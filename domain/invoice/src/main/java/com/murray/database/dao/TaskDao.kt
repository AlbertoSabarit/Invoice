package com.murray.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.ForeignKey.Companion.RESTRICT
import androidx.room.Insert
import androidx.room.OnConflictStrategy.Companion.REPLACE
import androidx.room.Query
import com.murray.data.tasks.Task
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDao {

    @Insert(onConflict = REPLACE) //(onConflict = ForeignKey.RESTRICT)
    fun insert(task: Task): Long


    @Query("SELECT * FROM task ORDER BY titulo")
    fun selectAll(): Flow<List<Task>>


    @Delete
    fun delete(task: Task)
}