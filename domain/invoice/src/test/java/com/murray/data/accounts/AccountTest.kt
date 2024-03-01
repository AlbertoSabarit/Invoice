package com.murray.data.accounts

import com.google.common.truth.Truth
import org.junit.Test

class AccountTest {

    //Configuración

    private val account = Account.create(
        id = AccountId(1),
        email = Email("alberto@gmail.com"),
        password = "12345678", displayName = "Alberto",
        businessProfile = null
    )

    @Test
    fun `cambiar a estado verified`(){
        val accountState = AccountState.VERIFIED
        val stateString = accountState.toString()

        Truth.assertThat(stateString).isEqualTo("VERIFIED")
    }

    @Test
    fun `cambiar a estado unverified`(){
        val accountState = AccountState.UNVERIFIED
        val stateString = accountState.toString()

        Truth.assertThat(stateString).isEqualTo("UNVERIFIED")
    }

    @Test
    fun `verificar el valor de ID`() {
        val accountId = AccountId(1)

        Truth.assertThat(account.id).isEqualTo(accountId)
    }

    @Test
    fun `verificar el valor de Email`() {
        val email = Email("alberto@gmail.com")

        Truth.assertThat(account.email.value).isEqualTo(email.value)
    }
}