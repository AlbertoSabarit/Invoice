package com.murray.invoice

import android.app.Application
import android.content.Context
import androidx.datastore.preferences.preferencesDataStore
import com.moronlu18.invoice.ui.preferences.DataStorePreferencesRepository
import com.murray.invoice.data.preferences.UserPreferencesRepository

object Locator {
    private var application: Application? = null
    val requireApplication get() = application ?: error("Missing call : initWith(application)")

    fun initWith(application: Application) {
        this.application = application
    }

    private val Context.userStore by preferencesDataStore(name = "user")
    private val Context.settingsStore by preferencesDataStore(name = "settings")

    val userPreferencesRepository by lazy {
        UserPreferencesRepository(requireApplication.userStore)
    }

    val settingsPreferencesRepository by lazy {
        DataStorePreferencesRepository(requireApplication.settingsStore)
    }
}