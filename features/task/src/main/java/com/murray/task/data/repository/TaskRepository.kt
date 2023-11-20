package com.murray.task.data.repository


class TaskRepository private constructor() {

    companion object {
        val dataSet: MutableList<com.murray.task.data.model.ListaTarea> = initDataSetUser()

        private fun initDataSetUser(): MutableList<com.murray.task.data.model.ListaTarea> {
            var dataset: MutableList<com.murray.task.data.model.ListaTarea> = ArrayList()
            dataset.add(
                com.murray.task.data.model.ListaTarea(
                    "Citación", "Antonio García", "Privado", "Modificado"
                )
            )
            dataset.add(
                com.murray.task.data.model.ListaTarea(
                    "Visita fábrica", "Estela Perez", "Llamada", "Vencido"
                )
            )
            dataset.add(
                com.murray.task.data.model.ListaTarea(
                    "Ver presupuesto", "Alejandro Castaño", "Visita", "Pendiente"
                )
            )
            dataset.add(
                com.murray.task.data.model.ListaTarea(
                    "Cancelar visita", "Fernando Carmona", "Visita", "Modificado"
                )
            )
            dataset.add(
                com.murray.task.data.model.ListaTarea(
                    "Agendar", "Marina Rey", "Llamada", "Vencido"
                )
            )
            dataset.add(
                com.murray.task.data.model.ListaTarea(
                    "Citación", "Daniel Hernandez", "Visita", "Vencido"
                )
            )
            dataset.add(
                com.murray.task.data.model.ListaTarea(
                    "Agendar", "Cristiano Ronaldo", "Privado", "Vencido"
                )
            )
            dataset.add(
                com.murray.task.data.model.ListaTarea(
                    "Ver informe", "Lucia Cabrera", "Privado", "Modificado"
                )
            )
            dataset.add(
                com.murray.task.data.model.ListaTarea(
                    "Ver resultados", "Mateo Chupetón", "Privado", "Pendiente"
                )
            )
            dataset.add(
                com.murray.task.data.model.ListaTarea(
                    "Citación", "Federico Valverde", "Visita", "Pendiente"
                )
            )
            dataset.add(
                com.murray.task.data.model.ListaTarea(
                    "Agendar", "David García", "Llamada", "Vencido"
                )
            )
            return dataset
        }
    }
}