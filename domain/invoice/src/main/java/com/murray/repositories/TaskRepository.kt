package com.murray.repositories

import com.murray.entities.accounts.User
import com.murray.entities.customers.Customer
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
                    "Citación", "Antonio García", "Privado","10/11/2023","25/12/2023", "Modificado", "Cita privada con Antonio"
                )
            )
            return dataSet
        }

        fun addTask(t: Task){
            dataSet.add(t)
        }

        suspend fun getTaskList(): ResourceList{
            return withContext(Dispatchers.IO) {
                //delay(2000)
                when {
                    dataSet.isEmpty()-> ResourceList.Error(Exception("No hay datos"))
                    else -> ResourceList.Success(dataSet as ArrayList<Task>)
                }
            }
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