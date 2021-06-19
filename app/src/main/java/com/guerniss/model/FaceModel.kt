package com.guerniss.model

import android.graphics.Bitmap

class FaceModel {
    var image: Bitmap? = null

    constructor(){}

    constructor(image: Bitmap) {
        this.image = image

    }
}