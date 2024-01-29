package com.murray.data.tasks

import android.os.Parcel
import android.os.Parcelable
import com.murray.data.base.Entity
import com.murray.data.customers.Customer

data class Task(
    var id: TaskId,
    var titulo: String,
    var cliente: Customer,
    var tipoTarea: String,
    var fechaCreacion: String,
    var fechaFin: String,
    var estado: String,
    var descripcion: String
) : Comparable<Task>, Parcelable{
    constructor(parcel: Parcel) : this(
        parcel.readParcelable(TaskId::class.java.classLoader)!!,
        parcel.readString()!!,
        parcel.readParcelable(Customer::class.java.classLoader)!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeParcelable(id, flags)
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
        var lastId: Int = 1
        override fun createFromParcel(parcel: Parcel): Task {
            return Task(parcel)
        }

        override fun newArray(size: Int): Array<Task?> {
            return arrayOfNulls(size)
        }

        fun createDefaultTask(): Task {
            return Task(TaskId(-1), "", Customer(), "", "", "", "", "")
        }
    }

    override fun compareTo(other: Task): Int {
        return cliente.name.compareTo(other.cliente.name)
    }


}