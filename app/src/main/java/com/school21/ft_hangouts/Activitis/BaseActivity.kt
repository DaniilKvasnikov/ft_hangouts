package com.school21.ft_hangouts.Activitis

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.school21.ft_hangouts.LocateSetter
import com.school21.ft_hangouts.R
import com.school21.ft_hangouts.ThemesInfo
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.util.*
import java.util.concurrent.TimeUnit

open class BaseActivity : AppCompatActivity() {

    companion object {
        var activitis: Long = 0

        @SuppressLint("SimpleDateFormat")
        fun convertSecondsToHMmSs(millis: Long): String? {
            val formatter = SimpleDateFormat("HH:mm:ss");
            val dateString = formatter.format(Date(millis))
            return dateString
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        themeUpdate()
    }

    override fun onResume() {
        super.onResume()
        themeUpdate()
        LocateSetter().onCreate(this)
        activitis--
        if (activitis < 0) openTimeDialog()
        activitis=0
    }

    private fun openTimeDialog() {
        Log.v("OpenLVL", "index = $activitis")
        var show: Boolean = (MainActivity.time != 0L)
        if (!show) return
        var timeStr = BaseActivity.convertSecondsToHMmSs(MainActivity.time)
        Toast.makeText(this, "$timeStr last run", Toast.LENGTH_LONG).show()
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

        Finish()
        val intent = intent
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
        OpenActivity(intent)
    }

    private fun themeUpdate() {
        val newTheme = getSharedPreferences(ThemesInfo.themeKey, Context.MODE_PRIVATE)
            .getInt(
                ThemesInfo.themeKey,
                ThemesInfo.defTheme
            )
        theme.applyStyle(newTheme, true)
    }

    fun OpenActivity(intent: Intent){
        activitis++
        startActivity(intent)
    }

    fun Finish(){
        activitis++
        finish()
    }
}