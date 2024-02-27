package com.murray.data.accounts

import com.google.common.truth.Truth
import com.murray.data.base.UniqueId
import org.junit.Assert
import org.junit.Test

class AccountIdTest {

    @Test
    fun `A es igual a B`() {
        //Configuración
        val a = AccountId(2)
        val b = AccountId(2)

        Truth.assertThat(a).isEqualTo(b)
    }

    @Test
    fun `A es diferente a B`() {
        //Configuración
        val a = AccountId(2)
        val b = AccountId(3)

        Truth.assertThat(a).isNotEqualTo(b)
    }

    @Test
    fun `AccountId valor negativo`() {
        Assert.assertThrows(AccountException::class.java){
            AccountId(-1)
        }
    }

    @Test
    fun `casteo de clase`() {
        //Configuración
        val a = AccountId(2)
        Truth.assertThat(a).isInstanceOf(UniqueId::class.java)
    }

}