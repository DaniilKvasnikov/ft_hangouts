package com.school21.ft_hangouts

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
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
}
