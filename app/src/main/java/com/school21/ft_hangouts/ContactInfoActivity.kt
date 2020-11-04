package com.school21.ft_hangouts

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_add_new_contact.*
import kotlinx.android.synthetic.main.activity_add_new_contact.back
import kotlinx.android.synthetic.main.activity_contact_info.*
import java.io.Serializable

class ContactInfoActivity : AppCompatActivity() {

    private var emailText: EditText? = null
    private var organizationText: EditText? = null
    private var phoneText: EditText? = null
    private var surnameText: EditText? = null
    private var nameText: EditText? = null
    private lateinit var db: DataBaseHandler
    private var id: Long = 0
    private var message: EditText? = null
    private var organization: String? = null
    private var email: String? = null
    private var phone: String? = null
    private var surname: String? = null
    private var name: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        theme.applyStyle(getSharedPreferences(ThemesInfo.themeKey, Context.MODE_PRIVATE).getInt(ThemesInfo.themeKey, ThemesInfo.defTheme), true)
        setContentView(R.layout.activity_contact_info)

        val context = this
        db = DataBaseHandler(context)

        id = intent.getIntExtra("id", 0).toLong()
        name = intent.getStringExtra("name")
        surname = intent.getStringExtra("surname")
        phone = intent.getStringExtra("phone")
        email = intent.getStringExtra("email")
        organization = intent.getStringExtra("organization")

        nameText = findViewById(R.id.name)
        surnameText = findViewById(R.id.surname)
        phoneText = findViewById(R.id.phone)
        organizationText = findViewById(R.id.organization)
        emailText = findViewById(R.id.email)

        nameText?.setText(name)
        surnameText?.setText(surname)
        phoneText?.setText(phone)
        organizationText?.setText(organization)
        emailText?.setText(email)
    }

    fun sendMessage(view: View) {
        val intent = Intent(this, SMSActivity::class.java).apply {
            putExtra("id", id)
            putExtra("name", name)
            putExtra("surname", surname)
            putExtra("phone", phone)
            putExtra("organization", organization)
            putExtra("email", email)
        }
        startActivity(intent)
    }

    fun callUser(view: View) {
    }

    fun backToMain(view: View) {
        finish()
    }

    fun delete(view: View) {
        db.deleteUser(id)
        backToMain(view)
    }

    fun save(view: View) {
        if (nameText?.text?.isEmpty()!!){
            Toast.makeText(this, getString(R.string.EnterName), Toast.LENGTH_SHORT).show()
            return
        }
        if (phoneText?.text?.isEmpty()!!){
            Toast.makeText(this, getString(R.string.EnterPhone), Toast.LENGTH_SHORT).show()
            return
        }
        var user = User()
        user.name = nameText?.text.toString()
        user.surname = surnameText?.text.toString()
        user.phone = phoneText?.text.toString()
        user.organization = organizationText?.text.toString()
        user.email = emailText?.text.toString()
        db.updateUser(id, user)
        backToMain(view)
    }
}
