package com.murray.repositories

import com.murray.data.accounts.Account
import com.murray.data.accounts.AccountId
import com.murray.data.accounts.AccountState
import com.murray.data.accounts.Email
import com.murray.data.accounts.User
import com.murray.networkstate.Resource
import com.murray.networkstate.ResourceList
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
          /*  dataSet.add(
                User(
                    "Alberto",
                    "Sabarit",
                    Email("blbertosabarit@iesportada.org")
                )
            )
            dataSet.add(
                User(
                    "Ender",
                    "Watts",
                    Email("enderwatts@iesportada.org")
                )
            )
            dataSet.add(
                User(
                    "Kateryna",
                    "Nikitenko",
                    Email("katerynanikitenko@iesportada.org")
                )
            )
            dataSet.add(
                User(
                    "Carlos",
                    "Valle",
                    Email("zlevalle@iesportada.org")
                )
            )
            dataSet.add(
                User(
                    "Javier",
                    "Zarcia",
                    Email("kavier@iesportada.org")
                )
            )
            dataSet.add(
                User(
                    "Manolo",
                    "Perez",
                    Email("manolo@iesportada.org")
                )
            )*/
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
                    AccountId(1),
                    Email("murray@gmail.com"),
                    "12345678",
                    "Murray",
                    AccountState.VERIFIED,
                    1
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
                    dataSet.isEmpty() -> ResourceList.NoData(Exception("No hay datos"))
                    else -> ResourceList.Success(dataSet as ArrayList<User>)
                }
            }
        }

        suspend fun createUser(user: User): Resource {
            return withContext(Dispatchers.IO) {
                delay(1000)
                if (dataSet.any { it.email.value == user.email.value}) {
                    Resource.Error(Exception("El usuario ya existe en la base de datos"))
                } else {
                    dataSet.add(user)
                    Resource.Success(dataSet)
                }
            }
        }
    }
}