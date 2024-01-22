package com.murray.data.accounts

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Al utilizar data class se implementa de forma automatica el metodo equals, toString, copy y HashCode
 * teniendo en cuenta las propiedades declaradas en el constructor primario/principal
 */

@Entity
open class User(@PrimaryKey var name: String, var surname: String, var email: Email): Comparable<User> {
    override fun compareTo(other: User): Int {
        return name.compareTo(other.name)
    }
}
