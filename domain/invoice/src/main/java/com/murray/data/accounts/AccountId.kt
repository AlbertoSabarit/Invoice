package com.murray.data.accounts

import com.murray.data.base.UniqueId

data class AccountId(override val value : Int) : UniqueId(value){

    init{
        if(value <0)
            throw AccountException.InvalidId()
    }
}