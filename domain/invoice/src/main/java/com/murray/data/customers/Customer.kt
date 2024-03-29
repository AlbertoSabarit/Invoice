package com.murray.data.customers

import android.os.Parcel
import android.os.Parcelable
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.murray.data.accounts.Email
import com.murray.data.converter.EmailTypeConverter

@androidx.room.Entity(tableName = "customer")
data class Customer(
    var name: String,
    @TypeConverters(EmailTypeConverter::class)
    var email: Email,
    var phone: Int?,
    var city: String?,
    var address: String?
) : Parcelable{

    @PrimaryKey(autoGenerate = true)
    var id: Int = 0

    constructor(parcel: Parcel) : this(
        parcel.readString() ?: "",
        parcel.readParcelable(Email::class.java.classLoader) ?: Email(""),
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readString(),
        parcel.readString()
    ){
        id = parcel.readInt()
    }

    constructor() : this( "", Email(""), null, null, null)

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
        parcel.writeParcelable(email, flags)
        parcel.writeValue(phone)
        parcel.writeString(city)
        parcel.writeString(address)
        parcel.writeInt(id)
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
            val customer = Customer(name = name, email = email, phone = phone, city = city, address = address)
            customer.id = id
            return customer
        }
    }
}