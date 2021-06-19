package com.guerniss.DB;

import android.graphics.Bitmap;

public class LocalDBProduct {
    String prod_name;
    Bitmap image;

    public LocalDBProduct(String prod_name, Bitmap image) {
        this.prod_name = prod_name;
        this.image = image;
    }

    public LocalDBProduct() {
    }

    public String getProd_name() {
        return prod_name;
    }

    public void setProd_name(String prod_name) {
        this.prod_name = prod_name;
    }

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }
}
