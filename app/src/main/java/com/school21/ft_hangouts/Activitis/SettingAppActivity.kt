package com.school21.ft_hangouts.Activitis

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.school21.ft_hangouts.LocateSetter
import com.school21.ft_hangouts.R
import com.school21.ft_hangouts.ThemesInfo

class SettingAppActivity : BaseActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting_app)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.settings_menu, menu)

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            R.id.back ->{
                Finish()
                val intent = Intent(this, MainActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
                OpenActivity(intent)
                true
            }
            else->{
                super.onOptionsItemSelected(item)
            }
        }
    }

    fun ChangeLanguage(view: View){
        var language = ""
        when(view.id){
            R.id.Russian ->{
                language = "ru"
            }
            R.id.English ->{
                language = "en"
            }
        }
        LocateSetter().changeLocate(this, language)
        recreate()
    }
}
