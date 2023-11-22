package com.murray.repositories

import com.murray.entities.accounts.User

/**
 * Esta clase es accesible en todo el proyecto. No se puede crear objetos de esta clase
 * constructor privado. Y tiene nu objeto que contiene el listado de usuarios
 */
class UserRepository private constructor() {

    companion object {
        val dataSet: MutableList<User> = initDataSetUser()

        private fun initDataSetUser(): MutableList<User> {
            var dataset: MutableList<User> = ArrayList()
            dataset.add(
                User(
                    "Alberto",
                    "Sabarit",
                    "albertosabarit@iesportada.org"
                )
            )
            dataset.add(
                User(
                    "Ender",
                    "Watts",
                    "enderwatts@iesportada.org"
                )
            )
            dataset.add(
                User(
                    "Kateryna",
                    "Nikitenko",
                    "katerynanikitenko@iesportada.org"
                )
            )
            dataset.add(
                User(
                    "Alejandro",
                    "Valle",
                    "alevalle@iesportada.org"
                )
            )
            return dataset
        }
    }
}