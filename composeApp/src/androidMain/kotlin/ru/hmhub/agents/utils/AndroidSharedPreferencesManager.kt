package ru.hmhub.agents.utils

import android.content.Context

class AndroidSharedPreferencesManager(private val context: Context) : SharedPreferencesManager {
    private val sharedPreferences = context.getSharedPreferences("my_preferences", Context.MODE_PRIVATE)
    private val editor = sharedPreferences.edit()

    override fun getInt(key: String, default: Int): Int {
        return sharedPreferences.getInt(key, default)
    }

    override fun setInt(key: String, value: Int) {
        editor.putInt(key, value).apply()
    }

    override fun getString(key: String, default: String): String? {
        return sharedPreferences.getString(key, default)
    }

    override fun setString(key: String, value: String) {
         editor.putString(key, value).apply()
    }

    override fun getBool(key: String, default: Boolean): Boolean {
        return sharedPreferences.getBoolean(key, default)
    }

    override fun setBool(key: String, value: Boolean) {
         editor.putBoolean(key, value).apply()
    }

    override fun deleteInt(key: String){
        editor.remove(key).apply()
    }
}