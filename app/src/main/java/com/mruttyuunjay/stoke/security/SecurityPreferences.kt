package com.mruttyuunjay.stoke.security

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

object Prefs {

    private const val APP_PREFERENCES = "app_preferences"
    private const val NEED_COMPLETE = "need_complete"
    lateinit var preferences: SharedPreferences

    fun init(context: Context) {
        preferences = context.getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE)
    }

//    fun getLastAppClosed() = preferences.getLong("last_app_closed", 0)
//    fun setLastAppClosed() = preferences.edit { it.putLong("last_app_closed",0) }
//    fun deleteLastAppClosed() = preferences.edit { it.remove("last_app_closed").commit() }

    private inline fun SharedPreferences.edit(operation: (SharedPreferences.Editor) -> Unit) {
        val editor = this.edit()
        operation(editor)
        editor.apply()
    }

    fun getBootPin() = preferences.getInt("BOOT_PIN",0)
    fun setBootPin(query: Int) = preferences.edit { it.putInt("BOOT_PIN",query)}

    fun getUseAuthenticator() = preferences.getBoolean("use_biometric_lock",false)
    fun setUseAuthenticator(query: Boolean) = preferences.edit { it.putBoolean("use_biometric_lock",query)}

    fun getLockAppAfter() = preferences.getInt("lock_app_after",0)
    fun setLockAppAfter(query: Int) = preferences.edit { it.putInt("lock_app_after",query)}

    fun getSecureScreen() = preferences.getString("secure_screen_v2", "Never")
    fun setSecureScreen(query: String) = preferences.edit { it.putString("secure_screen_v2",query)}
    private fun rmSecureScreen() = preferences.edit().remove("secure_screen_v2")

    fun getLastAppClosed() = preferences.getLong("last_app_closed", 0)
    fun setLastAppClosed(query: Long) = preferences.edit { it.putLong("last_app_closed",query)}
    fun deleteLastAppClosed() = preferences.edit { it.remove("last_app_closed").commit() }

    fun getIncognitoMode() = preferences.getBoolean("incognito_mode", false)
    fun setIncognitoMode(query: Boolean) = preferences.edit { it.putBoolean("incognito_mode", query) }

    private fun removePref(key:String){
        preferences.edit().remove(key).apply()
    }

}