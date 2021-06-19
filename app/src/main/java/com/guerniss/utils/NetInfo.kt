package com.guerniss.utils

import android.content.Context
import android.net.ConnectivityManager

class NetInfo{

    companion object {
        fun isOnline(ctx: Context):Boolean {
            val cm = ctx
                    .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val ni = cm.getActiveNetworkInfo()
            if (ni != null)
            {
                return ni.isConnectedOrConnecting()
            }
            else
            {
                return false
            }
        }


        fun isWIFI(con:Context):Boolean {
            val cm = con
                    .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val activeNetwork = cm.getActiveNetworkInfo()
            val isConnected = (activeNetwork != null && activeNetwork.isConnectedOrConnecting())
            val isWiFi = activeNetwork.getType() === ConnectivityManager.TYPE_WIFI
            if (isConnected)
            {
                if (isWiFi)
                {
                    return true
                }
                else
                {
                    return false
                }
            }
            return false
        }
    }


}