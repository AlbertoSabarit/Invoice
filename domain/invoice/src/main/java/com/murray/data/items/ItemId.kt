package com.murray.data.items

import android.os.Parcel
import android.os.Parcelable
import com.murray.data.base.UniqueId

class ItemId(override var value : Int) : UniqueId(value), Parcelable{
    constructor(parcel: Parcel) : this(parcel.readInt()) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(value)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ItemId> {
        override fun createFromParcel(parcel: Parcel): ItemId {
            return ItemId(parcel)
        }

        override fun newArray(size: Int): Array<ItemId?> {
            return arrayOfNulls(size)
        }
    }
}
