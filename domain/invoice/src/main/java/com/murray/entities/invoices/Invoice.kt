package com.murray.entities.invoices

import android.os.Parcel
import android.os.Parcelable
import com.murray.entities.accounts.Entity

data class Invoice(
    override var id: Int,
    var cliente: String,
    var articulo: String,
    var fcreacion: String,
    var fvencimiento: String
) : Parcelable, Comparable<Invoice>, Entity(id) {
    companion object CREATOR : Parcelable.Creator<Invoice> {
        val TAG = "Invoice"
        var lastId: Int = 1
        override fun createFromParcel(parcel: Parcel): Invoice {
            return Invoice(parcel)
        }

        override fun newArray(size: Int): Array<Invoice?> {
            return arrayOfNulls(size)
        }

        fun createDefaultInvoice(): Invoice {
            return Invoice(-1, "", "", "", "")
        }
    }

    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(cliente)
        parcel.writeString(articulo)
        parcel.writeString(fcreacion)
        parcel.writeString(fvencimiento)

    }

    override fun describeContents(): Int {
        return 0
    }

    override fun compareTo(other: Invoice): Int {
        return fcreacion.compareTo(other.fcreacion)
    }
}
