package com.murray.invoice.ui.preferences

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.DisplayMetrics
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.navigation.fragment.findNavController
import androidx.preference.ListPreference
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import com.murray.invoice.Locator
import com.murray.invoice.R
import java.util.Locale


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

        val sortPreference =
            preferenceManager.findPreference<Preference>(getString(R.string.key_sort))
        sortPreference?.setOnPreferenceClickListener {
            findNavController().navigate(R.id.action_settingsFragment_to_sortFragment)
            true
        }

        val preferences = activity?.getSharedPreferences("settings", Context.MODE_PRIVATE)

        /* MODO */
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

        /* IDIOMAS */
        val langValue = preferences!!.getString(
            getString(R.string.key_lang),
            getString(R.string.def_value_lang))

        val listPreferenceLang = findPreference<ListPreference>(getString(R.string.key_lang))
        listPreferenceLang?.summary = listPreferenceLang?.entries?.get(langValue!!.toInt())
        listPreferenceLang?.value = langValue
        listPreferenceLang?.setOnPreferenceChangeListener { preference, newValue ->
            if (preference is ListPreference) {
                val newLang = newValue.toString()
                preferences.edit()?.putString(getString(R.string.key_lang), newLang)?.apply()

                when(newLang.toInt()){
                    0 -> setLocale(Locale.getDefault().language)
                    1 -> setLocale("es")
                    2 -> setLocale("en")
                    3 -> setLocale("uk")
                }
                requireActivity().recreate()

                listPreferenceLang.summary = listPreferenceLang.entries?.get(newLang.toInt())
            }
            true
        }
    }

    private fun setLocale(languageCode: String) {
        val locale = Locale(languageCode)
        //Locale.setDefault(locale)
        val config = resources.configuration
        config.setLocale(locale)
        resources.updateConfiguration(config, resources.displayMetrics)
    }

}