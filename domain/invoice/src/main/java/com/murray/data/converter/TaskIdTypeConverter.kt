package com.murray.data.converter

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.murray.data.tasks.TaskId

@ProvidedTypeConverter
class TaskIdTypeConverter {
    @TypeConverter
    fun toTaskId(value: Int): TaskId {
        return TaskId(value)
    }

    @TypeConverter
    fun fromTaskId(taskId: TaskId): Int {
        return taskId.value
    }

}