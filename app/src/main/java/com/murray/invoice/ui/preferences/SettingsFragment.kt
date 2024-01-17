package com.murray.invoice.ui.preferences

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import androidx.navigation.fragment.findNavController
import androidx.preference.ListPreference
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

        val accountPreference =
            preferenceManager.findPreference<Preference>(getString(R.string.key_account))
        accountPreference?.setOnPreferenceClickListener {
            findNavController().navigate(R.id.action_settingsFragment_to_accountFragment)
            true
        }
        val preferences = activity?.getSharedPreferences("settings", Context.MODE_PRIVATE)

        val orderValue = preferences!!.getString("tasks", "0")

        val listPreference = findPreference<ListPreference>("tasks")
        listPreference?.summary = listPreference?.entries?.get(orderValue!!.toInt())
        listPreference?.value = orderValue
        listPreference?.setOnPreferenceChangeListener { preference, newValue ->
            if (preference is ListPreference)
                preferences.edit()?.putString("tasks", newValue.toString())?.apply()
            listPreference.summary = listPreference.entries?.get(newValue!!.toString().toInt())
            true
        }


        val orderValueinvoice = preferences!!.getString("invoices", "0")

        val listPreferenceinvoice = findPreference<ListPreference>("invoices")
        listPreferenceinvoice?.summary = listPreference?.entries?.get(orderValueinvoice!!.toInt())
        listPreferenceinvoice?.value = orderValueinvoice
        listPreferenceinvoice?.setOnPreferenceChangeListener { preference, newValue ->
            if (preference is ListPreference)
                preferences.edit()?.putString("invoices", newValue.toString())?.apply()
            listPreferenceinvoice.summary = listPreferenceinvoice.entries?.get(newValue!!.toString().toInt())
            true
        }


        val modoValue = preferences!!.getString("modo", "0")

        val listPreferencemodo = findPreference<ListPreference>("modo")
        listPreferencemodo?.summary = listPreferencemodo?.entries?.get(modoValue!!.toInt())
        listPreferencemodo?.value = modoValue
        listPreferencemodo?.setOnPreferenceChangeListener { preference, newValue ->
            if (preference is ListPreference) {
                val newMode = newValue.toString().toInt()
                preferences.edit()?.putString("modo", newMode.toString())?.apply()

                when (newMode) {
                    0 -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                    1 -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                }

                listPreferencemodo.summary = listPreferencemodo.entries?.get(newMode)
            }
            true
        }
    }

}