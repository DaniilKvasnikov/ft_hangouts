package com.school21.ft_hangouts.Activitis

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.school21.ft_hangouts.LocateSetter
import com.school21.ft_hangouts.R
import com.school21.ft_hangouts.ThemesInfo

open class BaseActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        themeUpdate()
    }

    override fun onResume() {
        super.onResume()
        themeUpdate()
        LocateSetter().onCreate(this)
    }

    fun changeTheme(view: View){
        var theme : Int = ThemesInfo.defTheme
        when(view.id) {
            R.id.AppTheme ->{
                theme = R.style.AppTheme
            }
            R.id.OverlayThemeLime ->{
                theme = R.style.OverlayThemeLime
            }
            R.id.OverlayThemeRed ->{
                theme = R.style.OverlayThemeRed
            }
            R.id.OverlayThemeGreen ->{
                theme = R.style.OverlayThemeGreen
            }
            R.id.OverlayThemeBlue ->{
                theme = R.style.OverlayThemeBlue
            }
        }
        val sharedPreferences = getSharedPreferences(ThemesInfo.themeKey, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putInt(ThemesInfo.themeKey, theme)
        editor.apply()

        val intent = intent
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
        startActivity(intent)
        finish()
    }

    private fun themeUpdate() {
        val newTheme = getSharedPreferences(ThemesInfo.themeKey, Context.MODE_PRIVATE)
            .getInt(
                ThemesInfo.themeKey,
                ThemesInfo.defTheme
            )
        theme.applyStyle(newTheme, true)
    }
}