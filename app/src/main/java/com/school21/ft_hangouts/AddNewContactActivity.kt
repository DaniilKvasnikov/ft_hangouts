package com.school21.ft_hangouts

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
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


        val context = this
        db = DataBaseHandler(context)
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
        backToMain(view)
    }

    fun backToMain(view: View) {
        finish()
    }
}
