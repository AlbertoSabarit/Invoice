package com.murray.data.customers

import android.os.Parcel
import com.murray.data.accounts.Email
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.whenever

@RunWith(MockitoJUnitRunner::class)
class CustomerTest {
    @Test
    fun pruebaEscribirParcel() {
        val customer = Customer(
            "Alejandro Valle",
            Email("alejandro@valle.com"),
            123456789,
            "Torre del Mar",
            "Mi calle"
        )
        customer.id = 1

        val parcelMock = mock(Parcel::class.java)
        customer.writeToParcel(parcelMock, 0)

        verify(parcelMock).writeInt(customer.id)
        verify(parcelMock).writeString(customer.name)
        verify(parcelMock).writeParcelable(customer.email, 0)
        verify(parcelMock).writeValue(customer.phone)
        verify(parcelMock).writeString(customer.city)
        verify(parcelMock).writeString(customer.address)
    }

    @Test
    fun pruebaConstructorPorDefecto() {
        val clientePorDefecto = Customer()
        assertEquals("", clientePorDefecto.name)
        assertEquals(Email("").value, clientePorDefecto.email.value)
        assertEquals(null, clientePorDefecto.phone)
        assertEquals(null, clientePorDefecto.city)
        assertEquals(null, clientePorDefecto.address)
    }

    @Test
    fun pruebaGettersYSetters() {
        val cliente = Customer()
        cliente.name = "Alejandro Valle"
        assertEquals("Alejandro Valle", cliente.name)

        val email = Email("alejandro@valle.com")
        cliente.email = email
        assertEquals(email, cliente.email)

        cliente.phone = 123456789
        assertEquals(123456789, cliente.phone)

        cliente.city = "Torre del Mar"
        assertEquals("Torre del Mar", cliente.city)

        cliente.address = "Mi calle"
        assertEquals("Mi calle", cliente.address)
    }
    @Test
    fun pruebaCrearCustomerConDatosProporcionados() {
        val id = 1
        val name = "Alejandro Valle"
        val email = Email("alejandro@valle.com")
        val phone = 123456789
        val city = "Torre del Mar"
        val address = "Mi calle"

        val customer = Customer.create(id, name, email, phone, city, address)

        assertEquals(id, customer.id)
        assertEquals(name, customer.name)
        assertEquals(email, customer.email)
        assertEquals(phone, customer.phone)
        assertEquals(city, customer.city)
        assertEquals(address, customer.address)
    }
    @Test
    fun pruebaCrearArrayNulo() {
        assertEquals(Customer.newArray(5), arrayOfNulls(5))
    }

    @Test
    fun pruebaCrearDesdeParce() {
        val id = 1
        val name = "Aloejandro Valle"
        val email = Email("alejandro@valle.com")
        val phone = 123456789
        val city = "Torre del Mar"
        val address = "Mi calle"
        val customer = Customer(name, email, phone, city, address)
        customer.id = id

        val parcelMock = mock(Parcel::class.java)
        whenever(parcelMock.readString()).thenReturn(name, city, address)
        whenever(parcelMock.readParcelable<Email>(Email::class.java.classLoader)).thenReturn(email)
        whenever(parcelMock.readValue(Int::class.java.classLoader)).thenReturn(phone)
        whenever(parcelMock.readInt()).thenReturn(id)

        val createdCustomer = Customer.CREATOR.createFromParcel(parcelMock)

        assertEquals(id, createdCustomer.id)
        assertEquals(name, createdCustomer.name)
        assertEquals(email, createdCustomer.email)
        assertEquals(phone, createdCustomer.phone)
        assertEquals(city, createdCustomer.city)
        assertEquals(address, createdCustomer.address)
    }
    @Test
    fun pruebaDescribeContents() {
        val customer = Customer()
        val result = customer.describeContents()
        assertEquals(0, result)
    }
}