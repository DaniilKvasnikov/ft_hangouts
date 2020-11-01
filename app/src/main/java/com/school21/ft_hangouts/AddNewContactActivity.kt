package com.school21.ft_hangouts

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_add_new_contact.*

class AddNewContactActivity : AppCompatActivity() {

    private lateinit var db: DataBaseHandler

    private val TAG = "PermissionDemo"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_new_contact)


        val context = this
        db = DataBaseHandler(context)

        var name = findViewById<EditText>(R.id.name)
        var surname = findViewById<EditText>(R.id.surname)
        var phone = findViewById<EditText>(R.id.phone)
        var organization = findViewById<EditText>(R.id.organization)
        var email = findViewById<EditText>(R.id.email)

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
