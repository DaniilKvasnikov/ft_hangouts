package com.school21.ft_hangouts

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.telephony.SmsManager
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

class PermissionsManager {

    private val MY_PERMISSIONS_REQUEST_SEND_SMS = 1
    private val permisionSMS = Manifest.permission.SEND_SMS
    fun setupPermissionsSms(activity: Activity, destinationAddress: String, smsMessage: String) {

        if (destinationAddress.isEmpty() || smsMessage.isEmpty()) return
        val permission = ContextCompat.checkSelfPermission(
            activity,
            permisionSMS
        )

        if (permission != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                activity,
                arrayOf(permisionSMS),
                MY_PERMISSIONS_REQUEST_SEND_SMS
            )
        } else{
            SmsManager.getDefault().sendTextMessage(destinationAddress, null, smsMessage, null, null)
            Log.i(destinationAddress, smsMessage)
        }
    }

    private val RECORD_REQUEST_CODE = 101

    fun setupPermissions(activity: Activity): Boolean {
        val permision= Manifest.permission.READ_CONTACTS
        val permission = ContextCompat.checkSelfPermission(
            activity,
            permision
        )

        return if (permission != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                activity,
                arrayOf(permision),
                RECORD_REQUEST_CODE
            )
            false
        } else{
            true
        }
    }

    fun setupPermissionsCall(activity: Activity, phone: String, name: String) {
        val permision= Manifest.permission.CALL_PHONE
        val permission = ContextCompat.checkSelfPermission(
            activity,
            permision
        )

        if (permission != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                activity,
                arrayOf(permision),
                RECORD_REQUEST_CODE
            )
        } else{
            val intent = Intent(Intent.ACTION_CALL, Uri.parse("tel:$phone"))
            activity.startActivity(intent)
        }
    }

}
