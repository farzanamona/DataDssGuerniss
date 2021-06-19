package com.guerniss.model

data class OrderListResponse(
    val data: List<Data>
)

data class Data(
    val created_at: String,
    val order_cancel_date: Any,
    val order_canceled_by: Any,
    val order_city: Any,
    val order_country: Any,
    val order_coupon_no: Any,
    val order_cust_id: String,
    val order_customer_feedback: Any,
    val order_date: String,
    val order_delivered_by: Any,
    val order_delivery_date: Any,
    val order_discount: String,
    val order_ds_id: Any,
    val order_email: String,
    val order_id: Int,
    val order_net_amount: String,
    val order_no: String,
    val order_pay_op_id: String,
    val order_payment_status: String,
    val order_phone: String,
    val order_priority: Any,
    val order_qty: Any,
    val order_ship_address: String,
    val order_ship_name: String,
    val order_state: String,
    val order_tax: Any,
    val order_total_amount: String,
    val order_tracking_no: Any,
    val order_vat: Any,
    val order_zipcode: Any,
    val updated_at: String
)