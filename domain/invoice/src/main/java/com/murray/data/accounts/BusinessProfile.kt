package com.murray.data.accounts

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "businessprofile")
data class BusinessProfile(
    @PrimaryKey val id: Int,
    val name: String = "",
    val address: String = "",
    val phoneNumber: String = ""
)
