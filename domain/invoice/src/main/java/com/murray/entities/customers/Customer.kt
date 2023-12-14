package com.murray.entities.customers

import com.murray.entities.email.Email

data class Customer(val id:Int, var name:String, var email:Email, var phone:Int?, var city:String?, var address:String?) {
}
