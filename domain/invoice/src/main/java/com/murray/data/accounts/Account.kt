package com.murray.data.accounts

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.Companion.CASCADE
import androidx.room.ForeignKey.Companion.RESTRICT
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.murray.data.converter.AccountIdTypeConverter
import com.murray.data.converter.EmailTypeConverter

@Entity(tableName = "account",
    foreignKeys = [ForeignKey( entity = BusinessProfile ::class,
        parentColumns = arrayOf("id"),
        childColumns = arrayOf("businessProfile"),
        onDelete = RESTRICT,
        onUpdate = CASCADE)])
class Account(
    @PrimaryKey
    @TypeConverters(AccountIdTypeConverter::class)
    val id: AccountId,
    @TypeConverters(EmailTypeConverter::class)
    val email: Email,
    val password: String?,
    val displayName: String?,
    var state: AccountState = AccountState.UNVERIFIED,
    val businessProfile: Int?,
){

  /*  @PrimaryKey(autoGenerate = true)
    var id :AccountId = 0*/

    /**
     * Al utilizar un objeto acompañante con una función y el constructor privado de la clase
     * garantizo el modo/restricciones que tenga al crear un objeto de la clase
     */


    companion object {
        fun create(
            id: AccountId,
            email: Email,
            password: String?,
            displayName: String?,
            businessProfile: Int?
        ): Account {
            return Account(
                id = id,
                email = email,
                password = password,
                displayName = displayName,
                state = AccountState.UNVERIFIED,
                businessProfile = businessProfile,
            )
        }

    }
}
