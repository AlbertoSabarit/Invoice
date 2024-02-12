package com.murray.data.invoices

import android.os.Parcel
import android.os.Parcelable
import com.murray.data.customers.Customer
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Ignore
import androidx.room.Index
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.murray.data.converter.CustomerTypeConverter

@Entity(tableName = "invoice",
    foreignKeys = [ForeignKey(
        entity = Customer::class,
        parentColumns = ["id"],
        childColumns = ["cliente"],
        onDelete = ForeignKey.RESTRICT,
        onUpdate = ForeignKey.CASCADE
    )],
    indices = [Index("cliente")]
)
data class Invoice(
    @TypeConverters(CustomerTypeConverter::class)
    var cliente: Customer,
    var fcreacion: String,
    var fvencimiento: String,
    @Ignore
    var lineItems: ArrayList<LineItems>
) : Parcelable, Comparable<Invoice> {

    @PrimaryKey(autoGenerate = true)
    var id: Int = 0

    constructor() : this(Customer(), "", "", ArrayList())


    constructor(parcel: Parcel) : this(
        parcel.readParcelable(Customer::class.java.classLoader)!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.createTypedArrayList(LineItems)!!
    ) {
        id = parcel.readInt()
    }


    override fun compareTo(other: Invoice): Int {
        return fcreacion.compareTo(other.fcreacion)
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeParcelable(cliente, flags)
        parcel.writeString(fcreacion)
        parcel.writeString(fvencimiento)
        parcel.writeTypedList(lineItems)
        parcel.writeInt(id)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Invoice> {
        val TAG = "Invoice"
        override fun createFromParcel(parcel: Parcel): Invoice {
            return Invoice(parcel)
        }

        override fun newArray(size: Int): Array<Invoice?> {
            return arrayOfNulls(size)
        }

        fun createDefaultInvoice(): Invoice {
            return Invoice(Customer(), "", "", ArrayList())
        }
    }
}
