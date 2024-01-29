package com.murray.data.customers

import android.os.Parcel
import android.os.Parcelable
import com.murray.data.accounts.Email
import com.murray.data.base.Entity

data class Customer(
    override val id: Int,
    var name: String,
    var email: Email,
    var phone: Int?,
    var city: String?,
    var address: String?
) : Parcelable, Entity(id) {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString()!!,
        parcel.readParcelable(Email::class.java.classLoader)!!,
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readString(),
        parcel.readString()
    )

    constructor() : this(0, "", Email(""), null, null, null)

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(name)
        parcel.writeParcelable(email, flags)
        parcel.writeValue(phone)
        parcel.writeString(city)
        parcel.writeString(address)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Customer> {
        override fun createFromParcel(parcel: Parcel): Customer {
            return Customer(parcel)
        }

        override fun newArray(size: Int): Array<Customer?> {
            return arrayOfNulls(size)
        }
    }
}