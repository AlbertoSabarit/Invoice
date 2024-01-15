package com.murray.invoice.ui.preferences

import android.os.Bundle
import android.text.InputType
import androidx.preference.EditTextPreference
import androidx.preference.PreferenceFragmentCompat
import com.murray.invoice.Locator
import com.murray.invoice.R

class AccountFragment : PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.account_preferences, rootKey)
        initPreferenceEmail()
        initPreferencePassword()
    }

    private fun initPreferenceEmail() {
        val email =
            preferenceManager.findPreference<EditTextPreference>(getString(R.string.key_email))
        //Vamos a inicializar el texto de la preferencia
        email?.setOnBindEditTextListener {
            it.setText(Locator.userPreferencesRepository.getEmail())
            it.isEnabled = false
        }
    }

    private fun initPreferencePassword() {
        val password =
            preferenceManager.findPreference<EditTextPreference>(getString(R.string.key_password))
        password?.setOnBindEditTextListener {
            it.setText(Locator.userPreferencesRepository.getPassword())
            it.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
            it.selectAll()
        }
        password?.setOnPreferenceChangeListener { _, newPassword ->
            Locator.userPreferencesRepository.savePassword(newPassword as String)
            true
        }
    }


}