package com.school21.ft_hangouts

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.res.Configuration
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.SeekBar
import android.widget.SeekBar.OnSeekBarChangeListener
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import java.util.*

class SettingAppActivity : MainActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        theme.applyStyle(getSharedPreferences(ThemesInfo.themeKey,Context.MODE_PRIVATE).getInt(ThemesInfo.themeKey, ThemesInfo.defTheme), true)
        loadLocate()
        setContentView(R.layout.activity_setting_app)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.settings_menu, menu)

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            R.id.back->{
                finish()
                true
            }
            else->{
                super.onOptionsItemSelected(item)
            }
        }
    }

    fun ChangeTheme(view: View){
        var theme : Int = ThemesInfo.defTheme
        when(view.id) {
            R.id.AppTheme->{
                theme = R.style.AppTheme
            }
            R.id.OverlayThemeLime->{
                theme = R.style.OverlayThemeLime
            }
            R.id.OverlayThemeRed->{
                theme = R.style.OverlayThemeRed
            }
            R.id.OverlayThemeGreen->{
                theme = R.style.OverlayThemeGreen
            }
            R.id.OverlayThemeBlue->{
                theme = R.style.OverlayThemeBlue
            }
        }
        getSharedPreferences(ThemesInfo.themeKey,Context.MODE_PRIVATE).edit().putInt(ThemesInfo.themeKey, theme).apply()

        val intent = intent
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
        finish()
        startActivity(intent)
    }

    fun ChangeLanguage(view: View){
        when(view.id){
            R.id.Russian->{
                setLocate("ru")
            }
            R.id.English->{
                setLocate("en")
            }
        }
        recreate()
    }

    private fun setLocate(language: String) {
        val locale = Locale(language)
        Locale.setDefault(locale)
        val config = Configuration()
        config.setLocale(locale)
        baseContext.resources.updateConfiguration(config, baseContext.resources.displayMetrics)

        val editor = getSharedPreferences("Settings", Context.MODE_PRIVATE).edit()
        editor.putString("My_Lang", language)
        editor.apply()
    }

    private fun loadLocate(){
        val sharedPreference = getSharedPreferences("Settings", Context.MODE_PRIVATE)
        val language = sharedPreference.getString("My_Lang", "")
        setLocate(language ?: "")
    }
}
