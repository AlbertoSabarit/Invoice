package com.murray.data.items

import android.net.Uri
import android.os.Parcel
import android.os.Parcelable
import com.murray.data.base.Entity
import java.io.Serializable

data class Item(
    override var id: Int,
    var name: String,
    var type: ItemType,
    var rate: Double,
    var isTaxable: Boolean,
    var description: String = "",
    var imageUri: Uri? = null,
    ): Serializable, Comparable<Item>, Entity(id), Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString()!!,
        parcel.readParcelable(ItemType::class.java.classLoader)!!,
        parcel.readDouble(),
        parcel.readByte() != 0.toByte(),
        parcel.readString()!!,
        parcel.readParcelable(Uri::class.java.classLoader)
    )
    constructor() : this(0, "", ItemType.SERVICE, 0.0, false, "", null)

    override fun compareTo(other: Item): Int {
        return name.compareTo(other.name)
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
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

