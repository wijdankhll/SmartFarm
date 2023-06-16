package com.example.smartfarm.API

import com.example.smartfarm.Response.LoginResponse
import com.example.smartfarm.Response.RegisterResponse
import retrofit2.Call
import retrofit2.http.*

interface ApiService {

    @FormUrlEncoded
    @POST("users/signin")
    fun getLogin(
        @Field("email") email: String,
        @Field("password") password: String,
    ): Call<LoginResponse>

    @FormUrlEncoded
    @POST("users/signup")
    fun getRegister(
        @Field("name") name: String,
        @Field("email") email: String,
        @Field("password") password: String,
        @Field("imageURL") img: String,
    ): Call<RegisterResponse>
}