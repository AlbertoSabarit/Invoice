package com.murray.data.customers

import android.os.Parcel
import com.murray.data.accounts.Email
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class CustomerTest {
    @Test
    fun `pruebaParcelable_EscribirLeer`() {

        val clienteOriginal = Customer(
            "Alejandro Valle",
            Email("alejandro@valle.com"),
            123456789,
            "Torre del mar",
            "Mi calle"
        )
        clienteOriginal.id = 123


        val parcel = mock(Parcel::class.java)

        clienteOriginal.writeToParcel(parcel, 0)

        parcel.setDataPosition(0)

        val clienteDesdeParcel = Customer.CREATOR.createFromParcel(parcel)

        assertEquals(clienteOriginal, clienteDesdeParcel)
    }

    @Test
    fun `pruebaConstructorPorDefecto`() {
        val clientePorDefecto = Customer()
        assertEquals("", clientePorDefecto.name)
        assertEquals(Email("").value, clientePorDefecto.email.value)
        assertEquals(null, clientePorDefecto.phone)
        assertEquals(null, clientePorDefecto.city)
        assertEquals(null, clientePorDefecto.address)
    }

    @Test
    fun `pruebaGettersYSetters`() {
        val cliente = Customer()
        cliente.name = "Nuevo Cliente"
        assertEquals("Nuevo Cliente", cliente.name)

        val email = Email("nuevo@example.com")
        cliente.email = email
        assertEquals(email, cliente.email)

        cliente.phone = 123456789
        assertEquals(123456789, cliente.phone)

        cliente.city = "Nueva Ciudad"
        assertEquals("Nueva Ciudad", cliente.city)

        cliente.address = "Nueva Dirección"
        assertEquals("Nueva Dirección", cliente.address)
    }
}