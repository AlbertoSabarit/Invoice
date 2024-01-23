package com.murray.data.accounts

import android.os.Parcel
import android.os.Parcelable
import java.util.regex.Pattern

/**
 * Esta clase comprueba que el email cumple el patrón establecido en el metodo compile
 * En caso contrario lanza excepción
 */
class Email(var value: String) : Parcelable {

    private val pattern = Pattern.compile("^\\S+@\\S+\\.\\S+$")

    var error: String? = null
        private set

    fun getEmail() : String{
        return value
    }
    constructor(parcel: Parcel) : this(parcel.readString()!!) {
    }

    init {
        if (value.isNullOrEmpty() || !pattern.matcher(value).matches()) {
            error = "Formato de correo electrónico inválido"
        }
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(value)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Email> {
        override fun createFromParcel(parcel: Parcel): Email {
            return Email(parcel)
        }

        override fun newArray(size: Int): Array<Email?> {
            return arrayOfNulls(size)
        }
    }
}