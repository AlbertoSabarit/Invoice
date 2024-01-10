package com.murray.invoice.ui.preferences

import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat
import com.murray.invoice.R

class AccountFragment : PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.account_preferences, rootKey)
    }
}