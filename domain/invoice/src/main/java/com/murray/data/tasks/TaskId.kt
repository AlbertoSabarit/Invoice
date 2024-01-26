package com.murray.data.tasks

import android.os.Parcel
import android.os.Parcelable
import com.murray.data.base.UniqueId

data class TaskId(override var value : Int) : UniqueId(value), Parcelable{
    constructor(parcel: Parcel) : this(parcel.readInt()) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(value)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<TaskId> {
        override fun createFromParcel(parcel: Parcel): TaskId {
            return TaskId(parcel)
        }

        override fun newArray(size: Int): Array<TaskId?> {
            return arrayOfNulls(size)
        }
    }
}