package com.murray.repositories

import com.murray.entities.tasks.Task


class TaskRepository private constructor() {
    companion object {
        val dataSet: MutableList<Task> = initDataSetTask()

        private fun initDataSetTask(): MutableList<Task> {
            var dataset: MutableList<Task> = ArrayList()
            dataset.add(Task(
                    "Citación", "Antonio García", "Privado", "Modificado"
                )
            )
            dataset.add(Task(
                    "Visita fábrica", "Estela Perez", "Llamada", "Vencido"
                )
            )
            dataset.add(Task(
                    "Ver presupuesto", "Alejandro Castaño", "Visita", "Pendiente"
                )
            )
            dataset.add(Task(
                    "Cancelar visita", "Fernando Carmona", "Visita", "Modificado"
                )
            )
            dataset.add(Task(
                    "Agendar", "Marina Rey", "Llamada", "Vencido"
                )
            )
            dataset.add(Task(
                    "Citación", "Daniel Hernandez", "Visita", "Vencido"
                )
            )
            dataset.add(Task(
                    "Agendar", "Cristiano Ronaldo", "Privado", "Vencido"
                )
            )
            dataset.add(Task(
                    "Ver informe", "Lucia Cabrera", "Privado", "Modificado"
                )
            )
            dataset.add(Task(
                    "Ver resultados", "Mateo Chupetón", "Privado", "Pendiente"
                )
            )
            dataset.add(Task(
                    "Citación", "Federico Valverde", "Visita", "Pendiente"
                )
            )
            dataset.add(Task(
                    "Agendar", "David García", "Llamada", "Vencido"
                )
            )
            return dataset
        }
    }
}