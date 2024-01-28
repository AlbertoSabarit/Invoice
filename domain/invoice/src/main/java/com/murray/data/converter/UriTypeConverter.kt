package com.murray.data.converter

import android.net.Uri
import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter

@ProvidedTypeConverter
class UriTypeConverter {
    @TypeConverter
    fun toUri(value: String): Uri {
        return Uri.parse(value)
    }

    @TypeConverter
    fun fromUri(uri: Uri): String{
        return uri.toString()
    }
}
