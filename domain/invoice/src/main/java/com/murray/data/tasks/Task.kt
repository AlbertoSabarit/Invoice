package com.murray.data.tasks

import android.os.Parcel
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.murray.data.accounts.Email
import com.murray.data.converter.CustomerTypeConverter
import com.murray.data.customers.Customer

@Entity(
    tableName = "task",
    foreignKeys = [ForeignKey(
        entity = Customer::class,
        parentColumns = ["id"],
        childColumns = ["cliente"],
        onDelete = ForeignKey.RESTRICT,
        onUpdate = ForeignKey.CASCADE
    )],
    indices = [Index("cliente")]
)
data class Task(
    var titulo: String,
    @TypeConverters(CustomerTypeConverter::class)
    var cliente: Customer,
    var tipoTarea: String,
    var fechaCreacion: String,
    var fechaFin: String,
    var estado: String,
    var descripcion: String
) : Comparable<Task>, Parcelable {

    @PrimaryKey(autoGenerate = true)
    var id: Int = 0

    constructor(parcel: Parcel) : this(
        parcel.readString() ?: "",
        parcel.readParcelable(Customer::class.java.classLoader) ?: Customer(),
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: ""
    ) {
        id = parcel.readInt()
    }

    constructor() : this("", Customer(), "", "", "", "", "")

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(titulo)
        parcel.writeParcelable(cliente, flags)
        parcel.writeString(tipoTarea)
        parcel.writeString(fechaCreacion)
        parcel.writeString(fechaFin)
        parcel.writeString(estado)
        parcel.writeString(descripcion)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Task> {
        val TAG = "Task"
        override fun createFromParcel(parcel: Parcel): Task {
            return Task(parcel)
        }

        override fun newArray(size: Int): Array<Task?> {
            return arrayOfNulls(size)
        }
    }

    override fun compareTo(other: Task): Int {
        return cliente.name.compareTo(other.cliente.name)
    }
}
