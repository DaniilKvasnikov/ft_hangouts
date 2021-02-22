package com.school21.ft_hangouts.Activitis

import android.content.Intent
import android.os.Bundle
import android.telephony.PhoneNumberUtils
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import android.widget.Toast
import com.school21.ft_hangouts.*
import kotlinx.android.synthetic.main.activity_add_new_contact.*

class ContactInfoActivity : BaseActivity() {

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
        setContentView(R.layout.activity_contact_info)

        db = DataBaseHandler(this)

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

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.settings_menu, menu)

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            R.id.back ->{
                Finish()
                true
            }
            else->{
                super.onOptionsItemSelected(item)
            }
        }
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
        OpenActivity(intent)
    }

    fun callUser(view: View) {
        PermissionsManager()
            .setupPermissionsCall(this, phone.toString(), message?.text.toString());
    }

    fun backToMain(view: View) {
        Finish()
    }

    fun delete(view: View) {
        db.deleteUser(id)
        backToMain(view)
    }

    fun save(view: View) {
        if (nameText!!.text.trim().isEmpty()){
            Toast.makeText(this, getString(R.string.EnterName), Toast.LENGTH_SHORT).show()
            return
        }
        if (phoneText!!.text.isEmpty() ||  !PhoneNumberUtils.isWellFormedSmsAddress(phoneText!!.text.toString())){
            Toast.makeText(this, getString(R.string.EnterPhone), Toast.LENGTH_SHORT).show()
            return
        }
        val user = User()
        user.name = nameText?.text.toString()
        user.surname = surnameText?.text.toString()
        user.phone = phoneText?.text.toString()
        user.organization = organizationText?.text.toString()
        user.email = emailText?.text.toString()
        db.updateUser(id, user)
        backToMain(view)
    }
}
