package com.noveogroup.modulotechinterview.data.preferences

import android.content.SharedPreferences

internal open class BasePreferences(
    protected val preferences: SharedPreferences
) {

    fun SharedPreferences.readBoolean(key: String, default: Boolean = false) =
        getBoolean(key, default)

    fun SharedPreferences.saveBoolean(key: String, value: Boolean) =
        save { it.putBoolean(key, value) }

    private fun SharedPreferences.save(action: (SharedPreferences.Editor) -> Unit) {
        edit().also {
            action.invoke(it)
            it.apply()
        }
    }
}
