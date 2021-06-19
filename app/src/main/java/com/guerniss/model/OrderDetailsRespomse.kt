package com.guerniss.model

data class OrderDetailsRespomse(
    val data: List<DataDetils>
)

data class DataDetils(
    val created_at: String,
    val od_net_prod_price: String,
    val od_prod_id: String,
    val od_prod_qty: String,
    val od_prod_unit_price: String,
    val od_prod_vat: Any,
    val od_sku: Any,
    val order_did: Int,
    val order_id: String,
    val product: Product,
    val updated_at: String
)

