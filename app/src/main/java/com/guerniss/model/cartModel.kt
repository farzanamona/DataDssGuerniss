package com.guerniss.model

import android.graphics.Bitmap
import android.widget.TextView

class cartModel {
    var image: Bitmap? = null
    var prod_name: String =""
    var price: String = ""

    constructor(){}

    constructor(prod_name:String,price :String,image: Bitmap) {
        this.image = image
        this.prod_name = prod_name
        this.price = price

    }
}