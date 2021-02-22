package com.school21.ft_hangouts

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.telephony.SmsMessage
import com.school21.ft_hangouts.Activitis.SMSActivity
import com.school21.ft_hangouts.sms.Message
import com.school21.ft_hangouts.sms.SMSDataBaseHandler


@Suppress("DEPRECATION")
class SmsListener : BroadcastReceiver() {
    private lateinit var db: SMSDataBaseHandler

    override fun onReceive(context: Context, intent: Intent) {
        db = SMSDataBaseHandler(context)
        if (intent.action == SMS_RECEIVED) {
            val bundle = intent.extras
            if (bundle != null) {
                val pdus = bundle["pdus"] as Array<Any>?
                if (pdus!!.isEmpty()) {
                    return
                }
                val messages = arrayOfNulls<SmsMessage>(
                    pdus.size
                )
                val sb = StringBuilder()
                for (i in pdus.indices) {
                    messages[i] = SmsMessage.createFromPdu(pdus[i] as ByteArray)
                    sb.append(messages[i]?.messageBody)
                }
                val sender = messages[0]!!.originatingAddress
                val message = sb.toString()
                var newMessage = Message()
                newMessage.message = message
                newMessage.sender = sender
                newMessage.createdAt = System.currentTimeMillis() / 1000L
                newMessage.input = true
                db.insertData(newMessage)
                SMSActivity.intent?.addMessage(newMessage)
            }
        }
    }

    companion object {
        private const val SMS_RECEIVED = "android.provider.Telephony.SMS_RECEIVED"
    }
}