package com.murray.data.invoices

import android.os.Parcel
import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.murray.data.converter.InvoiceTypeConverter
import com.murray.data.converter.ItemTypeConverter
import com.murray.data.items.Item

@Entity(tableName = "line_items",
    foreignKeys = [ForeignKey(
        entity = Invoice::class,
        parentColumns = ["id"],
        childColumns = ["invoice_id"],
        onDelete = ForeignKey.CASCADE
    ),ForeignKey(
        entity = Item::class,
        parentColumns = ["id"],
        childColumns = ["item"],
        onDelete = ForeignKey.RESTRICT,
        onUpdate = ForeignKey.CASCADE
    )],
    indices = [Index("invoice_id"), Index("item")]
)
data class LineItems(
    @ColumnInfo(name = "invoice_id")
    var invoiceId: Int,
    @TypeConverters(ItemTypeConverter::class)
    val item: Item,
    val cantidad: Int,
    val price: Double,
    val descripcion: String,
    val iva: Int,
): Parcelable {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0



    constructor() : this(0, Item(), 0, 0.0,"", 0)
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readParcelable(Item::class.java.classLoader)!!,
        parcel.readInt(),
        parcel.readDouble(),
        parcel.readString()!!,
        parcel.readInt()
    ) {
        id = parcel.readInt()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(invoiceId)
        parcel.writeParcelable(item, flags)
        parcel.writeInt(cantidad)
        parcel.writeDouble(price)
        parcel.writeString(descripcion)
        parcel.writeInt(iva)
        parcel.writeInt(id)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<LineItems> {
        override fun createFromParcel(parcel: Parcel): LineItems {
            return LineItems(parcel)
        }

        override fun newArray(size: Int): Array<LineItems?> {
            return arrayOfNulls(size)
        }
    }
}
