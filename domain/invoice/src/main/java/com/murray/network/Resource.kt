package com.murray.network

import java.lang.Exception

sealed class Resource {

    //data class Success<T, E>(var data: T, var response: E) : Resource()
    /**
     * Esta clase sellada representa la respuesta de un servicio API REST, firebase
     * donde se declara la clase Error que almacenará los errores de la infraestructura
     * y el caso de exito sea una coleccion de datos
     */
    data class Success<T>(var data: Collection<T>) : Resource()
    data class Error(var exception: Exception) : Resource()

}