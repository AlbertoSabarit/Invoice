package com.murray.data.converter

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.murray.data.accounts.AccountId

@ProvidedTypeConverter
class AccountIdTypeConverter {

    @TypeConverter
    fun toAccountId(value: Int): AccountId {
        return AccountId(value)
    }

    @TypeConverter
    fun fromAccountId(accountId: AccountId): Int {
        return accountId.value
    }
}