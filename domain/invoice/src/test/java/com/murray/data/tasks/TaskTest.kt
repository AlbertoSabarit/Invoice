package com.murray.data.tasks

import android.os.Parcel
import com.murray.data.accounts.Email
import com.murray.data.customers.Customer
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class PruebaTarea {

    @Test
    fun `pruebaParcelable_EscribirLeer`() {
        // Crear un cliente ficticio
        val cliente = Customer()
        cliente.name = "Juan Pérez"

        // Crear una tarea ficticia
        val tareaOriginal = Task(
            "Título de la Tarea",
            cliente,
            "Tipo",
            "2024-02-26",
            "2024-02-27",
            "Pendiente",
            "Descripción"
        )
        tareaOriginal.id = 123

        // Mock del Parcel
        val parcel = mock(Parcel::class.java)

        // Simular escribir en el Parcel
        tareaOriginal.writeToParcel(parcel, 0)

        // Cambiar el estado del Parcel para lectura
        parcel.setDataPosition(0)

        // Simular leer desde el Parcel
        val tareaDesdeParcel = Task.CREATOR.createFromParcel(parcel)

        // Verificar la igualdad de los objetos
        assertEquals(tareaOriginal, tareaDesdeParcel)
    }

    @Test
    fun `pruebaCompararParaOrdenar`() {
        val cliente1 = Customer()
        cliente1.name = "Ana"
        val tarea1 = Task("Tarea 1", cliente1, "Tipo", "2024-02-26", "2024-02-27", "Pendiente", "Descripción")

        val cliente2 = Customer()
        cliente2.name = "Pedro"
        val tarea2 = Task("Tarea 2", cliente2, "Tipo", "2024-02-26", "2024-02-27", "Pendiente", "Descripción")

        assertEquals(-1, tarea1.compareTo(tarea2))
        assertEquals(1, tarea2.compareTo(tarea1))
    }

    @Test
    fun `pruebaConstructorPorDefecto`() {
        val tareaPorDefecto = Task()
        assertEquals("", tareaPorDefecto.titulo)
        //assertEquals(Customer(), tareaPorDefecto.cliente)
        assertEquals(Customer(name = "", email = Email(""), phone = null, city = "", address = ""), tareaPorDefecto.cliente)
        assertEquals("", tareaPorDefecto.tipoTarea)
        assertEquals("", tareaPorDefecto.fechaCreacion)
        assertEquals("", tareaPorDefecto.fechaFin)
        assertEquals("", tareaPorDefecto.estado)
        assertEquals("", tareaPorDefecto.descripcion)
    }

    @Test
    fun `pruebaGettersYSetters`() {
        val tarea = Task()
        tarea.titulo = "Nuevo Título de Tarea"
        assertEquals("Nuevo Título de Tarea", tarea.titulo)

        val cliente = Customer()
        cliente.name = "Nuevo Cliente"
        tarea.cliente = cliente
        assertEquals(cliente, tarea.cliente)

        tarea.tipoTarea = "Nuevo Tipo"
        assertEquals("Nuevo Tipo", tarea.tipoTarea)

        tarea.fechaCreacion = "2024-02-26"
        assertEquals("2024-02-26", tarea.fechaCreacion)

        tarea.fechaFin = "2024-02-27"
        assertEquals("2024-02-27", tarea.fechaFin)

        tarea.estado = "Nuevo Estado"
        assertEquals("Nuevo Estado", tarea.estado)

        tarea.descripcion = "Nueva Descripción"
        assertEquals("Nueva Descripción", tarea.descripcion)
    }
}
