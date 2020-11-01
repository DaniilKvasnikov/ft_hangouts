package com.school21.ft_hangouts

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment

class MyDialogFragment : DialogFragment(){

    var delta: Long = 0

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            builder.setTitle("time the app")
                .setMessage("$delta sec.")
                .setIcon(R.drawable.ic_launcher_42_background)
                .setPositiveButton("OK") {
                        dialog, id ->  dialog.cancel()
                }
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }
}
