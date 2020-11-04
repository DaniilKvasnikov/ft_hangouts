package com.school21.ft_hangouts

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_add_new_contact.*

class AddNewContactActivity : AppCompatActivity() {

    private lateinit var db: DataBaseHandler

    private val TAG = "PermissionDemo"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        theme.applyStyle(getSharedPreferences(ThemesInfo.themeKey,Context.MODE_PRIVATE).getInt(ThemesInfo.themeKey, ThemesInfo.defTheme), true)
        setContentView(R.layout.activity_add_new_contact)

        MainActivity.applicationCount++

        val context = this
        db = DataBaseHandler(context)
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

    fun addNewUser(view: View) {
        if (name.text.isEmpty()){
            Toast.makeText(this, getString(R.string.EnterName), Toast.LENGTH_SHORT).show()
            return
        }
        if (phone.text.isEmpty()){
            Toast.makeText(this, getString(R.string.EnterPhone), Toast.LENGTH_SHORT).show()
            return
        }
        val user = User()
        user.name = name.text.toString()
        user.surname = surname.text.toString()
        user.phone = phone.text.toString()
        user.organization = organization.text.toString()
        user.email = email.text.toString()
        Log.i(TAG, "add $user")
        db.insertData(user)
        finish()
    }
}
