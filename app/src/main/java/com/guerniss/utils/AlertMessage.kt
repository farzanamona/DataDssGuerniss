package com.guerniss.utils

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import com.guerniss.R

class AlertMessage {

    companion object {

        fun showMessage(c: Context, title:String,
                        message:String) {
            val aBuilder = AlertDialog.Builder(c)
            aBuilder.setTitle(title)
            aBuilder.setIcon(R.mipmap.ic_launcher)
            aBuilder.setMessage(message)
            aBuilder.setPositiveButton("Ok", object: DialogInterface.OnClickListener {
                override fun onClick(dialog:DialogInterface, which:Int) {
                    dialog.dismiss()
                }
            })
            aBuilder.show()
        }

        fun showMessageFinishActivity(c: Context, activity: Activity,title:String,
                        message:String) {
            val aBuilder = AlertDialog.Builder(c)
            aBuilder.setCancelable(false)
            aBuilder.setTitle(title)
            aBuilder.setIcon(R.mipmap.ic_launcher)
            aBuilder.setMessage(message)
            aBuilder.setPositiveButton("Ok", object: DialogInterface.OnClickListener {
                override fun onClick(dialog:DialogInterface, which:Int) {
                    dialog.dismiss()
                    activity.finish()
                }
            })
            aBuilder.show()
        }
    }
}