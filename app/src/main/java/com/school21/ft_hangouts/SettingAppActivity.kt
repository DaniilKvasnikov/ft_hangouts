package com.school21.ft_hangouts

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.SeekBar
import android.widget.SeekBar.OnSeekBarChangeListener
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat

class SettingAppActivity : AppCompatActivity() {

    lateinit var sharedPreferences: SharedPreferences
    val themeKey = "currentTheme"
    val defTheme = R.style.AppTheme

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        sharedPreferences = getSharedPreferences(
            "ThemePref",
            Context.MODE_PRIVATE
        )

        val style = sharedPreferences.getInt(themeKey, defTheme)
        theme.applyStyle(style, true)

        setContentView(R.layout.activity_setting_app)

        val buttonBack = findViewById<Button>(R.id.Back)
        buttonBack.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }

    fun ChangeTheme(view: View){
        var theme : Int = defTheme
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
        sharedPreferences.edit().putInt(themeKey, theme).apply()

        val intent = intent // from getIntent()
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
        finish()
        startActivity(intent)
    }

    fun ChangeLanguage(view: View){

    }
}
