package com.murray.repositories

import com.murray.entities.accounts.Account
import com.murray.entities.accounts.AccountState
import com.murray.entities.accounts.Email
import com.murray.entities.accounts.User
import com.murray.network.Resource
import com.murray.network.ResourceList
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
        var dataSet: MutableList<User> = mutableListOf()

        init {
            initDataSetUser()
        }

        private fun initDataSetUser(): MutableList<User> {
            dataSet.add(
                User(
                    "Alberto",
                    "Sabarit",
                    "albertosabarit@iesportada.org"
                )
            )
            dataSet.add(
                User(
                    "Ender",
                    "Watts",
                    "enderwatts@iesportada.org"
                )
            )
            dataSet.add(
                User(
                    "Kateryna",
                    "Nikitenko",
                    "katerynanikitenko@iesportada.org"
                )
            )
            dataSet.add(
                User(
                    "Alejandro",
                    "Valle",
                    "alevalle@iesportada.org"
                )
            )
            return dataSet
        }

        /**
         * La funcion que se pregunta a Firebase/Room (Sqlite) por el usuario
         */
        suspend fun login(email: String, password: String): Resource {
            //Este codigo se ejecuta en un hilo especifico para oepraciones entrada/salida (IO)
            withContext(Dispatchers.IO) {
                delay(2000)
                //Se ejecutará el codigo de consulta a Firebase que puede tardar mas de 5sg y en ese caso se obtiene
                //el error ARN(Android Not Responding) porque puede bloquear la vista
            }
            return Resource.Success(
                data = Account.create(
                    1,
                    Email("murray@gmail.com"),
                    "12345678",
                    "Murray",
                    AccountState.VERIFIED
                )
            )
        }

        /**
         * Esta función simula una consulta asincrona y devuelve el conjunto de usuarios de la aplicación
         */
        suspend fun getUserList(): ResourceList {
            return withContext(Dispatchers.IO) {
                delay(2000)
                when {
                    dataSet.isEmpty() -> ResourceList.Error(Exception("No hay datos"))
                    else -> ResourceList.Success(dataSet as ArrayList<User>)
                }
            }
        }
    }
}