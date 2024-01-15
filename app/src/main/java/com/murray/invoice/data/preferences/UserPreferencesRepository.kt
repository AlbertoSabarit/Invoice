package com.murray.invoice.data.preferences

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

/**
 * Esta clase contiene todos los metodos necesarios para leer y guardar datos del usuario,
 * preferencias en el almacen de datos "user_preferences"
 */
class UserPreferencesRepository(private val dataStore: DataStore<Preferences>) {
    /**
     * Método que guarda la inforamcion del usuario de Firebase en user_preference
     */
    fun saveUser(email: String, password: String, id: Int) {
        GlobalScope.launch(Dispatchers.IO) {
            dataStore.edit { preferences ->
                preferences[EMAIL] = email
                preferences[PASSWORD] = password
                preferences[ID] = id
            }
        }
    }

    fun getEmail(): String {
        return runBlocking {
            dataStore.data.map { preferences ->
                preferences[EMAIL] ?: "none"
            }.first()
        }
    }

    fun getPassword(): String {
        return runBlocking {
            dataStore.data.map { preferences ->
                preferences[PASSWORD] ?: "none"
            }.first()
        }
    }

    fun savePassword(newPassword: String) {
        runBlocking {
            dataStore.edit { preferences ->
                preferences[PASSWORD] = newPassword
            }
        }
    }
    companion object {
        private val EMAIL = stringPreferencesKey("email")
        private val PASSWORD = stringPreferencesKey("password")
        private val ID = intPreferencesKey("id")
    }
}

