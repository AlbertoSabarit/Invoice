package com.murray.entities.invoices

import android.os.Parcel
import android.os.Parcelable
import com.murray.data.base.Entity
import com.murray.data.customers.Customer

data class Invoice(
    override var id: Int,
    var cliente: Customer,
    val articulo:InvoiceLine,
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
            return Invoice(-1, Customer(), InvoiceLine(), "", "")
        }
    }

    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readParcelable(Customer::class.java.classLoader)!!,
        parcel.readParcelable(InvoiceLine::class.java.classLoader)!!,
        parcel.readString()!!,
        parcel.readString()!!
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeParcelable(cliente, flags)
        parcel.writeParcelable(articulo, flags)
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
