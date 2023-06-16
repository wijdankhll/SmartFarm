package com.example.smartfarm.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import com.example.smartfarm.API.ApiConfig
import com.example.smartfarm.API.ApiService
import com.example.smartfarm.Response.LoginResponse
import com.example.smartfarm.Response.RegisterResponse
import com.example.smartfarm.Response.UserPreference.UserPreference
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class userRepository private constructor(
    private val userPreference: UserPreference,

){
    private val _register = MutableLiveData<RegisterResponse>()
    val register: LiveData<RegisterResponse> = _register

    private val _login = MutableLiveData<LoginResponse>()
    val login: LiveData<LoginResponse> = _login



    suspend fun userLogout() {
        userPreference.islogin(false)
    }

    suspend fun userLogin() {
        userPreference.islogin(true)
    }

    fun registerRequest(name: String, email: String, password: String) {
        ApiConfig.getApiService()
            .getRegister(name, email, password, "")
            .enqueue(object : Callback<RegisterResponse> {
                override fun onResponse(
                    call: Call<RegisterResponse>,
                    response: Response<RegisterResponse>
                ) {
                    if (response.isSuccessful){
                        _register.value = response.body()

                    }
                }

                override fun onFailure(call: Call<RegisterResponse>, t: Throwable) {
                    t.message?.let { Log.d("Failure", it) }
                }

            })
    }

    fun loginRequest(email: String, password: String) {
        ApiConfig.getApiService()
            .getLogin(email, password)
            .enqueue(object : Callback<LoginResponse> {
                override fun onResponse(
                    call: Call<LoginResponse>,
                    response: Response<LoginResponse>
                ) {
                    if (response.isSuccessful){
                        _login.value = response.body()
                    }
                }

                override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                    t.message?.let { Log.d("Failure", it) }
                }

            })
    }





    suspend fun saveUser(uModel: UserModel) {
        userPreference.saveUser(uModel)
    }

    fun getUser(): LiveData<UserModel> {
        return userPreference.getUser().asLiveData()
    }

    companion object {
        @Volatile
        private var instance: userRepository? = null
        fun getInstance(
            preferences: UserPreference,

        ): userRepository =
            instance ?: synchronized(this) {
                instance ?: userRepository(preferences)
            }.also { instance = it}
        }
}

