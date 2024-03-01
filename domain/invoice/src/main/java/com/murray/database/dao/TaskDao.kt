package com.murray.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.ForeignKey.Companion.RESTRICT
import androidx.room.Insert
import androidx.room.OnConflictStrategy.Companion.REPLACE
import androidx.room.Query
import androidx.room.Update
import com.murray.data.tasks.Task
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDao {

    @Insert(onConflict = RESTRICT) //(onConflict = ForeignKey.RESTRICT)
    fun insert(task: Task): Long


    @Query("SELECT * FROM task ORDER BY cliente")
    fun selectAll(): Flow<List<Task>>

    @Update
    fun update(task: Task)

    @Delete
    fun delete(task: Task)
}