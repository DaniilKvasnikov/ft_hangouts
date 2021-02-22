package com.school21.ft_hangouts.Activitis

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.school21.ft_hangouts.PermissionsManager
import com.school21.ft_hangouts.R
import com.school21.ft_hangouts.sms.Message
import com.school21.ft_hangouts.sms.MessageListAdapter
import com.school21.ft_hangouts.sms.SMSDataBaseHandler

class SMSActivity : BaseActivity() {

    companion object {
        var intent: SMSActivity? = null
    }


    private lateinit var users: java.util.ArrayList<Message>
    private lateinit var adapter: MessageListAdapter
    private var message: EditText? = null
    private lateinit var phoneView: TextView
    private lateinit var nameView: TextView


    private var organization: String? = null
    private var email: String? = null
    private var phone: String? = null
    private var surname: String? = null
    private var name: String? = null
    private var id: Long = 0
    private lateinit var recyclerView: RecyclerView

    private lateinit var db: SMSDataBaseHandler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_s_m_s)
        PermissionsManager().setupPermissionsReadSMS(this)
        Companion.intent = this

        id = intent.getIntExtra("id", 0).toLong()
        name = intent.getStringExtra("name")
        surname = intent.getStringExtra("surname")
        phone = intent.getStringExtra("phone")
        email = intent.getStringExtra("email")
        organization = intent.getStringExtra("organization")

        message = findViewById(R.id.edittext_chatbox);
        phoneView = findViewById(R.id.phoneNumber)
        nameView = findViewById(R.id.nameText)

        phoneView.text = phone
        nameView.text = name

        recyclerView = findViewById(R.id.recyclerView)

        users = dataBaseCreate()

        adapter = MessageListAdapter(users)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.scrollToPosition(adapter.messages.count() - 1)
    }

    override fun onDestroy() {
        Companion.intent = null
        super.onDestroy()
    }

    private fun getMessages(): ArrayList<Message> {
        val data = db.readData(phone.toString())
        return data as ArrayList<Message>
    }

    private fun dataBaseCreate(): ArrayList<Message> {
        val context = this
        db = SMSDataBaseHandler(context)
        return getMessages()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.settings_menu, menu)

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            R.id.back -> {
                finish()
                true
            }
            else->{
                super.onOptionsItemSelected(item)
            }
        }
    }

    fun sendMessage(view: View) {
        val newMessage = Message()
        newMessage.message = message?.text.toString()
        newMessage.sender = phone.toString()
        newMessage.createdAt = System.currentTimeMillis() / 1000L
        newMessage.input = false
        db.insertData(newMessage)
        addMessage(newMessage)
        PermissionsManager()
            .setupPermissionsSms(this, phone.toString(), message?.text.toString());
        Toast.makeText(this, message?.text.toString(), Toast.LENGTH_LONG).show()
    }

    fun backToMain(view: View) {
        finish()
    }

    fun addMessage(newMessage: Message) {
        adapter.addMessage(newMessage)
        recyclerView.scrollToPosition(adapter.messages.count() - 1)
    }
}