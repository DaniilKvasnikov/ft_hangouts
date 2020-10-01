package com.school21.ft_hangouts

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.database.Cursor
import android.os.Bundle
import android.provider.ContactsContract
import android.widget.ListView
import android.widget.SimpleAdapter
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    var timeSec: Long = 0
    lateinit var sharedPreferences: SharedPreferences
    val themeKey = "currentTheme"
    val defTheme = R.style.AppTheme

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        sharedPreferences = getSharedPreferences(
            "ThemePref",
            Context.MODE_PRIVATE
        )

        val style = sharedPreferences.getInt(themeKey, defTheme)
        theme.applyStyle(style, true)

        setContentView(R.layout.activity_main)

        settings.setOnClickListener {
            val intent = Intent(this, SettingAppActivity::class.java)
            startActivity(intent)
        }

        val listView : ListView = findViewById<ListView>(R.id.listView)

        val listHash = ArrayList<HashMap<String, Any>>()
        val adapter = SimpleAdapter(this, listHash, R.layout.list_item, arrayOf<String>("Name", "Phone", "Image"), intArrayOf(R.id.text1, R.id.text2, R.id.image))
        listView.adapter = adapter

        val contactList = getContacts()
        val phoneList = getPhones()
        for (contact in contactList){
            val newElem = HashMap<String, Any>()
            newElem["Name"] = contact.name
            newElem["Phone"] = phoneList[contact.id]?.get(0) ?: ""
            newElem["Image"] = R.mipmap.ic_launcher
            listHash.add(newElem)
        }
    }

    override fun onPause() {
        super.onPause()
        timeSec = System.currentTimeMillis() / 1000
    }

    override fun onResume() {
        super.onResume()
        val timeText = findViewById<TextView>(R.id.time)
        val deltaTime = System.currentTimeMillis() / 1000 - timeSec
        if (timeSec != 0L)
            timeText.text = "time = $deltaTime sec"
    }

    private fun getContacts() : ArrayList<Contact>{
        val contactList = ArrayList<Contact>()

        val c: Cursor? = contentResolver.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null)
        if ((c?.count ?: 0) > 0) {
            val nameIndex = c?.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME)
            val idIndex = c?.getColumnIndex(ContactsContract.Contacts._ID)
            while (c?.moveToNext()!!){
                contactList.add(Contact(c.getString(nameIndex!!), c.getString(idIndex!!)))
            }
        }
        c?.close()
        return contactList
    }

    private fun getPhones() : HashMap<String, ArrayList<String>>{
        val contactList = HashMap<String, ArrayList<String>>()

        val c: Cursor? = contentResolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, null)
        if (c != null && c.count > 0) {
            val idIndex = c.getColumnIndex(ContactsContract.CommonDataKinds.Phone.CONTACT_ID)
            val phoneIndex = c.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)
            while (c.moveToNext()){
                val contactID = c.getString(idIndex)
                val contactPhone : String = c.getString(phoneIndex)
                if (!contactList.containsKey(contactID))
                    contactList[contactID] = ArrayList()
                contactList[contactID]?.add(contactPhone)
            }
        }
        c?.close()
        return contactList
    }
}
