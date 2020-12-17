package com.hovi.hoco.utils

import android.content.Context

/**
 * Created by VietTC on 09/10/2020.
 */
class SharePreferenceUtils {
    companion object {
        val SHARE_PREFERENCE_NAME = "HOCO_SHARE_PREFERENCE"

        fun setString(context: Context, key: String, value: String) {
            val sharedPref = context.getSharedPreferences(SHARE_PREFERENCE_NAME, Context.MODE_PRIVATE) ?: return
            with (sharedPref.edit()) {
                putString(key, value)
                apply()
            }
        }

        fun getString(context: Context, key: String): String? {
            return getString(context, key, "")
        }

        fun getString(context: Context, key: String, default: String): String? {
            val sharedPref = context.getSharedPreferences(SHARE_PREFERENCE_NAME, Context.MODE_PRIVATE) ?: return default
            return sharedPref.getString(key, default)
        }

        fun setBoolean(context: Context, key: String, value: Boolean) {
            val sharedPref = context.getSharedPreferences(SHARE_PREFERENCE_NAME, Context.MODE_PRIVATE) ?: return
            with (sharedPref.edit()) {
                putBoolean(key, value)
                apply()
            }
        }

        fun getBoolean(context: Context, key: String): Boolean {
            val sharedPref = context.getSharedPreferences(SHARE_PREFERENCE_NAME, Context.MODE_PRIVATE) ?: return false
            return sharedPref.getBoolean(key, false)
        }

        fun getBoolean(context: Context, key: String, default: Boolean): Boolean {
            val sharedPref = context.getSharedPreferences(SHARE_PREFERENCE_NAME, Context.MODE_PRIVATE) ?: return default
            return sharedPref.getBoolean(key, default)
        }

        fun setInt(context: Context, key: String, value: Int) {
            val sharedPref = context.getSharedPreferences(SHARE_PREFERENCE_NAME, Context.MODE_PRIVATE) ?: return
            with (sharedPref.edit()) {
                putInt(key, value)
                apply()
            }
        }

        fun getInt(context: Context, key: String): Int {
            val sharedPref = context.getSharedPreferences(SHARE_PREFERENCE_NAME, Context.MODE_PRIVATE) ?: return 0
            return sharedPref.getInt(key, 0)
        }

        fun getInt(context: Context, key: String, default: Int): Int {
            val sharedPref = context.getSharedPreferences(SHARE_PREFERENCE_NAME, Context.MODE_PRIVATE) ?: return default
            return sharedPref.getInt(key, default)
        }
    }
}