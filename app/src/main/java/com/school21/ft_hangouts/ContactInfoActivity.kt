package com.school21.ft_hangouts

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import kotlinx.android.synthetic.main.activity_add_new_contact.*
import kotlinx.android.synthetic.main.activity_add_new_contact.back
import kotlinx.android.synthetic.main.activity_contact_info.*
import java.io.Serializable

class ContactInfoActivity : MainActivity() {

    private lateinit var db: DataBaseHandler
    private var id: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        theme.applyStyle(getSharedPreferences(ThemesInfo.themeKey, Context.MODE_PRIVATE).getInt(ThemesInfo.themeKey, ThemesInfo.defTheme), true)
        setContentView(R.layout.activity_contact_info)

        val context = this
        db = DataBaseHandler(context)

        id = intent.getIntExtra("id", 0).toLong()
        val name: String? = intent.getStringExtra("name")
        val surname: String? = intent.getStringExtra("surname")
        val phone: String? = intent.getStringExtra("phone")
        val email: String? = intent.getStringExtra("email")
        val organization: String? = intent.getStringExtra("organization")

        val nameText : EditText = findViewById(R.id.name)
        val surnameText : EditText = findViewById(R.id.surname)
        val phoneText : EditText = findViewById(R.id.phone)
        val organizationText : EditText = findViewById(R.id.organization)
        val emailText : EditText = findViewById(R.id.email)

        nameText.setText(name)
        surnameText.setText(surname)
        phoneText.setText(phone)
        organizationText.setText(organization)
        emailText.setText(email)

        back.setOnClickListener {
            backToMain()
        }

        save.setOnClickListener {
            var user = User()
            user.name = nameText.text.toString()
            user.surname = surnameText.text.toString()
            user.phone = phoneText.text.toString()
            user.organization = organizationText.text.toString()
            user.email = emailText.text.toString()
            db.updateUser(id, user)
            backToMain()
        }

        delete.setOnClickListener {
            db.deleteUser(id)
            backToMain()
        }
    }

    private fun backToMain() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
}
