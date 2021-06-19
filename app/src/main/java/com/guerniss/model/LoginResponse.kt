package com.guerniss.model

data class LoginResponse(
    val success: Boolean,
    val token: Token,
    val user: User,
    val imageUrl: String
)

data class Token(
    val token: String
)

data class User(
    val address: String,
    val created_at: String,
    val email: String,
    val email_verified_at: Any,
    val gender: Any,
    val id: Int,
    val image: String,
    val name: String,
    val phone: String,
    val status: String,
    val updated_at: String,
    val usertype: String
)