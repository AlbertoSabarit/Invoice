package com.murray.data.converter

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.murray.data.accounts.Email


@ProvidedTypeConverter
class EmailTypeConverter {
    @TypeConverter
    fun toEmail(value:String) : Email = Email(value)

    @TypeConverter
    fun fromEmail(email : Email) : String = email.value
}