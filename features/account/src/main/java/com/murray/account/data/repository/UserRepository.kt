package com.murray.account.data.repository

/**
 * Esta clase es accesible en todo el proyecto. No se puede crear objetos de esta clase
 * constructor privado. Y tiene nu objeto que contiene el listado de usuarios
 */
class UserRepository private constructor() {

    companion object {
        val dataSet: MutableList<com.murray.account.data.model.User> = initDataSetUser()

        private fun initDataSetUser(): MutableList<com.murray.account.data.model.User> {
            var dataset: MutableList<com.murray.account.data.model.User> = ArrayList()
            dataset.add(
                com.murray.account.data.model.User(
                    "Alberto",
                    "Sabarit",
                    "albertosabarit@iesportada.org"
                )
            )
            dataset.add(
                com.murray.account.data.model.User(
                    "Ender",
                    "Watts",
                    "enderwatts@iesportada.org"
                )
            )
            dataset.add(
                com.murray.account.data.model.User(
                    "Kateryna",
                    "Nikitenko",
                    "katerynanikitenko@iesportada.org"
                )
            )
            dataset.add(
                com.murray.account.data.model.User(
                    "Alejandro",
                    "Valle",
                    "alevalle@iesportada.org"
                )
            )
            return dataset
        }
    }
}