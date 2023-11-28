package com.murray.repositories

import com.murray.entities.tasks.Task
import com.murray.network.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import java.lang.Exception


class TaskRepository private constructor() {
    companion object {
        val dataSet: MutableList<Task> = initDataSetTask()

        private fun initDataSetTask(): MutableList<Task> {
            var dataset: MutableList<Task> = ArrayList()
            dataset.add(Task(
                    "Citación", "Antonio García", "Privado", "Modificado", "Cita privada con Antonio"
                )
            )
            dataset.add(Task(
                    "Visita fábrica", "Estela Perez", "Llamada", "Vencido", "Visita con Estela"
                )
            )
            dataset.add(Task(
                    "Ver presupuesto", "Alejandro Castaño", "Visita", "Pendiente", "Reunión para presupuesto Alejandro"
                )
            )
            dataset.add(Task(
                    "Cancelar visita", "Fernando Carmona", "Visita", "Modificado", "Cancelar la visita con Fernando por problemas"
                )
            )
            dataset.add(Task(
                    "Agendar", "Marina Rey", "Llamada", "Vencido", "Agendar una quedada con Marina"
                )
            )
            dataset.add(Task(
                    "Citación", "Daniel Hernandez", "Visita", "Vencido", "Cita privada con Daniel"
                )
            )
            dataset.add(Task(
                    "Agendar", "Luciano Torres", "Privado", "Vencido", "Agendar a Luciano para hablar de asuntos laborales"
                )
            )
            dataset.add(Task(
                    "Ver informe", "Lucia Cabrera", "Privado", "Modificado", "Modificar informe de Lucia"
                )
            )
            dataset.add(Task(
                    "Ver resultados", "Alicia Dominguez", "Privado", "Pendiente", "Cita privada con Alicia"
                )
            )
            dataset.add(Task(
                    "Citación", "Federico Valverde", "Visita", "Pendiente", "Citar para renovar a Federico"
                )
            )
            dataset.add(Task(
                    "Agendar", "David García", "Llamada", "Vencido", "David va a la calle"
                )
            )
            return dataset
        }

        suspend fun createTask(titulo: String, descripcion: String) : Resource {
            //Este codigo se ejecuta en un hilo especifico para oepraciones entrada/salida (IO)
            withContext(Dispatchers.IO){
                delay(2000)
                //Se ejecutará el codigo de consulta a Firebase que puede tardar mas de 5sg y en ese caso se obtiene
                //el error ARN(Android Not Responding) porque puede bloquear la vista
            }
            return Resource.Error(Exception("El password es incorrecto"))
        }
    }
}