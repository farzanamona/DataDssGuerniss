package com.guerniss.model

import android.graphics.Bitmap

class NailModel {
    var image: Bitmap? = null

    constructor(){}

    constructor(image: Bitmap) {
        this.image = image
    }
}