package com.murray.invoice.ui.preferences

import android.content.Context
import android.os.Bundle
import androidx.preference.ListPreference
import androidx.preference.PreferenceFragmentCompat
import com.google.android.material.snackbar.Snackbar
import com.murray.invoice.Locator
import com.murray.invoice.R

class SortFragment: PreferenceFragmentCompat() {
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.sort_preferences, rootKey)
        val store = Locator.settingsPreferencesRepository
        preferenceManager.preferenceDataStore = store

        val preferences = activity?.getSharedPreferences("settings", Context.MODE_PRIVATE)

        /* SORT CUSTOMER */
        val orderValueCustomer = preferences!!.getString(
            "customers",
            "0")
        val listPreferenceCustomer = findPreference<ListPreference>("customers")
        listPreferenceCustomer?.summary = listPreferenceCustomer?.entries?.get(orderValueCustomer!!.toInt())
        listPreferenceCustomer?.value = orderValueCustomer
        listPreferenceCustomer?.setOnPreferenceChangeListener { preference, newValue ->
            if (preference is ListPreference)
                preferences.edit()?.putString("customers", newValue.toString())?.apply()
            listPreferenceCustomer.summary = listPreferenceCustomer.entries?.get(newValue!!.toString().toInt())
            true
        }

        /* SORT ITEM */
        val orderValueItem = preferences!!.getString(
            getString(R.string.key_item),
            getString(R.string.def_value_item))

        val listPreferenceItem = findPreference<ListPreference>(getString(R.string.key_item))
        listPreferenceItem?.summary = listPreferenceItem?.entries?.get(orderValueItem!!.toInt())
        listPreferenceItem?.value = orderValueItem
        listPreferenceItem?.setOnPreferenceChangeListener { preference, newValue ->
            if (preference is ListPreference)
                preferences.edit()?.putString(getString(R.string.key_item), newValue.toString())?.apply()
            listPreferenceItem.summary = listPreferenceItem.entries?.get(newValue!!.toString().toInt())
            true
        }

        /* SORT INVOICE */
        val orderValueInvoice = preferences!!.getString("invoices", "0")
        val listPreferenceInvoice = findPreference<ListPreference>("invoices")
        listPreferenceInvoice?.summary = listPreferenceInvoice?.entries?.get(orderValueInvoice!!.toInt())
        listPreferenceInvoice?.value = orderValueInvoice
        listPreferenceInvoice?.setOnPreferenceChangeListener { preference, newValue ->
            if (preference is ListPreference)
                preferences.edit()?.putString("invoices", newValue.toString())?.apply()
            listPreferenceInvoice.summary = listPreferenceInvoice.entries?.get(newValue!!.toString().toInt())
            true
        }

        /* SORT TASK */
        val orderValueTask = preferences!!.getString("tasks", "0")
        val listPreferenceTask = findPreference<ListPreference>("tasks")
        listPreferenceTask?.summary = listPreferenceTask?.entries?.get(orderValueTask!!.toInt())
        listPreferenceTask?.value = orderValueTask
        listPreferenceTask?.setOnPreferenceChangeListener { preference, newValue ->
            if (preference is ListPreference)
                preferences.edit()?.putString("tasks", newValue.toString())?.apply()
            listPreferenceTask.summary = listPreferenceTask.entries?.get(newValue!!.toString().toInt())
            true
        }
    }
}