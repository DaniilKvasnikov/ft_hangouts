package com.school21.ft_hangouts

import android.database.Cursor
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract
import android.widget.ArrayAdapter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val list : MutableList<String> = mutableListOf()
        val adapter: ArrayAdapter<String> = ArrayAdapter(
            this,
            android.R.layout.simple_dropdown_item_1line,list
        )
        listView.adapter = adapter

        var contactList = getFoneContacts()
        for (contect in contactList)
            list.add(contect.name)
    }

    private fun getFoneContacts() : ArrayList<Contact>{
        val contactList = ArrayList<Contact>()

        val c: Cursor? = contentResolver.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null)
        if ((c?.count ?: 0) > 0) {
            val nameIndex = c?.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME)
            while (c?.moveToNext()!!){
                contactList.add(Contact(c.getString(nameIndex!!)))
            }
        }
        c?.close()
        return contactList
    }
}
