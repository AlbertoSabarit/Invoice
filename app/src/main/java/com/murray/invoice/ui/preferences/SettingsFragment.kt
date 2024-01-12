package com.murray.invoice.ui.preferences

import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import com.murray.invoice.Locator
import com.murray.invoice.R

class SettingsFragment : PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.settings_preferences, rootKey)
        //Obtener el DataStore que se quiere que utilicen los componentes visuales de las Preferencias
        //Cuando se modifica el gestor de las preferencias se utiliza en todos los PreferenceFragment de la jerarquia de vistas
        //Ya no se utiliza el fichero shared_preferences
        val store = Locator.settingsPreferencesRepository
        preferenceManager.preferenceDataStore = store

        val accountPreference = preferenceManager.findPreference<Preference>(getString(R.string.key_account))
        accountPreference?.setOnPreferenceClickListener {
            findNavController().navigate(R.id.action_settingsFragment_to_accountFragment)
            true
        }
    }

}