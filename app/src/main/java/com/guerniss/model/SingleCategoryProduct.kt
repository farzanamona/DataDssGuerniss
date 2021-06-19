package com.guerniss.model

data class SingleCategoryProduct(
    val imageUrl: String,
    val data: List<Product>
)

data class Product(
    val brand_id: String,
    val color_code: String,
    val hex_color_code: String,
    var image: String,
    val is_active: String,
    val is_available: String,
    val is_showable: String,
    val main_category: String,
    val measuring_unit: Any,
    val mrp: String,
    val old_price: Any,
    val prod_actual_stock: Any,
    val prod_cart_desc: Any,
    val prod_id: Int,
    val prod_image: Any,
    val prod_long_desc: Any,
    val prod_name: String,
    val prod_short_desc: String,
    val prod_stock_price: String,
    val prod_sub_catg_id: String,
    val prod_visible_stock: Any,
    val prod_weight: Any,
    val sku: Any,
    val slug: Any,
    val sub_category: String,
    val unit_price: String,
    var quentity: Int,
    var totalPrice: Int
)