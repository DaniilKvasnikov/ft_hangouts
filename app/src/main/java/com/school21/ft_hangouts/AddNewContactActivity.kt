package com.school21.ft_hangouts

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_add_new_contact.*

class AddNewContactActivity : MainActivity() {

    private lateinit var db: DataBaseHandler

    private val TAG = "PermissionDemo"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        theme.applyStyle(getSharedPreferences(ThemesInfo.themeKey,Context.MODE_PRIVATE).getInt(ThemesInfo.themeKey, ThemesInfo.defTheme), true)
        setContentView(R.layout.activity_add_new_contact)


        val context = this
        db = DataBaseHandler(context)

        back.setOnClickListener {
            backToMain()
        }

        createContact.setOnClickListener {
            addNewUser()
            backToMain()
        }
    }

    private fun addNewUser() {
        val user = User()
        user.name = name.text.toString()
        user.surname = surname.text.toString()
        user.phone = phone.text.toString()
        user.organization = organization.text.toString()
        user.email = email.text.toString()
        Log.i(TAG, "add $user")
        db.insertData(user)
    }

    private fun backToMain() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
}
