package com.example.smartfarm.data

data class UserModel(
    val name: String,
    val email: String,
    val password: String,
    val islogin : Boolean,
    val token: String
)

