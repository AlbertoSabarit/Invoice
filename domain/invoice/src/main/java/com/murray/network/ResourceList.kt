package com.murray.network

import java.lang.Exception

sealed class ResourceList() {

    data class NoData(var exception: Exception) : ResourceList()
    data class Success<T>(var data: ArrayList<T>?) : ResourceList()
}