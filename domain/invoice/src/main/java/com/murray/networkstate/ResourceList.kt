package com.murray.networkstate

import java.lang.Exception

sealed class ResourceList() {

    data class NoData(var exception: Exception) : ResourceList()
    data class Success<T>(var data: ArrayList<T>?) : ResourceList()
}