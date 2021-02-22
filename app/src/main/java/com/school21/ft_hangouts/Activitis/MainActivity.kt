package com.school21.ft_hangouts.Activitis

import android.content.Intent
import android.database.Cursor
import android.os.Bundle
import android.provider.ContactsContract
import android.view.Menu
import android.view.MenuItem
import android.widget.AdapterView
import android.widget.ListView
import android.widget.SimpleAdapter
import android.widget.Toast
import com.school21.ft_hangouts.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap


open class MainActivity : BaseActivity() {

    private lateinit var listView: ListView
    private lateinit var db: DataBaseHandler

    companion object {
        var time: Long = 0
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        listView = findViewById(R.id.listView)
        updateUsersList()
        PermissionsManager().setupPermissionsReadSMS(this)
    }

    override fun onResume() {
        super.onResume()
        updateUsersList()
    }

    override fun onPause() {
        super.onPause()
        time = System.currentTimeMillis() / 1000L
    }

    fun loadUsersPhone(){
//        if (PermissionsManager().setupPermissions(this)){
//            val contactList = getContacts()
//            val phoneList = getPhones()
//            for (contact in contactList){
//                val newElem = HashMap<String, Any>()
//                newElem["Name"] = contact.name
//                newElem["Phone"] = phoneList[contact.id]?.get(0) ?: ""
//                newElem["Image"] = R.mipmap.ic_launcher
//                listHash.add(newElem)
//            }
//        }
    }

    private fun updateUsersList() {
        val users = dataBaseCreate()
        val listHash = showUsers(users)

        val adapter = SimpleAdapter(
            this, listHash,
            R.layout.list_item, arrayOf("Name", "Phone", "Image"), intArrayOf(
                R.id.text1,
                R.id.text2,
                R.id.image
            )
        )
        listView.adapter = adapter

        listView.onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id ->
            val user = users[id.toInt()]
            val intent = Intent(this, ContactInfoActivity::class.java).apply {
                putExtra("id", user.id)
                putExtra("name", user.name)
                putExtra("surname", user.surname)
                putExtra("phone", user.phone)
                putExtra("organization", user.organization)
                putExtra("email", user.email)
            }
            OpenActivity(intent)
        }
    }

    private fun getUsers(): ArrayList<User> {
        val data = db.readData()
        return data as ArrayList<User>
    }

    private fun showUsers(users: ArrayList<User>): ArrayList<HashMap<String, Any>>{
        val listHash = ArrayList<HashMap<String, Any>>()
        for(user in users){
            val newElem = HashMap<String, Any>()
            newElem["Name"] = user.name
            newElem["Phone"] = user.phone
            newElem["Image"] = R.mipmap.ic_launcher
            listHash.add(newElem)
        }
        return listHash
    }

    private fun dataBaseCreate(): ArrayList<User> {
        val context = this
        db = DataBaseHandler(context)
        return getUsers()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.main_menu, menu)

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            R.id.add -> {
                val intent = Intent(this, AddNewContactActivity::class.java)
                OpenActivity(intent)
                true
            }
            R.id.settings -> {
                Finish()
                val intent = Intent(this, SettingAppActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
                OpenActivity(intent)
                true
            }
            else->{
                super.onOptionsItemSelected(item)
            }
        }
    }

    private fun getContacts() : ArrayList<Contact>{
        val contactList = ArrayList<Contact>()

        val c: Cursor? = contentResolver.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null)
        if ((c?.count ?: 0) > 0) {
            val nameIndex = c?.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME)
            val idIndex = c?.getColumnIndex(ContactsContract.Contacts._ID)
            while (c?.moveToNext()!!)
                contactList.add(
                    Contact(
                        c.getString(
                            nameIndex!!
                        ), c.getString(idIndex!!)
                    )
                )
        }
        c?.close()
        return contactList
    }

    private fun getPhones() : HashMap<String, ArrayList<String>>{
        val contactList = HashMap<String, ArrayList<String>>()

        val c: Cursor? = contentResolver.query(
            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
            null,
            null,
            null,
            null
        )
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
