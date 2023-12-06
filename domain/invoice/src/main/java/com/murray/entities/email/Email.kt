package com.murray.entities.email

class Email(val value: String) {
    private val email:String = value

    public fun getEmail():String{
        return email
    }
}