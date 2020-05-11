package com.example.ftmsnew.utils;

import android.content.Context
import android.preference.PreferenceManager

object PrefUtil {

    @JvmOverloads
    fun putString(context: Context?, key: String?, value: String, pref: String? = null) {
        if (context == null || key == null) {
            return
        }
        if (pref == null || pref.isEmpty()) {
            PreferenceManager.getDefaultSharedPreferences(context).edit().putString(key, value).apply()
        } else {
            context.getSharedPreferences(pref, Context.MODE_PRIVATE).edit().putString(key, value).apply()
        }
    }

    @JvmOverloads
    fun getString(context: Context?, key: String?, pref: String? = null): String? {
        if (context == null || key == null) {
            return null
        }
        return if (pref == null || pref.isEmpty()) {
            PreferenceManager.getDefaultSharedPreferences(context).getString(key, null)
        } else {
            context.getSharedPreferences(pref, Context.MODE_PRIVATE).getString(key, null)
        }
    }

    @JvmOverloads
    fun putInt(context: Context?, key: String?, value: Int, pref: String? = null) {
        if (context == null || key == null) {
            return
        }
        if (pref == null || pref.isEmpty()) {
            PreferenceManager.getDefaultSharedPreferences(context).edit().putInt(key, value).apply()
        } else {
            context.getSharedPreferences(pref, Context.MODE_PRIVATE).edit().putInt(key, value).apply()
        }
    }

    @JvmOverloads
    fun getInt(context: Context?, key: String?, pref: String? = null): Int {
        if (context == null || key == null) {
            return 0
        }
        return if (pref == null || pref.isEmpty()) {
            PreferenceManager.getDefaultSharedPreferences(context).getInt(key, 0)
        } else {
            context.getSharedPreferences(pref, Context.MODE_PRIVATE).getInt(key, 0)
        }
    }

    @JvmOverloads
    fun putLong(context: Context?, key: String?, value: Long, pref: String? = null) {
        if (context == null || key == null) {
            return
        }
        if (pref == null || pref.isEmpty()) {
            PreferenceManager.getDefaultSharedPreferences(context).edit().putLong(key, value).apply()
        } else {
            context.getSharedPreferences(pref, Context.MODE_PRIVATE).edit().putLong(key, value).apply()
        }
    }

    @JvmOverloads
    fun getLong(context: Context?, key: String?, pref: String? = null): Long {
        if (context == null || key == null) {
            return 0
        }
        return if (pref == null || pref.isEmpty()) {
            PreferenceManager.getDefaultSharedPreferences(context).getLong(key, 0)
        } else {
            context.getSharedPreferences(pref, Context.MODE_PRIVATE).getLong(key, 0)
        }
    }

    @JvmOverloads
    fun putBool(context: Context?, key: String?, value: Boolean, pref: String? = null) {
        if (context == null || key == null) {
            return
        }
        if (pref == null || pref.isEmpty()) {
            PreferenceManager.getDefaultSharedPreferences(context).edit().putBoolean(key, value).apply()
        } else {
            context.getSharedPreferences(pref, Context.MODE_PRIVATE).edit().putBoolean(key, value).apply()
        }
    }

    @JvmOverloads
    fun getBool(context: Context?, key: String?, pref: String? = null): Boolean {
        if (context == null || key == null) {
            return false
        }
        return if (pref == null || pref.isEmpty()) {
            PreferenceManager.getDefaultSharedPreferences(context).getBoolean(key, false)
        } else {
            context.getSharedPreferences(pref, Context.MODE_PRIVATE).getBoolean(key, false)
        }
    }

    fun getIntvalue(context: Context, key: String): Int {
        return getInt(context, key, null)
    }

    fun removeKey(context: Context?, key: String?, pref: String?) {
        if (context == null || key == null) {
            return
        }
        if (pref == null || pref.isEmpty()) {
            PreferenceManager.getDefaultSharedPreferences(context).edit().remove(key).commit()
        } else {
        }
    }
}
