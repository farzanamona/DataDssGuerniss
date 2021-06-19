package com.guerniss.utils

import android.content.Context
import com.kaopiz.kprogresshud.KProgressHUD


public class AppHelper {

    companion object{

        public fun  progressLoader(context: Context):KProgressHUD{

            var hud: KProgressHUD? = null
              hud = KProgressHUD.create(context)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel("Please wait")
                .setMaxProgress(100)

            return hud

        }
    }
}