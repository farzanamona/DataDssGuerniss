package com.guerniss.model

data class CustomerDetaisResponse(
    val data: List<DataCust>,
    val imageUrl: String
)

data class DataCust(
    val coupon_no: Any,
    val created_at: String,
    val cust_address: String,
    val cust_category: Any,
    val cust_catg_dis_pct: Any,
    val cust_catg_id: String,
    val cust_coupon_pct: Any,
    val cust_date_of_birth: String,
    val cust_email: String,
    val cust_email_verified_at: Any,
    val cust_employee_statement: String,
    val cust_gender: String,
    val cust_id: Int,
    val cust_image: String,
    val cust_marrage_aniversary: String,
    val cust_name: String,
    val cust_phone: String,
    val cust_status: String,
    val cust_total_dis_pct: Any,
    val fb_link: Any,
    val is_fb_cust: String,
    val otp_code: Any,
    val password: String,
    val remember_token: Any,
    val total_order: Any,
    val updated_at: String
)