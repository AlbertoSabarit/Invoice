package com.murray.repositories

import com.murray.data.accounts.Email
import com.murray.data.customers.Customer
import com.murray.data.tasks.Task
import com.murray.data.tasks.TaskId
import com.murray.networkstate.Resource
import com.murray.networkstate.ResourceList
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

         /*   dataSet.add(
                Task(
                    TaskId(Task.lastId++) ,
                    "Citación",
                    Customer(
                        CustomerRepository.getNextId(),
                        "Alberto Sabarit",
                        Email("alberto@gmail.es"),
                        620400868,
                        "Rincón de la Victoria",
                        "Calle José María Doblas 4"
                    ),
                    "Privado",
                    "10/11/2023",
                    "25/12/2023",
                    "Modificado",
                    "Cita privada con Antonio"
                ),

                )*/
            return dataSet
        }

        suspend fun getTaskList(): ResourceList {
            return withContext(Dispatchers.IO) {
                delay(1000)
                when {
                    dataSet.isEmpty() -> ResourceList.NoData(Exception("No hay datos"))
                    else -> ResourceList.Success(dataSet as ArrayList<Task>)
                }
            }
        }

        suspend fun createTask(task: Task): Resource {
            return withContext(Dispatchers.IO) {
                delay(1000)
                if (dataSet.any { it.titulo == task.titulo }) {
                    Resource.Error(Exception("Esta tarea ya existe"))
                } else {
                    dataSet.add(task)
                    Resource.Success(dataSet)
                }
            }
        }

        fun removeFromList(task: Task) {
            dataSet.remove(task)
        }
    }
}