package com.school21.ft_hangouts

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.telephony.SmsMessage
import android.widget.Toast

@Suppress("DEPRECATION")
class SmsListener : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
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
                Toast.makeText(context, "$sender: $message", Toast.LENGTH_LONG).show()
            }
        }
    }

    companion object {
        private const val SMS_RECEIVED = "android.provider.Telephony.SMS_RECEIVED"
    }
}