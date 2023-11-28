package com.murray.entities.accounts

sealed class AccountException (message : String = ""): RuntimeException(message){
    class InvalidEmailFormat : AccountException ("Email con formato inválido")
}