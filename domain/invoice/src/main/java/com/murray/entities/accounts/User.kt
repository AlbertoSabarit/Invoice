package com.murray.entities.accounts

/**
 * Al utilizar data class se implementa de forma automatica el metodo equals, toString, copy y HashCode
 * teniendo en cuenta las propiedades declaradas en el constructor primario/principal
 */
data class User(var name: String, var surname: String, var email: String): Comparable<User> {
    override fun compareTo(other: User): Int {
        return name.compareTo(other.name)
    }
}
