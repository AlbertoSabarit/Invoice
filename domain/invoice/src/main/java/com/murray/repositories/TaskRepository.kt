package com.murray.repositories

import com.murray.entities.accounts.User
import com.murray.entities.tasks.Task
import com.murray.network.Resource
import com.murray.network.ResourceList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import java.lang.Exception


class TaskRepository private constructor() {
    companion object {
        val dataSet: MutableList<Task> = mutableListOf()

        init {
            initDataSetTask()
        }

        private fun initDataSetTask(): MutableList<Task> {
            dataSet.add(Task(
                    "Citación", "Antonio García", "Privado", "Modificado", "Cita privada con Antonio"
                )
            )
            dataSet.add(Task(
                    "Visita fábrica", "Estela Perez", "Llamada", "Vencido", "Visita con Estela"
                )
            )
            dataSet.add(Task(
                    "Ver presupuesto", "Alejandro Castaño", "Visita", "Pendiente", "Reunión para presupuesto Alejandro"
                )
            )
            dataSet.add(Task(
                    "Cancelar visita", "Fernando Carmona", "Visita", "Modificado", "Cancelar la visita con Fernando por problemas"
                )
            )
            dataSet.add(Task(
                    "Agendar", "Marina Rey", "Llamada", "Vencido", "Agendar una quedada con Marina"
                )
            )
            dataSet.add(Task(
                    "Citación", "Daniel Hernandez", "Visita", "Vencido", "Cita privada con Daniel"
                )
            )
            dataSet.add(Task(
                    "Agendar", "Luciano Torres", "Privado", "Vencido", "Agendar a Luciano para hablar de asuntos laborales"
                )
            )
            dataSet.add(Task(
                    "Ver informe", "Lucia Cabrera", "Privado", "Modificado", "Modificar informe de Lucia"
                )
            )
            dataSet.add(Task(
                    "Ver resultados", "Alicia Dominguez", "Privado", "Pendiente", "Cita privada con Alicia"
                )
            )
            dataSet.add(Task(
                    "Citación", "Federico Valverde", "Visita", "Pendiente", "Citar para renovar a Federico"
                )
            )
            dataSet.add(Task(
                    "Agendar", "David García", "Llamada", "Vencido", "David va a la calle"
                )
            )
            return dataSet
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

        suspend fun getTaskList(): ResourceList{
            return withContext(Dispatchers.IO) {
                delay(2000)
                when {
                    dataSet.isEmpty()-> ResourceList.Error(Exception("No hay datos"))
                    else -> ResourceList.Success(dataSet as ArrayList<Task>)
                }
            }
        }
    }
}