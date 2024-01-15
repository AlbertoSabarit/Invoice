package com.murray.entities.items

import android.net.Uri
import android.os.Parcel
import android.os.Parcelable
import com.murray.entities.tasks.Task
import java.io.Serializable

<<<<<<< HEAD
enum class ItemType {
    PRODUCT,
    SERVICE
}

=======
>>>>>>> 6f0a7a0d251dcaa6309bddafc2dceb1b382b14f8
data class Item(
    var id: Int,
    var name: String,
    var type: ItemType,
    var rate: Double,
    var isTaxable: Boolean,
    var description: String = "",
    var imageUri: Uri? = null,
<<<<<<< HEAD
) : Parcelable, Comparable<Item> {
    companion object CREATOR : Parcelable.Creator<Item> {
        val TAG = "Item"
        override fun createFromParcel(parcel: Parcel): Item {
            return Item(parcel)
        }

        override fun newArray(size: Int): Array<Item?> {
            return arrayOfNulls(size)
        }

        /*
        fun createDefaultTask() : Task {
            return Task(-1, "", "", "","","", "", "")
        }*/
    }

    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString() ?: "",
        ItemType.valueOf(
            parcel.readString() ?: ItemType.PRODUCT.name
        ), // Convertir el nombre del enum de nuevo a enum
        parcel.readDouble(),
        parcel.readByte() != 0.toByte(),
        parcel.readString() ?: "",
        parcel.readParcelable(Uri::class.java.classLoader)
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(name)
        parcel.writeString(type.name)
        parcel.writeDouble(rate)
        parcel.writeByte(if (isTaxable) 1 else 0) // 1 si es true, 0 si es false
        parcel.writeString(description)
        parcel.writeParcelable(imageUri, flags)
    }

    override fun describeContents(): Int {
        return 0
    }
=======
    ): Serializable, Comparable<Item> {
>>>>>>> 6f0a7a0d251dcaa6309bddafc2dceb1b382b14f8

    override fun compareTo(other: Item): Int {
        return name.compareTo(other.name)
    }
}

