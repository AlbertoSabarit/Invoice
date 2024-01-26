package com.murray.database.dao

import androidx.room.Dao
import androidx.room.ForeignKey
import androidx.room.Insert
import androidx.room.OnConflictStrategy.Companion.REPLACE
import androidx.room.Query
import androidx.room.Update
import com.murray.data.accounts.Account
import com.murray.data.accounts.BusinessProfile

@Dao
interface AccountDao {

    @Insert(onConflict = REPLACE)
    fun insert (account: Account) : Long

    /*@Update
    fun update (account: Account)

    @Query ("SELECT * FROM account")
    fun selectAll(): List<Account>*/


    @Query("SELECT * FROM account JOIN businessprofile ON account.businessProfile = businessProfile.id")
    fun loadAccountAndBusinessProfile(): Map<Account, BusinessProfile>
}