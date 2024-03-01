package com.murray.data.tasks

import android.os.Parcel
import com.murray.data.accounts.Email
import com.murray.data.customers.Customer
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.whenever

@RunWith(MockitoJUnitRunner::class)
class TaskTest {

    @Test
    fun pruebaEscribirParcel() {
        val task = Task(
            "Entrevista",
            Customer("Alberto", Email("alberto@gmail.com"), 620699858, "Malaga", "calle las minas"),
            "Privado",
            "27/02/2024",
            "28/02/2024",
            "Mi Pendiente",
            "Hablar con RRHH"
        )
        task.id = 1

        val parcelMock = mock(Parcel::class.java)
        task.writeToParcel(parcelMock, 0)

        verify(parcelMock).writeInt(task.id)
        verify(parcelMock).writeString(task.titulo)
        verify(parcelMock).writeParcelable(task.cliente, 0)
        verify(parcelMock).writeString(task.tipoTarea)
        verify(parcelMock).writeString(task.fechaCreacion)
        verify(parcelMock).writeString(task.fechaFin)
        verify(parcelMock).writeString(task.estado)
        verify(parcelMock).writeString(task.descripcion)

    }

    @Test
    fun pruebaConstructorPorDefecto() {
        val taskPorDefecto = Task()
        assertEquals("", taskPorDefecto.titulo)
        assertEquals(Customer().id, taskPorDefecto.id)
        assertEquals("", taskPorDefecto.tipoTarea)
        assertEquals("", taskPorDefecto.fechaCreacion)
        assertEquals("", taskPorDefecto.fechaFin)
        assertEquals("", taskPorDefecto.estado)
        assertEquals("", taskPorDefecto.descripcion)
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



    @Test
    fun pruebaCrearArrayNulo() {
        assertEquals(Task.newArray(7), arrayOfNulls(7))
    }


    @Test
    fun pruebaCrearDesdeParce() {
        val id = 1
        val titulo = "A por todas"
        val cliente =
            Customer("Alberto", Email("alberto@gmail.com"), 620699858, "Malaga", "calle las minas")
        val tipoTarea = "Llamada"
        val fechaCreacion = "01/03/2024"
        val fechaFin = "02/03/2024r"
        val estado = "Vencido"
        val descripcion = "Exito"

        val task = Task(
            titulo,
            cliente,
            tipoTarea,
            fechaCreacion,
            fechaFin,
            estado,
            descripcion
        )

        task.id = id

        val parcelMock = mock(Parcel::class.java)
        whenever(parcelMock.readString()).thenReturn( titulo,tipoTarea,fechaCreacion,fechaFin,estado,descripcion)
        whenever(parcelMock.readParcelable<Customer>(Customer::class.java.classLoader)).thenReturn(cliente)
        whenever(parcelMock.readInt()).thenReturn(id)

        val createdTask = Task.CREATOR.createFromParcel(parcelMock)

        assertEquals(id, createdTask.id)
        assertEquals(titulo, createdTask.titulo)
        assertEquals(cliente, createdTask.cliente)
        assertEquals(tipoTarea, createdTask.tipoTarea)
        assertEquals(fechaCreacion, createdTask.fechaCreacion)
        assertEquals(fechaFin, createdTask.fechaFin)
        assertEquals(estado, createdTask.estado)
        assertEquals(descripcion, createdTask.descripcion)
    }

    @Test
    fun pruebaDescribeContents() {
        val task = Task()
        val result = task.describeContents()
        assertEquals(0, result)
    }
    @Test
    fun pruebaTAG() {
        assertEquals("Task", Task.TAG)
    }

    @Test
    fun pruebaCompareTo() {
        val tarea1 = Task(
            "Tarea 1",
            Customer("Cliente 1", Email("cliente1@gmail.com"), 123456789, "Ciudad 1", "Dirección 1"),
            "Tipo 1",
            "2024-02-28",
            "2024-02-29",
            "Pendiente",
            "Descripción 1"
        )
        val tarea2 = Task(
            "Tarea 2",
            Customer("Cliente 2", Email("cliente2@gmail.com"), 987654321, "Ciudad 2", "Dirección 2"),
            "Tipo 2",
            "2024-02-27",
            "2024-02-28",
            "Completada",
            "Descripción 2"
        )

        assertEquals(-1, tarea1.compareTo(tarea2))

        assertEquals(1, tarea2.compareTo(tarea1))

        assertEquals(0, tarea1.compareTo(tarea1))
    }
}
