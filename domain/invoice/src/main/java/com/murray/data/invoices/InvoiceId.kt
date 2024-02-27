package com.murray.data.invoices

import android.os.Parcel
import android.os.Parcelable
import com.murray.data.base.UniqueId

class InvoiceId (override var value : Int) : UniqueId(value), Parcelable {
    constructor(parcel: Parcel) : this(parcel.readInt()) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(value)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<InvoiceId> {
        override fun createFromParcel(parcel: Parcel): InvoiceId {
            return InvoiceId(parcel)
        }

        override fun newArray(size: Int): Array<InvoiceId?> {
            return arrayOfNulls(size)
        }
    }
}