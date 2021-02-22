package com.school21.ft_hangouts

import android.content.Context
import android.content.ContextWrapper
import android.content.res.Configuration
import java.util.*

class LocateSetter {
    private val nameSettings: String = "Settings"
    private val keyLanguage: String = "My_Lang"

    fun changeLocate(context: ContextWrapper, language: String){
        setLocate(context, language)
        saveLocate(context, language)
    }

    fun saveLocate(context: ContextWrapper, language: String){
        val editor = context.getSharedPreferences(nameSettings, Context.MODE_PRIVATE).edit()
        editor.putString(keyLanguage, language)
        editor.apply()
    }

    private fun setLocate(context: ContextWrapper, language: String) {
        val locale = Locale(language)
        val config = Configuration()
        config.setLocale(locale)
        context.baseContext.resources.updateConfiguration(config, context.baseContext.resources.displayMetrics)
    }

    fun loadLocate(context: ContextWrapper){
        val sharedPreference = context.getSharedPreferences(nameSettings, Context.MODE_PRIVATE)
        val default = Locale.getDefault()
        val language = sharedPreference.getString(keyLanguage, default.language)
        setLocate(context, language ?: "")
    }

    fun onCreate(context: ContextWrapper) {
        val default = Locale.getDefault()
        val sharedPreference = context.getSharedPreferences(nameSettings, Context.MODE_PRIVATE)
        val language = sharedPreference.getString(keyLanguage, default.language)
        LocateSetter().changeLocate(context, language!!)
    }
}