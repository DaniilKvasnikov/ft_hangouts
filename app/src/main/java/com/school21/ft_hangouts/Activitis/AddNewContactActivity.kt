package com.school21.ft_hangouts.Activitis

import android.os.Bundle
import android.telephony.PhoneNumberUtils
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import com.school21.ft_hangouts.*
import kotlinx.android.synthetic.main.activity_add_new_contact.*

class AddNewContactActivity : BaseActivity() {

    private lateinit var db: DataBaseHandler

    private val TAG = "PermissionDemo"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_new_contact)

        db = DataBaseHandler(this)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.settings_menu, menu)

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            R.id.back ->{
                finish()
                true
            }
            else->{
                super.onOptionsItemSelected(item)
            }
        }
    }

    fun addNewUser(view: View) {
        if (name.text.trim().isEmpty()){
            Toast.makeText(this, getString(R.string.EnterName), Toast.LENGTH_SHORT).show()
            return
        }
        if (phone.text.isEmpty() || !PhoneNumberUtils.isGlobalPhoneNumber(phone.text.toString())){
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
