package com.tree.chope.backend

import android.content.SharedPreferences

class PreferenceHelper(private val prefs: SharedPreferences) {

    fun read(key: String, value: String): String? {
        return prefs.getString(key, value)
    }
    fun read(key: String, value: Long): Long? {
        return prefs.getLong(key, value)
    }
    fun read(key: String, value: Boolean): Boolean? {
        return prefs.getBoolean(key, value)
    }
    fun write(key: String, value: String?) {
        val prefsEditor: SharedPreferences.Editor = prefs.edit()
        with(prefsEditor) {
            putString(key, value)
            commit()
        }
    }
    fun write(key: String, value: Boolean) {
        val prefsEditor: SharedPreferences.Editor = prefs.edit()
        with(prefsEditor) {
            putBoolean(key, value)
            commit()
        }
    }
    fun write(key: String, value: Long) {
        val prefsEditor: SharedPreferences.Editor = prefs.edit()
        with(prefsEditor) {
            putLong(key, value)
            commit()
        }
    }

    fun clear(key: String) {
        val prefsEditor: SharedPreferences.Editor = prefs.edit()
        prefsEditor.remove(key).apply()
    }

    fun clear() {
        val prefsEditor: SharedPreferences.Editor = prefs.edit()
        prefsEditor.clear().apply()
    }

}
