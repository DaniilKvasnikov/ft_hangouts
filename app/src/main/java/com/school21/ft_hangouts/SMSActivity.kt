package com.school21.ft_hangouts

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class SMSActivity : AppCompatActivity() {
    private var message: EditText? = null
    private lateinit var phoneView: TextView


    private var organization: String? = null
    private var email: String? = null
    private var phone: String? = null
    private var surname: String? = null
    private var name: String? = null
    private var id: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_s_m_s)

        id = intent.getIntExtra("id", 0).toLong()
        name = intent.getStringExtra("name")
        surname = intent.getStringExtra("surname")
        phone = intent.getStringExtra("phone")
        email = intent.getStringExtra("email")
        organization = intent.getStringExtra("organization")

        message = findViewById(R.id.message);
        phoneView = findViewById<TextView>(R.id.phoneNumber)
        phoneView.text = phone
    }

    fun sendMessage(view: View) {
        PermissionsManager().setupPermissionsSms(this, phone.toString(), message?.text.toString());
    }

    fun backToMain(view: View) {
        finish()
    }
}