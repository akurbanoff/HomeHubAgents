package ru.hmhub.agents.utils

interface SharedPreferencesManager {
    fun getInt(key: String, default: Int): Int
    fun setInt(key: String, value: Int)
    fun deleteInt(key: String)
    fun getString(key: String, default: String) : String?
    fun setString(key: String, value: String)
    fun getBool(key: String, default: Boolean) : Boolean
    fun setBool(key: String, value: Boolean)
}