package com.murray.data.customers

import android.os.Parcel
import android.os.Parcelable
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.murray.data.accounts.Email
import com.murray.data.base.Entity
import com.murray.data.converter.EmailTypeConverter

@androidx.room.Entity(tableName = "customer")
data class Customer(
    @PrimaryKey
    override val id: Int,
    var name: String,
    @TypeConverters(EmailTypeConverter::class)
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

        fun create(id: Int,
                   name: String,
                   email: Email,
                   phone: Int?,
                   city: String?,
                   address: String?
        ): Customer {
            return Customer(id = id, name = name, email = email, phone = phone, city = city, address = address)
        }
    }
}
