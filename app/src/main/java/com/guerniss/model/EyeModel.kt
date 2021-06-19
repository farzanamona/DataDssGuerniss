package com.guerniss.model

import android.graphics.Bitmap

class EyeModel {
    var image: Bitmap? = null

    constructor(){}

    constructor(image: Bitmap) {
        this.image = image
    }
}