package com.murray.entities.email

class Email(var value: String) {
    private var email:String = value

    public fun getEmail():String{
        return email
    }
}