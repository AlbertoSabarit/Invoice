package com.murray.entities.accounts

import java.util.regex.Pattern

/**
 * Esta clase comprueba que el email cumple el patrón establecido en el metodo compile
 * En caso contrario lanza excepción
 */
class Email(val value: String) {

    //private val pattern = Pattern.compile("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")
    private val pattern = Pattern.compile("^\\S+@\\S+\\.\\S+$")

    init {
        if (!pattern.matcher(value).matches())
            throw AccountException.InvalidEmailFormat()
    }
}