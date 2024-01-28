package com.murray.data.items

import android.net.Uri
import android.os.Parcel
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.murray.data.converter.ItemIdTypeConverter
import com.murray.data.converter.UriTypeConverter

@Entity(tableName="item")
data class Item(
    @PrimaryKey
    @TypeConverters(ItemIdTypeConverter::class)
    var id: ItemId,
    var name: String,
    var type: ItemType,
    var rate: Double,
    var isTaxable: Boolean,
    var description: String = "",
    @TypeConverters(UriTypeConverter::class)
    var imageUri: Uri? = null,
    ): Comparable<Item>, Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readParcelable(ItemId::class.java.classLoader)!!,
        parcel.readString()!!,
        parcel.readParcelable(ItemType::class.java.classLoader)!!,
        parcel.readDouble(),
        parcel.readByte() != 0.toByte(),
        parcel.readString()!!,
        parcel.readParcelable(Uri::class.java.classLoader)
    )
    constructor() : this(ItemId(0), "", ItemType.SERVICE, 0.0, false, "", null)

    override fun compareTo(other: Item): Int {
        return name.compareTo(other.name)
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeParcelable(id, flags)
        parcel.writeString(name)
        parcel.writeDouble(rate)
        parcel.writeByte(if (isTaxable) 1 else 0)
        parcel.writeString(description)
        parcel.writeParcelable(imageUri, flags)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Item> {
        override fun createFromParcel(parcel: Parcel): Item {
            return Item(parcel)
        }

        override fun newArray(size: Int): Array<Item?> {
            return arrayOfNulls(size)
        }
    }
}

