package com.example.smartfarm.Response

import com.google.gson.annotations.SerializedName

data class LoginResponse(

	@field:SerializedName("status")
	val status: String? = null,

	@field:SerializedName("token")
	val token: String? = null
)
