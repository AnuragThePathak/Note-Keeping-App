package com.anurag.notekeepingapp.ui

import android.os.Bundle
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import com.anurag.notekeepingapp.BuildConfig
import com.anurag.notekeepingapp.R

class SettingsFragment : PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey)

        val version = findPreference<Preference>("version")
        version?.summary = BuildConfig.VERSION_NAME
    }
}