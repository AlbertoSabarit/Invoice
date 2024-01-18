package com.murray.entities.base

open class Entity(open val id: Int){
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Entity

        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        return id
    }
}
