package com.murray.entities.invoices

import android.os.Parcel
import android.os.Parcelable
import com.murray.data.items.Item

data class InvoiceLine(
    val item: Item,
    val count: Int,
    val iva: Int,
    val price: Double
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readParcelable(Item::class.java.classLoader)!!,
        parcel.readInt(),
        parcel.readInt(),
        parcel.readDouble()
    )

    constructor():this(Item(), 0, 0, 0.00)
    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeParcelable(item, flags)
        parcel.writeInt(count)
        parcel.writeInt(iva)
        parcel.writeDouble(price)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<InvoiceLine> {
        override fun createFromParcel(parcel: Parcel): InvoiceLine {
            return InvoiceLine(parcel)
        }

        override fun newArray(size: Int): Array<InvoiceLine?> {
            return arrayOfNulls(size)
        }
    }
}
