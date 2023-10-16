package com.rsnimchenko.simple_arkanoid.util

import android.content.Context
import android.content.SharedPreferences

object SharedPrefUtil {
    private const val key = "history"
    private lateinit var prefs: SharedPreferences

    fun init(context: Context) {
        prefs = context.getSharedPreferences("history_store", Context.MODE_PRIVATE)
    }

    fun writeHistory(score: String) {
        val editor = prefs.edit()
        editor.putString(key, score)
        editor.apply()
    }

    fun readHistory(): String {
        return prefs.getString(key, "").orEmpty()
    }
}