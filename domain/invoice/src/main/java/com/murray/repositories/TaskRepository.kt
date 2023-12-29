package com.murray.repositories

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
            dataSet.add(
                Task(
                    Task.lastId++,
                    "Citación",
                    "Alberto Sabarit",
                    "Privado",
                    "10/11/2023",
                    "25/12/2023",
                    "Modificado",
                    "Cita privada con Antonio"
                )
            )
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