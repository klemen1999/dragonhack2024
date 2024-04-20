package com.kraskibrancini.sweatify.core

import android.content.Context

class PreferencesManager(context : Context) {
    companion object {
        const val INSTALL_ID = "install_id"
        const val USERNAME = "username"
    }

    private val sharedPreferences =
        context.getSharedPreferences("Sweatify", Context.MODE_PRIVATE)

    fun saveString(key: String, value: String) {
        sharedPreferences.edit().putString(key, value).apply()
    }

    fun getString(key: String): String? {
        return sharedPreferences.getString(key, null)
    }
}