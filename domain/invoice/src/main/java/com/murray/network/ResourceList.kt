package com.murray.network

import java.lang.Exception

sealed class ResourceList() {
    data class Success<T>(var data: ArrayList<T>?) : ResourceList()
    data class Error(var exception: Exception) : ResourceList()

}