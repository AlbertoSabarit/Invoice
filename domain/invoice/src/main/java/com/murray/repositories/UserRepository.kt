package com.murray.repositories

import com.murray.entities.accounts.User
import com.murray.network.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import java.lang.Exception

/**
 * Esta clase es accesible en todo el proyecto. No se puede crear objetos de esta clase
 * constructor privado. Y tiene nu objeto que contiene el listado de usuarios
 */
class UserRepository private constructor() {

    companion object {
        val dataSet: MutableList<User> = mutableListOf()

        init {
            initDataSetUser()
        }

        private fun initDataSetUser(){
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
        }

        /**
         * La funcion que se pregunta a Firebase/Room (Sqlite) por el usuario
         */
        suspend fun login(email: String, password: String) : Resource {
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