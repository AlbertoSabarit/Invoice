package com.murray.data.base

abstract class UniqueId(open val value:Any) {
    override fun equals(other: Any?): Boolean {
        if (this === other)
            return true

        other as UniqueId

        if (value != other.value)
            return false

        return true
    }

    override fun hashCode(): Int {
        return value.hashCode()
    }

    override fun toString(): String {
        return "$value"
    }


}