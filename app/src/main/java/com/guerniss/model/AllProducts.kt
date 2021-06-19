package com.guerniss.model


import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.widget.Button

class AllProducts {

    var image: Bitmap? = null

    constructor(){}

    constructor(image:Bitmap) {
        this.image = image
    }
}