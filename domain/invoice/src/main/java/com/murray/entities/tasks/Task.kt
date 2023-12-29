package com.murray.entities.tasks

import android.os.Parcel
import android.os.Parcelable

data class Task(
    var id: Int,
    var titulo: String,
    var nombre: String,
    var tipoTarea: String,
    var fechaCreacion: String,
    var fechaFin: String,
    var estado: String,
    var descripcion: String
) : Parcelable, Comparable<Task> {

    override fun compareTo(other: Task): Int {
        return nombre.compareTo(other.nombre)
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

        fun createDefaultTask() : Task{
            return Task(-1, "", "", "","","", "", "")
        }
    }

    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(titulo)
        parcel.writeString(nombre)
        parcel.writeString(tipoTarea)
        parcel.writeString(fechaCreacion)
        parcel.writeString(fechaFin)
        parcel.writeString(estado)
        parcel.writeString(descripcion)
    }

    override fun describeContents(): Int {
        return 0
    }
}
