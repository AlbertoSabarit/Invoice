// Paquete correcto para un DAO
package com.murray.database.dao

import androidx.room.Dao
import androidx.room.ForeignKey
import androidx.room.Insert
import androidx.room.Query
import com.murray.data.accounts.BusinessProfile

@Dao
interface BusinessProfileDao {

    @Insert(onConflict = ForeignKey.RESTRICT)
    fun insert(businessProfile: BusinessProfile)

    @Query("SELECT * FROM businessprofile where id = :businessprofileId")
    fun selectBusinessProfile(businessprofileId: Int): BusinessProfile
}
