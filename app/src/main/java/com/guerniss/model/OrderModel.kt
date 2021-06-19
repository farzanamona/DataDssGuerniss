package com.guerniss.model

class OrderModel {
    var prod_name: String? = null
    var prod_qty: String? = null
    var prod_price: String? = null

    constructor(){}

    constructor(prod_name:String,prod_qty: String,prod_price: String) {
        this.prod_name = prod_name
        this.prod_qty = prod_qty
        this.prod_price = prod_price

    }
}