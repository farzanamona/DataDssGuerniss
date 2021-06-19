package com.guerniss.model

import android.graphics.Bitmap

class HaircareModel {
    var image: Bitmap? = null

    constructor(){}

    constructor(image: Bitmap) {
        this.image = image

    }
}