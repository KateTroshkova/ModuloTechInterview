package com.noveogroup.modulotechinterview.data.preferences

import android.content.SharedPreferences

class DataPreferences(
    sharedPreferences: SharedPreferences
) : BasePreferences(sharedPreferences) {

    var hasLocalData: Boolean
        get() = preferences.readBoolean(KEY_DATA_SAVED, false)
        set(value) {
            preferences.saveBoolean(KEY_DATA_SAVED, value)
        }

    companion object {
        private const val KEY_DATA_SAVED = "data_saved"
    }
}