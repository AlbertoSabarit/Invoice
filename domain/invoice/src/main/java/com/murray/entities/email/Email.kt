package com.murray.entities.email

import android.os.Parcel
import android.os.Parcelable

class Email(var value: String) : Parcelable {

    constructor(parcel: Parcel) : this(parcel.readString()!!)

    constructor() : this("")

    fun getEmail(): String {
        return value
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
