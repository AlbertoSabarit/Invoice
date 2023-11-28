package com.murray.entities.accounts

import java.util.regex.Pattern

/**
 * Esta clase comprueba que el email cumple el patrón establecido en el metodo compile
 * En caso contrario lanza excepción
 */
class Email(val value: String) {

    private val pattern = Pattern.compile("")

    init {
        if (!pattern.matcher(value).matches())
            throw AccountException.InvalidEmailFormat()
    }
}