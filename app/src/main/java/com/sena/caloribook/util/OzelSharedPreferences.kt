package com.sena.caloribook.util

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit

class OzelSharedPreferences {

    companion object {

        private val TIME = "time"
        private var sharedPreferences: SharedPreferences? = null

        @Volatile
        private var instance: OzelSharedPreferences? = null

        private val lock = Any()
        operator fun invoke(context: Context): OzelSharedPreferences =
            instance ?: synchronized(lock) {
                instance ?: ozelSharedPrefOlustur(context = context).also {
                    instance = it
                }
            }

        private fun ozelSharedPrefOlustur(context: Context): OzelSharedPreferences {
            sharedPreferences =
                androidx.preference.PreferenceManager.getDefaultSharedPreferences(context)
            return OzelSharedPreferences()
        }
    }

    fun zamaniKaydet(time: Long) {
        sharedPreferences?.edit(commit = true) {
            putLong(TIME, time)
        }
    }

    fun getTime() =
        sharedPreferences?.getLong(TIME, 0)

}