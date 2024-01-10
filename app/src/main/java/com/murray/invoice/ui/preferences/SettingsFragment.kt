package com.murray.invoice.ui.preferences

import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import com.murray.invoice.R

class SettingsFragment : PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey)

        val navController: NavController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_content_main)

        val accountPreference: Preference? = findPreference(getString(R.string.key_account))

        accountPreference?.onPreferenceClickListener = Preference.OnPreferenceClickListener {

            if (it.key == getString(R.string.key_account)) {
                navController.navigate(R.id.action_settingsFragment_to_accountFragment)
            }
            true
        }
    }

}