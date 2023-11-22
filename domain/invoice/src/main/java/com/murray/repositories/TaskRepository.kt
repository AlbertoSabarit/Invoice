package com.murray.repositories




class TaskRepository private constructor() {
    companion object {
        val dataSet: MutableList<com.murray.entities.tasks.Task> = initDataSetTask()

        private fun initDataSetTask(): MutableList<com.murray.entities.tasks.Task> {
            var dataset: MutableList<com.murray.entities.tasks.Task> = ArrayList()
            dataset.add(
                com.murray.entities.tasks.Task(
                    "Citación", "Antonio García", "Privado", "Modificado"
                )
            )
            dataset.add(
                com.murray.entities.tasks.Task(
                    "Visita fábrica", "Estela Perez", "Llamada", "Vencido"
                )
            )
            dataset.add(
                com.murray.entities.tasks.Task(
                    "Ver presupuesto", "Alejandro Castaño", "Visita", "Pendiente"
                )
            )
            dataset.add(
                com.murray.entities.tasks.Task(
                    "Cancelar visita", "Fernando Carmona", "Visita", "Modificado"
                )
            )
            dataset.add(
                com.murray.entities.tasks.Task(
                    "Agendar", "Marina Rey", "Llamada", "Vencido"
                )
            )
            dataset.add(
                com.murray.entities.tasks.Task(
                    "Citación", "Daniel Hernandez", "Visita", "Vencido"
                )
            )
            dataset.add(
                com.murray.entities.tasks.Task(
                    "Agendar", "Cristiano Ronaldo", "Privado", "Vencido"
                )
            )
            dataset.add(
                com.murray.entities.tasks.Task(
                    "Ver informe", "Lucia Cabrera", "Privado", "Modificado"
                )
            )
            dataset.add(
                com.murray.entities.tasks.Task(
                    "Ver resultados", "Mateo Chupetón", "Privado", "Pendiente"
                )
            )
            dataset.add(
                com.murray.entities.tasks.Task(
                    "Citación", "Federico Valverde", "Visita", "Pendiente"
                )
            )
            dataset.add(
                com.murray.entities.tasks.Task(
                    "Agendar", "David García", "Llamada", "Vencido"
                )
            )
            return dataset
        }
    }
}